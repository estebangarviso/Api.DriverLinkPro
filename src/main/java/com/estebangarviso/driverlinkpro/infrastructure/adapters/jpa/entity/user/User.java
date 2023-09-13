package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.Driver;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "`user`",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = {"email"}),
        }
)
@SQLDelete(sql = "UPDATE user SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 95)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime passwordExpiration;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime accountExpiration;

    @Column(nullable = false)
    private Boolean isLocked = Boolean.FALSE;

    @Column(nullable = false, unique = true)
    private String securityToken;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_drivers",
//            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "id_driver", referencedColumnName = "id")
//    )
//    private Set<Driver> drivers;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpiration == null || accountExpiration.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return passwordExpiration.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return this.getIsEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.name()) );
    }

}
