package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver;

import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.listener.DriverListener;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@EntityListeners({DriverListener.class})
@Transactional
@Table(
        name = "driver",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_driver_code", columnNames = "code")
        },
        indexes = {
                @Index(name = "uk_driver_code", columnList = "code", unique = true)
        }
)
@SQLDelete(sql = "UPDATE driver SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class DriverEntity implements SoftDeleteInterface, EnableInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36, updatable = false)
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
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_driver_vehicle"))
    private VehicleEntity vehicle;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_driver_user"))
    private UserEntity user;
}
