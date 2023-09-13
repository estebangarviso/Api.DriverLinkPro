package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(
        name = "user_drivers",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_drivers_unique", columnNames = {"id_user", "id_driver"})
        },
        indexes = {
                @Index(name = "user_drivers_id_user_index", columnList = "id_user"),
                @Index(name = "user_drivers_id_driver_index", columnList = "id_driver")
        }
)
@Getter
@Setter
public class UserDrivers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long id_user;

    @Column(nullable = false)
    private Long id_driver;
}
