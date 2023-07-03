package com.restaurantreservation.api.service.repository;

import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    boolean existsByManager(User manager);

    Optional<Restaurant> findByManager(User manager);
}