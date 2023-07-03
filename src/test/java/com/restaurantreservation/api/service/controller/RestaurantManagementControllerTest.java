package com.restaurantreservation.api.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.config.JwtSecurityConfigAdapter;
import com.restaurantreservation.api.global.config.SecurityConfig;
import com.restaurantreservation.api.global.security.JwtAccessDeniedHandler;
import com.restaurantreservation.api.global.security.JwtAuthenticationEntryPoint;
import com.restaurantreservation.api.global.security.JwtManager;
import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.service.appservice.RestaurantManagementServiceImpl;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantManagementControllerImpl.class)
@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class, JwtSecurityConfigAdapter.class, JwtManager.class})
class RestaurantManagementControllerTest {
    @MockBean
    RestaurantManagementServiceImpl restaurantManagementService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${api.base}")
    String base;
    @Value("${api.restaurant.save}")
    String restaurantPath;

    @Test
    @DisplayName("음식점 등록 성공")
    void registerRestaurantBasicPath() throws Exception {
        User manager = MockData.securityUser(UserRole.ROLE_PARTNER);
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String addressMain = UUID.randomUUID().toString();
        String addressDetail = UUID.randomUUID().toString();
        Double latitude = new Random().nextDouble(-90, 91);
        Double longitude = new Random().nextDouble(-180, 181);
        LocalDateTime createdAt = LocaleUtil.DateTime.now();

        given(restaurantManagementService.saveRestaurant(any()))
            .willReturn(
                RestaurantDto.builder()
                    .name(name)
                    .managerId(manager.getId())
                    .description(description)
                    .addressMain(addressMain)
                    .addressDetail(addressDetail)
                    .latitude(latitude)
                    .longitude(longitude)
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .build()
            );

        mvc.perform(
                post(base + restaurantPath)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            RestaurantRegistrationDto.Request.builder()
                                .name(name)
                                .description(description)
                                .addressMain(addressMain)
                                .addressDetail(addressDetail)
                                .latitude(latitude)
                                .longitude(longitude)
                                .build()
                        ))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.description").value(description))
            .andExpect(jsonPath("$.addressMain").value(addressMain))
            .andExpect(jsonPath("$.addressDetail").value(addressDetail))
            .andExpect(jsonPath("$.latitude").value(latitude))
            .andExpect(jsonPath("$.longitude").value(longitude))
            .andExpect(jsonPath("$.managerId").value(manager.getId()))
            .andExpect(jsonPath("$.createdAt").value(createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }


    @Test
    @DisplayName("음식점 등록 실패: role")
    void registerWrongRole() throws Exception {
        MockData.securityUser(UserRole.ROLE_CUSTOMER);
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String addressMain = UUID.randomUUID().toString();
        String addressDetail = UUID.randomUUID().toString();
        Double latitude = new Random().nextDouble(-90, 91);
        Double longitude = new Random().nextDouble(-180, 181);

        mvc.perform(
                post(base + restaurantPath)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            RestaurantRegistrationDto.Request.builder()
                                .name(name)
                                .description(description)
                                .addressMain(addressMain)
                                .addressDetail(addressDetail)
                                .latitude(latitude)
                                .longitude(longitude)
                                .build()
                        ))
            )
            .andExpect(status().isForbidden());
    }
}