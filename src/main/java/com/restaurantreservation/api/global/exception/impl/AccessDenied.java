package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class AccessDenied extends RestException {
    public AccessDenied() {
        super(ErrorCode.FORBIDDEN);
    }
}
