package com.restaurantreservation.api.service.dto.restaurant;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class RestaurantRegistrationDto {
    @Data
    @Builder
    public static class Request {
        @NotBlank
        private String name;
        @Size(max = 100)
        private String description = "";
        @NotBlank
        private String addressMain;
        private String addressDetail = "";
        @NotNull
        @Min(-90)
        @Max(90)
        private Double latitude;
        @NotNull
        @Min(-180)
        @Max(180)
        private Double longitude;
    }

    @Data
    @Builder
    public static class Response {
        private String id;
        private String name;
        private String description;
        private String addressMain;
        private String addressDetail;
        private Double latitude;
        private Double longitude;
        private String managerId;
        private LocalDateTime createdAt;

        public static Response from(RestaurantDto dto) {
            return Response.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .addressMain(dto.getAddressMain())
                .addressDetail(dto.getAddressDetail())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .managerId(dto.getManagerId())
                .createdAt(dto.getCreatedAt())
                .build();
        }
    }
}
