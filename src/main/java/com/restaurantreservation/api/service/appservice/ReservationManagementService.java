package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationHandlingDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRetrieveDto;
import org.springframework.data.domain.Pageable;

public interface ReservationManagementService {
    ReservationDto saveReservation(ReservationRequestDto.Request dto);

    ReservationDto handleReservation(String reservationId, ReservationHandlingDto.Request dto);

    ReservationRetrieveDto retrieve(Pageable pageable);
}
