package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class UserEmailAlreadyExists extends RestException {
    public UserEmailAlreadyExists() {
        super(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
    }
}
