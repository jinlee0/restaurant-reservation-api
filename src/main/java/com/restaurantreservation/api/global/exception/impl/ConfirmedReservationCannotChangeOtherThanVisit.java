package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class ConfirmedReservationCannotChangeOtherThanVisit extends RestException {
    public ConfirmedReservationCannotChangeOtherThanVisit() {
        super(ErrorCode.CONFIRMED_RESERVATION_CANNOT_CHANGE_OTHER_THAN_VISIT);
    }
}
