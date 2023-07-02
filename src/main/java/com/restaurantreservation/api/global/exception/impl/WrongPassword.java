package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class WrongPassword extends RestException {
    public WrongPassword() {
        super(ErrorCode.AUTH_WRONG_PASSWORD);
    }
}
