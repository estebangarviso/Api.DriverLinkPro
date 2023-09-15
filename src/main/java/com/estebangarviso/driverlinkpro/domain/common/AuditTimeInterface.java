package com.estebangarviso.driverlinkpro.domain.common;

import java.time.LocalDateTime;

public interface AuditTimeInterface {
    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);
    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}
