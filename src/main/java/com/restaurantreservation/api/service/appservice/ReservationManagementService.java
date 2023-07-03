package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;

public interface ReservationManagementService {
    ReservationDto saveReservation(ReservationRequestDto.Request dto);
}
