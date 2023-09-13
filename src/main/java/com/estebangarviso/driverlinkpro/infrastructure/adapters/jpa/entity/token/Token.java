package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.User;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "token_token_unique", columnNames = {"token"})
        }
)
@Entity
public class Token {

    @Id
    @GeneratedValue()
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked = Boolean.FALSE;

    private boolean expired = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;
}