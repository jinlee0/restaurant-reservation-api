package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class InvalidApiArgument extends RestException {
    public InvalidApiArgument() {
        super(ErrorCode.INVALID_API_ARGUMENT);
    }
}
