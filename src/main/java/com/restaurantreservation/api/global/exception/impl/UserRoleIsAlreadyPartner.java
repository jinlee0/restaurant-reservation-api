package com.restaurantreservation.api.global.exception.impl;

import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.exception.RestException;

public class UserRoleIsAlreadyPartner extends RestException {
    public UserRoleIsAlreadyPartner() {
        super(ErrorCode.USER_ROLE_ALREADY_PARTNER);
    }
}
