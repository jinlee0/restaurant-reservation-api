package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.user.RegisterUserAsPartnerDto;
import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base}")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PostMapping("${api.v1.user.signup}")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupDto.Response signup(@RequestBody @Valid SignupDto.Request dto) {
        return SignupDto.Response.from(userService.saveUser(dto));
    }

    @Override
    @PostMapping("${api.v1.user.register-user-as-partner}")
    @ResponseStatus(HttpStatus.OK)
    public RegisterUserAsPartnerDto.Response registerCustomerAsPartner() {
        return RegisterUserAsPartnerDto.Response.from(userService.updateUserRoleToPartner());
    }
}
