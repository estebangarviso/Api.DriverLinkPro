package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "parcel_articles",
        uniqueConstraints = {
                @UniqueConstraint(name = "parcel_articles_code_unique", columnNames = "code")
        },
        indexes = {
                @Index(name = "parcel_articles_code_index", columnList = "code")
        }
)
@SQLDelete(sql = "UPDATE parcel_articles SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class ParcelDetailsEntity implements SoftDeleteInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String code;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    private Boolean isDeleted = Boolean.FALSE;
    private LocalDateTime deletedAt;
}
