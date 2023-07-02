package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;
import com.restaurantreservation.api.service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base}")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("${api.v1.auth.authenticate}")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateDto.Response authenticate(@RequestBody @Valid AuthenticateDto.Request loginRequestDto) {
        return AuthenticateDto.Response.from(authService.authenticate(loginRequestDto));
    }
}