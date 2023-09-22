package com.estebangarviso.driverlinkpro.domain.model.user;

import com.estebangarviso.driverlinkpro.domain.common.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements SoftDeleteInterface, EnableInterface, AuditTimeInterface{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<UserRole> roles;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private Boolean isEnabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
