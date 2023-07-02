package com.restaurantreservation.api.service.repository;

import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    boolean existsByManager(User manager);
}