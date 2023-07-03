package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.AuthService;
import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base}")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("${api.auth.authenticate}")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateDto.Response authenticate(@RequestBody @Valid AuthenticateDto.Request loginRequestDto) {
        return AuthenticateDto.Response.from(authService.authenticate(loginRequestDto));
    }
}
