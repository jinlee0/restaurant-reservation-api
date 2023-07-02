package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.dto.restaurant.RetrieveRestaurantDto;
import org.springframework.data.domain.Pageable;

public interface RestaurantManagementController {
    RestaurantRegistrationDto.Response registerRestaurant(RestaurantRegistrationDto.Request dto);
    RetrieveRestaurantDto retrieveRestaurants(Pageable pageable);
}
