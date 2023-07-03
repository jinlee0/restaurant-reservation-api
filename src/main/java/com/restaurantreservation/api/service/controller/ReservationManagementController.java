package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;

public interface ReservationManagementController {
    ReservationRequestDto.Response requestReservation(ReservationRequestDto.Request dto);
}
