package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "vehicle",
        uniqueConstraints = {
                @UniqueConstraint(name = "vehicles_code_unique", columnNames = "code")
        },
        indexes = {
                @Index(name = "parcel_code_index", columnList = "code")
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
    @Column(nullable = false, length = 64)
    private String code;

    private Boolean isEnabled = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "vehicle")
    private DriverEntity driver;
}