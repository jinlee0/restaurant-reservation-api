package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class NotConfirmOrRefuseOrVisitNext extends RestException {
    public NotConfirmOrRefuseOrVisitNext() {
        super(ErrorCode.NOT_CONFIRM_OR_REFUSE);
    }
}
