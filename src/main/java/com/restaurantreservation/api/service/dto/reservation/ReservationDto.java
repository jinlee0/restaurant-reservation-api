package com.restaurantreservation.api.service.dto.reservation;

import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReservationDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dateTime;
    private Integer numberOfPeople;
    private String requirements;
    private String contactPhoneNumber;
    private ReservationType type;
    private LocalDateTime typeSetAt;
    private String customerId;
    private String restaurantId;

    public static ReservationDto from(Reservation r) {
        return ReservationDto
            .builder()
            .id(r.getId())
            .createdAt(r.getCreatedAt())
            .updatedAt(r.getUpdatedAt())
            .dateTime(r.getDateTime())
            .numberOfPeople(r.getNumberOfPeople())
            .requirements(r.getRequirements())
            .contactPhoneNumber(r.getContactPhoneNumber())
            .type(r.getStatus().getType())
            .typeSetAt(r.getStatus().getTypeSetAt())
            .customerId(r.getCustomer().getId())
            .restaurantId(r.getRestaurant().getId())
            .build();
    }

}
