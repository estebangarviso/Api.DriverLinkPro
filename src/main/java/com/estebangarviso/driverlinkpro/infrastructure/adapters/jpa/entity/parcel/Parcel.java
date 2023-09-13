package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.BaseEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(
    name = "parcel",
    uniqueConstraints = {
            @UniqueConstraint(name = "parcel_code_unique", columnNames = {"code"})
    }
)
@SQLDelete(sql = "UPDATE parcel SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class Parcel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    @Column(nullable = false)
    private ParcelStatus status;

    @OneToMany(mappedBy = "parcel")
    private Set<ParcelArticles> articles;

    @ManyToOne
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;
}