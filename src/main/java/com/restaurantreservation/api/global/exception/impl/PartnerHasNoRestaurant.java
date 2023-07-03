package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class PartnerHasNoRestaurant extends RestException {
    public PartnerHasNoRestaurant() {
        super(ErrorCode.PARTNER_HAS_NO_RESTAURANT);
    }
}
