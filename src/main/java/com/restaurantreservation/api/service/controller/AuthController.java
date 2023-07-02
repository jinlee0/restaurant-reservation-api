package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;

public interface AuthController {
    AuthenticateDto.Response authenticate(AuthenticateDto.Request loginRequestDto);
}
