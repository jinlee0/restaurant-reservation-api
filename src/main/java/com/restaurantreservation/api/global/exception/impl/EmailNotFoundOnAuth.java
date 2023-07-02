package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class EmailNotFoundOnAuth extends RestException {
    public EmailNotFoundOnAuth() {
        super(ErrorCode.AUTH_EMAIL_NOT_FOUND);
    }
}
