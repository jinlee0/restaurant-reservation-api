package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.reservation.ReviewRetrieveDto;
import com.restaurantreservation.api.service.dto.review.ReviewWritingDto;
import org.springframework.data.domain.Pageable;

public interface ReviewController {
    ReviewWritingDto.Response writeReview(
        String reservationId,
        ReviewWritingDto.Request dto
    );

    ReviewRetrieveDto retrieveReviews(Pageable pageable);
}
