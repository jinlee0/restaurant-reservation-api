package com.restaurantreservation.api.service.entity.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoReservationStatus {
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationType type;
    @Column(name = "type_set_at", nullable = false)
    private LocalDateTime typeSetAt;
}
