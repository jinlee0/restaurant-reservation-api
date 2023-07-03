package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class NotReviewableReservation extends RestException {
    public NotReviewableReservation() {
        super(ErrorCode.NOT_REVIEWABLE_RESERVATION);
    }
}
