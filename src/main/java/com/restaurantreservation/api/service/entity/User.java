package com.restaurantreservation.api.service.entity;

import com.restaurantreservation.api.service.entity.type.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {
    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, UserRole role) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void registerToPartner() {
        this.role = UserRole.ROLE_PARTNER;
    }

    public boolean isPartner() {
        return this.role == UserRole.ROLE_PARTNER;
    }

    public boolean isCustomer() {
        return this.role == UserRole.ROLE_CUSTOMER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
