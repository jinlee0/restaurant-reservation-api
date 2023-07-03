package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class Forbidden extends RestException {
    public Forbidden() {
        super(ErrorCode.FORBIDDEN);
    }
}
