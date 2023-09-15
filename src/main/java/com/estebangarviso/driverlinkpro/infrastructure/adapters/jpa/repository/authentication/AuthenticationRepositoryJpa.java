package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepositoryJpa extends JpaRepository<UserEntity, Long> {

    /**
     * Find user by email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find user by security token
     */
    Optional<UserEntity> findBySecurityToken(String securityToken);

    /**
     * Check if user exists by email
     */
    Boolean existsByEmail(String email);
}

