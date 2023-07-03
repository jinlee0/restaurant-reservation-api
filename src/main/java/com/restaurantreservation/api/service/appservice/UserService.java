package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.dto.user.UserDto;

public interface UserService {
    UserDto saveUser(SignupDto.Request dto);

    UserDto updateUserRoleToPartner(String userId);
}
