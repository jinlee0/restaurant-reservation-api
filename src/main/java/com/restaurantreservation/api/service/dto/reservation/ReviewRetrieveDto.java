package com.restaurantreservation.api.service.dto.reservation;

import com.restaurantreservation.api.service.dto.PageDtoBase;
import com.restaurantreservation.api.service.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class ReviewRetrieveDto extends PageDtoBase<ReviewRetrieveDto.ReviewInfo> {
    public ReviewRetrieveDto(Page<Review> page) {
        super(page, page.getContent().stream().map(ReviewInfo::from).toList());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ReviewInfo {
        private String id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String comment;
        private Integer rating;
        private String reservationId;

        public static ReviewInfo from(Review r) {
            return ReviewInfo
                .builder()
                .id(r.getId())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .comment(r.getComment())
                .rating(r.getRating())
                .reservationId(r.getReservation().getId())
                .build();
        }
    }
}
