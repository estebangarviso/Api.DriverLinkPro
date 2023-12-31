package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Transactional
@Table(
        name = "vehicle",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vehicle_code", columnNames = "code")
        },
        indexes = {
                @Index(name = "uk_vehicle_code", columnList = "code", unique = true)
        }
)
@SQLDelete(sql = "UPDATE vehicle SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class VehicleEntity implements SoftDeleteInterface, EnableInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, updatable = false)
    private String code;

    private Boolean isEnabled = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "vehicle")
    private DriverEntity driver;

    @OneToMany(mappedBy = "vehicle")
    private List<ParcelEntity> parcels;

    @PrePersist
    public void prePersist() {
        if (this.code == null) {
            this.code = UUID.randomUUID().toString();
        }
    }
}
