package com.restaurantreservation.api.service.repository;

import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Page<Reservation> findAllByRestaurant(Restaurant restaurant, Pageable pageable);
}