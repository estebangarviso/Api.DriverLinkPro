package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;
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
    name = "parcel",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_parcel_code", columnNames = "code")
    },
    indexes = {
        @Index(name = "uk_parcel_code", columnList = "code", unique = true)
    }
)
@SQLDelete(sql = "UPDATE parcel SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class ParcelEntity implements SoftDeleteInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, updatable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    @Column(nullable = false)
    private ParcelStatus status;

    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "id_parcel",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_parcel_details")
    )
    private List<ParcelDetailsEntity> details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        nullable = false,
        name = "id_vehicle",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_parcel_vehicle")
    )
    private VehicleEntity vehicle;

    public void removeDetail(ParcelDetailsEntity parcelDetailsEntity) {
        this.details.remove(parcelDetailsEntity);
    }

    @PrePersist
    public void prePersist() {
        if (this.code == null) {
            this.code = UUID.randomUUID().toString();
        }
    }

}