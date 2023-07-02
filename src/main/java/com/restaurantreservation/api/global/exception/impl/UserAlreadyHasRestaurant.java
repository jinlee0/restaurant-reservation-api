package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class UserAlreadyHasRestaurant extends RestException {
    public UserAlreadyHasRestaurant() {
        super(ErrorCode.USER_ALREADY_HAS_RESTAURANT);
    }
}
