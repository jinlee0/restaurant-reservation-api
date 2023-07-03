package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.ReservationManagementService;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1.base}")
public class ReservationManagementControllerImpl implements ReservationManagementController {
    private final ReservationManagementService reservationManagementService;
    @Override
    @PostMapping("${api.v1.reservation.save}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationRequestDto.Response requestReservation(@RequestBody @Valid ReservationRequestDto.Request dto) {
        return ReservationRequestDto.Response.from(reservationManagementService.saveReservation(dto));
    }
}
