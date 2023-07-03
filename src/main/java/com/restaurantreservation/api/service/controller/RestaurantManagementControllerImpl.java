package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.RestaurantManagementService;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRetrieveDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base}")
@RequiredArgsConstructor

public class RestaurantManagementControllerImpl implements RestaurantManagementController {
    private final RestaurantManagementService restaurantManagementService;
    @Override
    @PostMapping("${api.v1.restaurant.save}")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantRegistrationDto.Response registerRestaurant(@RequestBody @Valid RestaurantRegistrationDto.Request dto) {
        return RestaurantRegistrationDto.Response.from(restaurantManagementService.saveRestaurant(dto));
    }

    @Override
    @GetMapping("${api.v1.restaurant.list}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantRetrieveDto retrieveRestaurants(Pageable pageable) {
        return RestaurantRetrieveDto.from(restaurantManagementService.retrieveRestaurants(pageable));
    }

}
