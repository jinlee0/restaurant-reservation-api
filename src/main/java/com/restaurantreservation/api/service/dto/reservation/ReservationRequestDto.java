package com.restaurantreservation.api.service.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReservationRequestDto {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private LocalDateTime dateTime;
        @NotNull
        @Min(1)
        private Integer numberOfPeople;
        private String requirements = "";
        @NotNull
        private String restaurantId;
        @NotNull
        private String customerId;
    }

    @Getter
    @AllArgsConstructor
    @Builder
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
