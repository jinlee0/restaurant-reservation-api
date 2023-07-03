package com.restaurantreservation.api.service.dto.reservation;

import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReservationHandlingDto {
    @AllArgsConstructor
    @Builder
    @Getter
    @NoArgsConstructor
    public static class Request {
        private ReservationType type;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private String id;
        private LocalDateTime createdAt;
        private LocalDateTime dateTime;
        private Integer numberOfPeople;
        private String requirements;
        private String restaurantId;
        private String customerId;

        public static Response from(ReservationDto dto) {
            return Response
                .builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .dateTime(dto.getDateTime())
                .numberOfPeople(dto.getNumberOfPeople())
                .requirements(dto.getRequirements())
                .restaurantId(dto.getRestaurantId())
                .customerId(dto.getCustomerId())
                .build();
        }

    }
}
