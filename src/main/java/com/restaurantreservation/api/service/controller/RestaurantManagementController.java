package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;

public interface RestaurantManagementController {
    RestaurantRegistrationDto.Response registerRestaurant(RestaurantRegistrationDto.Request dto);
}
