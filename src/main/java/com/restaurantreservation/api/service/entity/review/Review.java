package com.restaurantreservation.api.service.entity.review;

import com.restaurantreservation.api.service.entity.BaseEntity;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Column(name = "comment", columnDefinition = "TEXT", nullable = false)
    private String comment;
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservationId", nullable = false)
    private Reservation reservation;

    @Builder
    private Review(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String comment, Integer rating, Reservation reservation) {
        super(id, createdAt, updatedAt);
        this.comment = comment;
        this.rating = rating;
        this.reservation = reservation;
    }
}
