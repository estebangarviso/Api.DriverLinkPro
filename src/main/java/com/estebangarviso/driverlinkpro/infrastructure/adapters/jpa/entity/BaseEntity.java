package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(nullable = false)
    private Boolean isEnabled = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
