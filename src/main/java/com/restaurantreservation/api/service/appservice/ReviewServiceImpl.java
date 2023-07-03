package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.global.exception.impl.AccessDenied;
import com.restaurantreservation.api.global.exception.impl.NotReviewableReservation;
import com.restaurantreservation.api.global.exception.impl.ReservationNotFound;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.reservation.ReviewRetrieveDto;
import com.restaurantreservation.api.service.dto.review.ReviewDto;
import com.restaurantreservation.api.service.dto.review.ReviewWritingDto;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.review.Review;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.repository.ReservationRepository;
import com.restaurantreservation.api.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final SecurityService securityService;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewDto saveReview(String reservationId, ReviewWritingDto.Request dto) {
        User reviewer = securityService.getUser();
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(ReservationNotFound::new);
        if (!reviewer.getId().equals(reservation.getCustomer().getId()))
            throw new AccessDenied();
        if (!reservation.reviewable())
            throw new NotReviewableReservation();
        Review review = reviewRepository.save(
            Review
                .builder()
                .comment(dto.getComment())
                .rating(dto.getRating())
                .reservation(reservation)
                .build()
        );
        return ReviewDto.from(review);
    }

    @Override
    public ReviewRetrieveDto retrieve(Pageable pageable) {
        return new ReviewRetrieveDto(reviewRepository.findAll(pageable));
    }
}
