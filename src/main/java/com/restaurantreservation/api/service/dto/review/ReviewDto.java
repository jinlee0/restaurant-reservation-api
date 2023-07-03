package com.restaurantreservation.api.service.dto.review;

import com.restaurantreservation.api.service.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewDto {
    private String id;
    private LocalDateTime createdAt;
    private String comment;
    private Integer rating;
    private String reservationId;

    public static ReviewDto from (Review r) {
        return ReviewDto
            .builder()
            .id(r.getId())
            .createdAt(r.getCreatedAt())
            .comment(r.getComment())
            .rating(r.getRating())
            .reservationId(r.getReservation().getId())
            .build();
    }
}
