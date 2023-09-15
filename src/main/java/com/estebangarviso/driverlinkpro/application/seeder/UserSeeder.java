package com.estebangarviso.driverlinkpro.application.seeder;

import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.user.UserRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
class UserSeeder implements ApplicationRunner {

    private final UserRepository userRepository;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var users = userRepository.findAll();
        if (users.isEmpty()) {
            var securityToken = UUID.randomUUID().toString();
            var userRoles = new HashSet<UserRole>();
            userRoles.add(UserRole.USER);
            var user = new UserEntity();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("johndoe@example.com");
            user.setPassword("password");
            user.setRoles(userRoles);
            user.setIsEnabled(true);
            user.setSecurityToken(securityToken);

            userRepository.save(user);
        } else {
            System.out.println("Users already exist");
        }
    }
}