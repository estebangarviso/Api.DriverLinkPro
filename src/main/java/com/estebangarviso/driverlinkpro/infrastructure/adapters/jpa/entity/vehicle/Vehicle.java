package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.BaseEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.Driver;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.Parcel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(
        name = "vehicle",
        uniqueConstraints = {
                @UniqueConstraint(name = "vehicles_code_unique", columnNames = {"code"})
        }
)
@SQLDelete(sql = "UPDATE vehicle SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class Vehicle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String code;

    @OneToOne(mappedBy = "vehicle")
    private Driver driver;

    @OneToMany(mappedBy = "vehicle")
    private Set<Parcel> parcels;
}
