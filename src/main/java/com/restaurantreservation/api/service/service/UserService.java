package com.restaurantreservation.api.service.service;

import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.dto.user.UserDto;

public interface UserService {
    UserDto saveUser(SignupDto.Request dto);

    UserDto updateUserRoleToPartner();
}
