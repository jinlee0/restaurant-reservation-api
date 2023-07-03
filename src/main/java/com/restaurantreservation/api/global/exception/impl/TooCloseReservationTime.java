package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class TooCloseReservationTime extends RestException {
    public TooCloseReservationTime() {
        super(ErrorCode.TOO_CLOSE_RESERVATION_TIME);
    }
}
