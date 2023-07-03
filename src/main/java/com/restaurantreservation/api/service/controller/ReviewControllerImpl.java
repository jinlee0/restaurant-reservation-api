package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.ReviewService;
import com.restaurantreservation.api.service.dto.reservation.ReviewRetrieveDto;
import com.restaurantreservation.api.service.dto.review.ReviewWritingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base}")
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {
    private final ReviewService reviewService;
    @Override
    @PostMapping("${api.v1.review.save}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewWritingDto.Response writeReview(
        @PathVariable String reservationId,
        @RequestBody @Valid ReviewWritingDto.Request dto
    ) {
        return ReviewWritingDto.Response.from(reviewService.saveReview(reservationId, dto));
    }

    @Override
    @GetMapping("${api.v1.review.list}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewRetrieveDto retrieveReviews(Pageable pageable) {
        return reviewService.retrieve(pageable);
    }
}
