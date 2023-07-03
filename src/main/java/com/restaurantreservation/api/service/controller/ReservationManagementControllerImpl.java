package com.restaurantreservation.api.service.controller;

import com.restaurantreservation.api.service.appservice.ReservationManagementService;
import com.restaurantreservation.api.service.dto.reservation.ReservationHandlingDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRetrieveDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api..base}")
public class ReservationManagementControllerImpl implements ReservationManagementController {
    private final ReservationManagementService reservationManagementService;

    @Override
    @PostMapping("${api.reservation.save}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationRequestDto.Response requestReservation(@RequestBody @Valid ReservationRequestDto.Request dto) {
        return ReservationRequestDto.Response.from(reservationManagementService.saveReservation(dto));
    }

    @Override
    @PutMapping("${api.reservation.update-type}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationHandlingDto.Response handleReservation(
        @PathVariable String reservationId,
        @RequestBody @Valid ReservationHandlingDto.Request dto
    ) {
        return ReservationHandlingDto.Response.from(reservationManagementService.handleReservation(reservationId, dto));
    }

    @Override
    @GetMapping("${api.reservation.list}")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    public ReservationRetrieveDto retrieveReservations(Pageable pageable) {
        return reservationManagementService.retrieve(pageable);
    }
}
