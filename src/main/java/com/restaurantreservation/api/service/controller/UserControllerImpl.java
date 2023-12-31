package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.UserService;
import com.restaurantreservation.api.service.dto.user.PutUserRoleDto;
import com.restaurantreservation.api.service.dto.user.SignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base}")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PostMapping("${api.user.signup}")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupDto.Response signup(@RequestBody @Valid SignupDto.Request dto) {
        return SignupDto.Response.from(userService.saveUser(dto));
    }

    @Override
    @PutMapping("${api.user.update-role}")
    @ResponseStatus(HttpStatus.OK)
    public PutUserRoleDto.Response registerCustomerAsPartner(@PathVariable String userId) {
        return PutUserRoleDto.Response.from(userService.updateUserRoleToPartner(userId));
    }
}
