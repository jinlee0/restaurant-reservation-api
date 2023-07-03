package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.entity.user.UserRole;
import com.restaurantreservation.api.service.repository.ReservationRepository;
import com.restaurantreservation.api.service.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReservationManagementTest {
    @InjectMocks
    ReservationManagementServiceImpl service;
    @Mock
    SecurityService securityService;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("예약 신청 성공")
    void save() {
        User customer = MockData.user(UserRole.ROLE_CUSTOMER);
        User manager = MockData.user(UserRole.ROLE_PARTNER);
        Restaurant restaurant = MockData.restaurant(manager);
        Reservation exp = MockData.reservation(customer, restaurant, ReservationType.REQUESTED);

        given(securityService.getUser())
            .willReturn(customer);
        given(restaurantRepository.findById(any()))
            .willReturn(Optional.of(restaurant));
        given(reservationRepository.save(any()))
            .willReturn(exp);

        ReservationDto act = service.saveReservation(
            ReservationRequestDto.Request
                .builder()
                .dateTime(exp.getDateTime())
                .numberOfPeople(exp.getNumberOfPeople())
                .requirements(exp.getRequirements())
                .restaurantId(exp.getRestaurant().getId())
                .customerId(exp.getCustomer().getId())
                .build()
        );

        assertNotNull(act);
        assertEquals(exp.getCustomer().getId(), act.getCustomerId());
        assertEquals(exp.getCreatedAt(), act.getCreatedAt());
        assertEquals(exp.getRestaurant().getId(), act.getRestaurantId());
        assertEquals(exp.getRequirements(), act.getRequirements());
        assertEquals(exp.getNumberOfPeople(), act.getNumberOfPeople());
        assertEquals(exp.getDateTime(), act.getDateTime());
        assertEquals(exp.getStatus().getType(), act.getType());
        assertEquals(exp.getStatus().getTypeSetAt(), act.getTypeSetAt());
    }
}