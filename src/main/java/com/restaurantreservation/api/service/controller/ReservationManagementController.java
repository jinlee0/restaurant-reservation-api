package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.reservation.ReservationHandlingDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRetrieveDto;
import org.springframework.data.domain.Pageable;

public interface ReservationManagementController {
    ReservationRequestDto.Response requestReservation(ReservationRequestDto.Request dto);
    ReservationHandlingDto.Response handleReservation(
        String reservationId,
        ReservationHandlingDto.Request dto
    );

    ReservationRetrieveDto retrieveReservations(Pageable pageable);
}
