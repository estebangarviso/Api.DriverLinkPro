package com.estebangarviso.driverlinkpro.domain.common;


import java.time.LocalDateTime;

public interface SoftDeleteInterface {
    Boolean getIsDeleted();
    void setIsDeleted(Boolean isDeleted);
    LocalDateTime getDeletedAt();
    void setDeletedAt(LocalDateTime deletedAt);
}
