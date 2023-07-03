package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class ReservationNotFound extends RestException {
    public ReservationNotFound() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
