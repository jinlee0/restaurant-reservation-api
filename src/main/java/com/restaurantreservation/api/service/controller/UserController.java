package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.user.PutUserRoleDto;
import com.restaurantreservation.api.service.dto.user.SignupDto;

public interface UserController {
    SignupDto.Response signup(SignupDto.Request dto);
    PutUserRoleDto.Response registerCustomerAsPartner(String userId);
}
