package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;
import com.restaurantreservation.api.service.dto.auth.TokenDto;

public interface AuthService {
    TokenDto authenticate(AuthenticateDto.Request dto);
}
