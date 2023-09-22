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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @Column(nullable = false, length = 36)
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