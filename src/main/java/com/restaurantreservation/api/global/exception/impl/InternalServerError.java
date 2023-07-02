package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class InternalServerError extends RestException {
    public InternalServerError() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
