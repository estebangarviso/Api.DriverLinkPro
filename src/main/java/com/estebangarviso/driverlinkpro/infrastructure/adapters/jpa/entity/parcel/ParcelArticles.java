package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(
        name = "parcel_articles",
        uniqueConstraints = {
                @UniqueConstraint(name = "parcel_articles_code_unique", columnNames = {"code"})
        }
)
@SQLDelete(sql = "UPDATE parcel_articles SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class ParcelArticles extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_parcel", referencedColumnName = "id", nullable = false)
    private Parcel parcel;
}
