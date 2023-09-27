package com.estebangarviso.driverlinkpro;

import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequestBodyDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(Application.class);
	private final AuthenticationRepository authenticationRepository;
	private final PasswordEncoder passwordEncoder;
	@Value("${application.admin.email}")
	private String adminEmail;
	@Value("${application.admin.password}")
	private String adminPassword;
	@Value("${application.admin.firstName}")
	private String adminFirstName;
	@Value("${application.admin.lastName}")
	private String adminLastName;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		seedAdmin();
	}

	private void seedAdmin() {
		var users = authenticationRepository.findAll();
		if (users.isEmpty()) {
			logger.info("Seeding admin");
			var request = new SignUpRequestBodyDto();
			request.setFirstName(adminFirstName);
			request.setLastName(adminLastName);
			request.setEmail(adminEmail);
			request.setPassword(adminPassword);

            String securityToken = UUID.randomUUID().toString();
			UserEntity admin = new UserEntity();
			admin.setFirstName(request.getFirstName());
			admin.setLastName(request.getLastName());
			admin.setEmail(request.getEmail());
			admin.setPassword(passwordEncoder.encode(request.getPassword()));
			admin.addRole(UserRole.ADMIN);
			admin.setIsEnabled(true);
			admin.setSecurityToken(securityToken);
			authenticationRepository.save(admin);
			logger.info("Admin with email {} seeded", adminEmail);
		} else {
			logger.info("User already seeded");
		}
	}
}
