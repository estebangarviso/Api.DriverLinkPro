package com.estebangarviso.driverlinkpro.application.usecase.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;

@AllArgsConstructor
@Component
public interface UpdateUserRoleUseCase {
    void updateUserRole(Long userId, UserRole userRole);
}
