package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class NotRequestOrConfirmPrev extends RestException {
    public NotRequestOrConfirmPrev() {
        super(ErrorCode.NOT_REQUEST_STATUS);
    }
}
