package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "driver",
        uniqueConstraints = {
                @UniqueConstraint(name = "drivers_code_unique", columnNames = {"code"})
        }
)
@SQLDelete(sql = "UPDATE driver SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String code;
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 15)
    private String cellphone;

    @Column(nullable = false, length = 95)
    private String email;

    private Boolean isEnabled = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id")
    private Vehicle vehicle;
}
