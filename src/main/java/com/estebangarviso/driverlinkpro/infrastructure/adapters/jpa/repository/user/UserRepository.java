package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.user;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { }
