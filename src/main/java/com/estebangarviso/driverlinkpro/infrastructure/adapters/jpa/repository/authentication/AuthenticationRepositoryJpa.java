package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.User;
import java.util.Optional;

@Repository
public interface AuthenticationRepositoryJpa extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findBySecurityToken(String securityToken);

    Boolean existsByEmail(String email);
}

