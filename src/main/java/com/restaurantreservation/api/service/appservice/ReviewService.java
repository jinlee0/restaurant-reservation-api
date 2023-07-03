package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.reservation.ReviewRetrieveDto;
import com.restaurantreservation.api.service.dto.review.ReviewDto;
import com.restaurantreservation.api.service.dto.review.ReviewWritingDto;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewDto saveReview(String reservationId, ReviewWritingDto.Request dto);

    ReviewRetrieveDto retrieve(Pageable pageable);
}
