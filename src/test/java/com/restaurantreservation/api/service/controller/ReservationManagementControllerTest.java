package com.restaurantreservation.api.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.config.JwtSecurityConfigAdapter;
import com.restaurantreservation.api.global.config.SecurityConfig;
import com.restaurantreservation.api.global.security.JwtAccessDeniedHandler;
import com.restaurantreservation.api.global.security.JwtAuthenticationEntryPoint;
import com.restaurantreservation.api.global.security.JwtManager;
import com.restaurantreservation.api.service.appservice.ReservationManagementServiceImpl;
import com.restaurantreservation.api.service.dto.reservation.ReservationDto;
import com.restaurantreservation.api.service.dto.reservation.ReservationRequestDto;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import com.restaurantreservation.api.service.entity.reservation.ReservationType;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.entity.user.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationManagementControllerImpl.class)
@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class, JwtSecurityConfigAdapter.class, JwtManager.class})
class ReservationManagementControllerTest {
    @MockBean
    ReservationManagementServiceImpl service;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${api.v1.base}")
    String base;
    @Value("${api.v1.reservation.save}")
    String savePath;

    @Test
    @DisplayName("예약 신청 성공")
    void save() throws Exception {
        User customer = MockData.securityUser(UserRole.ROLE_CUSTOMER);
        User manager = MockData.user(UserRole.ROLE_PARTNER);
        Restaurant restaurant = MockData.restaurant(manager);
        Reservation exp = MockData.reservation(customer, restaurant, ReservationType.REQUESTED);

        given(service.saveReservation(any()))
            .willReturn(
                ReservationDto
                    .builder()
                    .id(exp.getId())
                    .createdAt(exp.getCreatedAt())
                    .updatedAt(exp.getUpdatedAt())
                    .dateTime(exp.getDateTime())
                    .numberOfPeople(exp.getNumberOfPeople())
                    .requirements(exp.getRequirements())
                    .type(exp.getStatus().getType())
                    .typeSetAt(exp.getStatus().getTypeSetAt())
                    .customerId(exp.getCustomer().getId())
                    .restaurantId(exp.getRestaurant().getId())
                    .build()
            );

        mvc
            .perform(
                post(base + savePath)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            ReservationRequestDto.Request
                                .builder()
                                .dateTime(exp.getDateTime())
                                .numberOfPeople(exp.getNumberOfPeople())
                                .requirements(exp.getRequirements())
                                .restaurantId(exp.getRestaurant().getId())
                                .customerId(exp.getCustomer().getId())
                                .build()
                        )
                    )
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(exp.getId()))
            .andExpect(jsonPath("$.createdAt").value(exp.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .andExpect(jsonPath("$.dateTime").value(exp.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .andExpect(jsonPath("$.numberOfPeople").value(exp.getNumberOfPeople()))
            .andExpect(jsonPath("$.requirements").value(exp.getRequirements()))
            .andExpect(jsonPath("$.restaurantId").value(exp.getRestaurant().getId()))
            .andExpect(jsonPath("$.customerId").value(exp.getCustomer().getId()));

    }
}