package com.estebangarviso.driverlinkpro;

import com.estebangarviso.driverlinkpro.domain.model.token.TokenType;
import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.domain.service.mail.MailContentBuilderService;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token.TokenEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.token.TokenRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.smtp.SMTPAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@SpringBootApplication
public class Application implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(Application.class);
	private final MailContentBuilderService mailContentBuilderService;
	private final AuthenticationRepository authenticationRepository;
	private final SMTPAdapter smtpAdapter;
	private final TokenRepository tokenRepository;

	public Application(MailContentBuilderService mailContentBuilderService, AuthenticationRepository authenticationRepository, SMTPAdapter smtpAdapter, TokenRepository tokenRepository) {
		this.mailContentBuilderService = mailContentBuilderService;
		this.authenticationRepository = authenticationRepository;
		this.smtpAdapter = smtpAdapter;
		this.tokenRepository = tokenRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		seedUsers();
	}

	private void seedUsers() {
		var users = authenticationRepository.findAll();
		if (users.isEmpty()) {
			var securityToken = UUID.randomUUID().toString();
			var user = new UserEntity();
			user.setFirstName("Esteban");
			user.setLastName("Garviso");
			user.setEmail("e.garvisovenegas@gmail.com");
			user.setPassword("password");
			user.addRole(UserRole.USER);
			user.setIsEnabled(true);
			user.setSecurityToken(securityToken);
			getDriverEntities().forEach(user::addDriver);


			authenticationRepository.save(user);
			saveUserToken(user, securityToken);
			sendConfirmationEmail(user.getFirstName(), user.getLastName(), user.getEmail(), securityToken);
			logger.info("User seeded");
		} else {
			logger.info("User already seeded");
		}
	}

	private Set<DriverEntity> getDriverEntities() {
		logger.info("Preparing drivers");
		Set<DriverEntity> drivers = new HashSet<>();
		var driver1 = new DriverEntity();
		var vehicle1 = new VehicleEntity();
		vehicle1.setCode("VEHICLE-1");
		driver1.setCode("DRIVER-1");
		driver1.setName("Driver 1");
		driver1.setCellphone("+56912345678");
		driver1.setEmail("driver1@example.com");
		driver1.setVehicle(vehicle1);

		var driver2 = new DriverEntity();
		var vehicle2 = new VehicleEntity();
		vehicle2.setCode("VEHICLE-2");
		driver2.setCode("DRIVER-2");
		driver2.setName("Driver 2");
		driver2.setCellphone("+56912345678");
		driver2.setEmail("driver2@example.com");
		driver2.setVehicle(vehicle2);

		drivers.add(driver1);
		drivers.add(driver2);
		return drivers;
	}

	private void saveUserToken(UserEntity user, String jwtToken) {
		var token = TokenEntity.builder()
				.user(user)
				.value(jwtToken)
				.type(TokenType.BEARER)
				.build();
		tokenRepository.save(token);
	}

	private void sendConfirmationEmail(String firstName, String lastName, String email, String securityToken) {
		URI uri;
		String uriString;
		uri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
		uriString = uri.toString();

		var emailBody = mailContentBuilderService
				.addVariables(new HashMap<>() {{
					put("firstName", firstName);
					put("lastName", lastName);
					put("confirmationLink", uriString + "/api/v1/auth/confirm-email/" + securityToken);
				}})
				.setTemplate("email-confirmation")
				.build();

		smtpAdapter.send(
				"Confirm your email",
				emailBody,
				email,
				"true"
		);
	}
}
