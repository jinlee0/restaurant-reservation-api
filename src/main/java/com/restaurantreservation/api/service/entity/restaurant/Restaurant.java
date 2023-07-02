package com.restaurantreservation.api.service.entity.restaurant;

import com.restaurantreservation.api.service.entity.BaseEntity;
import com.restaurantreservation.api.service.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description = "";
    @Embedded
    private VoLocation location;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Builder
    private Restaurant(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, VoLocation location, User manager) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.location = location;
        this.manager = manager;
    }
}
