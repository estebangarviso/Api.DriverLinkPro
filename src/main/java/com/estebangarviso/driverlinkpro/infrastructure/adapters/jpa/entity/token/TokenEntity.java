package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token;

import com.estebangarviso.driverlinkpro.domain.model.token.TokenType;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@Table(
        name = "tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_token_value", columnNames = "value")
        },
        indexes = {
                @Index(name = "uk_token_value", columnList = "value", unique = true)
        }
)
@Entity
public class TokenEntity {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false, updatable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    private TokenType type = TokenType.BEARER;

    private boolean revoked = Boolean.FALSE;

    private boolean expired = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_user",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_token_user")
    )
    private UserEntity user;
}