package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLDelete;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Transactional
@Table(
        name = "parcel_details",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_parcel_details_code", columnNames = "code")
        },
        indexes = {
                @Index(name = "uk_parcel_details_code", columnList = "code", unique = true)
        }
)
@SQLDelete(sql = "UPDATE parcel_details SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class ParcelDetailsEntity implements SoftDeleteInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, updatable = false)
    private String code;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false, length = 64)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @PrePersist
    public void prePersist() {
        if (this.code == null) {
            this.code = UUID.randomUUID().toString();
        }
    }
}
