package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class CheckInTooLate extends RestException {
    public CheckInTooLate() {
        super(ErrorCode.BAD_REQUEST);
    }
}
