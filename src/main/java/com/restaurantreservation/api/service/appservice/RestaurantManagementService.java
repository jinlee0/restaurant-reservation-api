package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.restaurant.RestaurantDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;

public interface RestaurantManagementService {
    RestaurantDto saveRestaurant(RestaurantRegistrationDto.Request dto);
}
