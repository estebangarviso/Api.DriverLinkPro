package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(
    name = "parcel",
    uniqueConstraints = {
            @UniqueConstraint(name = "parcel_code_unique", columnNames = "code")
    },
    indexes = {
            @Index(name = "parcel_code_index", columnList = "code")
    }
)
@SQLDelete(sql = "UPDATE parcel SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
public class ParcelEntity implements SoftDeleteInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String code;

    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    @Column(nullable = false)
    private ParcelStatus status;

    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_parcel", referencedColumnName = "id")
    private Set<ParcelDetailsEntity> articles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id", nullable = false)
    private VehicleEntity vehicle;
}