package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.global.exception.impl.*;
import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationHandlingDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRetrieveDto;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import com.restaurantreservation.api.service.entity.reservation.VoReservationStatus;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.repository.ReservationRepository;
import com.restaurantreservation.api.service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
        if (dto.getDateTime().isBefore(LocaleUtil.DateTime.now().plusMinutes(20)))
            // 예약 시간이 현재로부터 20분 후보다 가까운 경우 예약 불가.
            throw new TooCloseReservationTime();
        Reservation reservation = reservationRepository.save(
            Reservation
                .builder()
                .dateTime(dto.getDateTime())
                .numberOfPeople(dto.getNumberOfPeople())
                .requirements(dto.getRequirements())
                .contactPhoneNumber(dto.getContactPhoneNumber())
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

    @Override
    @Transactional
    public ReservationDto handleReservation(String reservationId, ReservationHandlingDto.Request dto) {
        User manager = securityService.getUser();
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFound::new);
        if (!reservation.getRestaurant().getManager().getId().equals(manager.getId()))
            throw new AccessDenied();

        ReservationType nextType = dto.getType();
        if (nextType != ReservationType.CONFIRMED && nextType != ReservationType.REFUSED && nextType != ReservationType.VISITED)
            throw new NotConfirmOrRefuseOrVisitNext();

        ReservationType prevType = reservation.getStatus().getType();
        if (prevType != ReservationType.REQUESTED && prevType != ReservationType.CONFIRMED)
            throw new NotRequestOrConfirmPrev();

        switch (prevType) {
            // 매니저가 예약을 확정 또는 반려한다. REQUESTED -> CONFIRMED or REFUSED
            case REQUESTED -> confirmOrRefuse(reservation, nextType);
            // 키오스크(매니저)가 고객 방문을 확인한다. CONFIRMED -> VISITED
            case CONFIRMED -> visit(reservation, nextType);
        }

        return ReservationDto.from(reservation);
    }

    private static void visit(Reservation reservation, ReservationType nextType) {
        if (nextType != ReservationType.VISITED)
            throw new ConfirmedReservationCannotChangeOtherThanVisit();
        if (LocaleUtil.DateTime.now().isAfter(reservation.getDateTime().minusMinutes(10)))
            // 체크인 시간(에약 시간 10분 전) 보다 늦은 경우
            throw new CheckInTooLate();
        reservation.visit();
    }

    private static void confirmOrRefuse(Reservation reservation, ReservationType nextType) {
        switch (nextType) {
            case CONFIRMED -> reservation.confirm();
            case REFUSED -> reservation.refuse();
            default -> throw new InternalServerError(); // Unreachable
        }
    }

    @Override
    public ReservationRetrieveDto retrieve(Pageable pageable) {
        User manager = securityService.getUser();
        if (!manager.isPartner())
            throw new AccessDenied();
        Page<Reservation> page = reservationRepository.findAllByManager(pageable);
        return ReservationRetrieveDto.from(page);
    }
}