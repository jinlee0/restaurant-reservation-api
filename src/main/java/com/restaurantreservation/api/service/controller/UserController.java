package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.user.RegisterUserAsPartnerDto;
import com.restaurantreservation.api.service.dto.user.SignupDto;

public interface UserController {
    SignupDto.Response signup(SignupDto.Request dto);
    RegisterUserAsPartnerDto.Response registerCustomerAsPartner();
}
