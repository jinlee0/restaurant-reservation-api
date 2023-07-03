package com.restaurantreservation.api.service.repository;

import com.restaurantreservation.api.service.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
}