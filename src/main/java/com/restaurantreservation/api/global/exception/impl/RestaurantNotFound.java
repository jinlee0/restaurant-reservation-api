package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class RestaurantNotFound extends RestException {
    public RestaurantNotFound() {
        super(ErrorCode.RESTAURANT_NOT_FOUND);
    }
}
