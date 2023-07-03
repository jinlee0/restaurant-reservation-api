package com.restaurantreservation.api.service.entity;

import com.restaurantreservation.api.global.util.LocaleUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GenericGenerator(name = "uuid_gen", type = CustomIdGenerator.class)
    @GeneratedValue(generator = "uuid_gen")
    private String id;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void setDefault() {
        LocalDateTime now = LocaleUtil.DateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void updateUpdatedAt() {
        this.updatedAt = LocaleUtil.DateTime.now();
    }
}
