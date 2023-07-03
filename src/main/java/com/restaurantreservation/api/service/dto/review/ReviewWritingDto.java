package com.restaurantreservation.api.service.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewWritingDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {
        @Size(min = 5, max = 255)
        private String comment;
        @Min(1)
        @Max(5)
        private Integer rating;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private String id;
        private LocalDateTime createdAt;
        private String comment;
        private Integer rating;
        private String reservationId;

        public static Response from(ReviewDto dto) {
            return Response
                .builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .comment(dto.getComment())
                .rating(dto.getRating())
                .reservationId(dto.getReservationId())
                .build();
        }
    }
}
