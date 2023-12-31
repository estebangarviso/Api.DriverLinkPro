package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user;

import com.estebangarviso.driverlinkpro.domain.common.AuditTimeInterface;
import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "`user`",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_user_security_token", columnNames = "securityToken")
        },
        indexes = {
                @Index(name = "uk_user_email", columnList = "email", unique = true),
                @Index(name = "uk_user_security_token", columnList = "securityToken", unique = true)
        }
)
@SQLDelete(sql = "UPDATE user SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
@Getter
@Setter
public class UserEntity implements UserDetails, SoftDeleteInterface, EnableInterface, AuditTimeInterface {
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

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();

    @Column(nullable = false, length = 64)
    private String securityToken;

    private Boolean isEnabled = Boolean.FALSE;
    private Boolean isDeleted = Boolean.FALSE;
    private Boolean isLocked = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime passwordExpiration;
    private LocalDateTime accountExpiration;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DriverEntity> drivers = new ArrayList<>();

    public void addDriver(DriverEntity driver) {
        this.drivers.add(driver);
        driver.setUser(this);
    }

    public void addRole(UserRole role) {
        this.roles.add(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (accountExpiration != null) return accountExpiration.isAfter(LocalDateTime.now());
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (passwordExpiration != null) return passwordExpiration.isAfter(LocalDateTime.now());
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return authorities;
    }
}
