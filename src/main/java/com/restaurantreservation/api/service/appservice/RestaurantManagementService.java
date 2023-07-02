package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.restaurant.RestaurantDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantManagementService {
    RestaurantDto saveRestaurant(RestaurantRegistrationDto.Request dto);

    Page<Restaurant> retrieveRestaurants(Pageable pageable);
}
