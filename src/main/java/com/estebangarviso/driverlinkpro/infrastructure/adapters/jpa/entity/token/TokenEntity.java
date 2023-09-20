package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token;

import com.estebangarviso.driverlinkpro.domain.model.token.TokenType;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "token_value_unique", columnNames = "value")
        },
        indexes = {
                @Index(name = "token_value_index", columnList = "value")
        }
)
@Entity
public class TokenEntity {
    @Id
    @GeneratedValue()
    private Long id;

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