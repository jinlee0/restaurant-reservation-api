package com.restaurantreservation.api.service.entity.reservation;

import com.restaurantreservation.api.service.entity.BaseEntity;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "num_people", nullable = false)
    private Integer numberOfPeople;
    @Column(name = "requirements", nullable = false)
    private String requirements = "";
    @Embedded
    private VoReservationStatus status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private User customer;
    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, updatable = false)
    private Restaurant restaurant;

    @Builder
    private Reservation(String id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime dateTime, Integer numberOfPeople, String requirements, VoReservationStatus status, User customer, Restaurant restaurant) {
        super(id, createdAt, updatedAt);
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
        this.requirements = requirements;
        this.status = status;
        this.customer = customer;
        this.restaurant = restaurant;
    }
}
