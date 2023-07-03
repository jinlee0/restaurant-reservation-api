package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.global.exception.impl.AccessDenied;
import com.restaurantreservation.api.global.exception.impl.RestaurantNotFound;
import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import com.restaurantreservation.api.service.entity.reservation.VoReservationStatus;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.repository.ReservationRepository;
import com.restaurantreservation.api.service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationManagementServiceImpl implements ReservationManagementService {
    private final SecurityService securityService;
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public ReservationDto saveReservation(ReservationRequestDto.Request dto) {
        User user = securityService.getUser();
        if (!user.getId().equals(dto.getCustomerId()))
            throw new AccessDenied();
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
            .orElseThrow(RestaurantNotFound::new);
        Reservation reservation = reservationRepository.save(
            Reservation
                .builder()
                .dateTime(dto.getDateTime())
                .numberOfPeople(dto.getNumberOfPeople())
                .requirements(dto.getRequirements())
                .status(
                    VoReservationStatus
                        .builder()
                        .type(ReservationType.REQUESTED)
                        .typeSetAt(LocaleUtil.DateTime.now())
                        .build()
                )
                .customer(user)
                .restaurant(restaurant)
                .build()
        );
        return ReservationDto.from(reservation);
    }
}
