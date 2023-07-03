package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRetrieveDto;
import org.springframework.data.domain.Pageable;

public interface RestaurantManagementController {
    RestaurantRegistrationDto.Response registerRestaurant(RestaurantRegistrationDto.Request dto);
    RestaurantRetrieveDto retrieveRestaurants(Pageable pageable);
}
