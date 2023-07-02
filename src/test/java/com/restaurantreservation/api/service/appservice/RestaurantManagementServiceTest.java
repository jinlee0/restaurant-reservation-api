package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.exception.impl.UserAlreadyHasRestaurant;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.type.UserRole;
import com.restaurantreservation.api.service.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RestaurantManagementServiceTest {
    @InjectMocks
    RestaurantManagementServiceImpl service;

    @Mock
    SecurityService securityService;
    @Mock
    RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("음식점 등록 성공")
    void save() {
        User manager = MockData.user(UserRole.ROLE_PARTNER);
        Restaurant exp = MockData.restaurant(manager);
        given(securityService.getUser())
            .willReturn(manager);
        given(restaurantRepository.save(any()))
            .willReturn(exp);

        RestaurantDto act = service.saveRestaurant(
            RestaurantRegistrationDto.Request
                .builder()
                .name(exp.getName())
                .description(exp.getDescription())
                .addressMain(exp.getLocation().getAddress().getMain())
                .addressDetail(exp.getLocation().getAddress().getDetail())
                .latitude(exp.getLocation().getCoords().getLatitude())
                .longitude(exp.getLocation().getCoords().getLongitude())
                .build()
        );

        assertNotNull(act);
        assertEquals(exp.getName(), act.getName());
        assertEquals(exp.getDescription(), act.getDescription());
        assertEquals(exp.getLocation().getAddress().getMain(), act.getAddressMain());
        assertEquals(exp.getLocation().getAddress().getDetail(), act.getAddressDetail());
        assertEquals(exp.getLocation().getCoords().getLatitude(), act.getLatitude());
        assertEquals(exp.getLocation().getCoords().getLongitude(), act.getLongitude());
        assertEquals(exp.getManager().getId(), act.getManagerId());
    }

    @Test
    @DisplayName("음식점 등록 실패: user already has restaurant")
    void alreadyHasRestaurant() {
        User manager = MockData.user(UserRole.ROLE_PARTNER);
        given(securityService.getUser())
            .willReturn(manager);
        given(restaurantRepository.existsByManager(any()))
            .willReturn(true);

        assertThrows(UserAlreadyHasRestaurant.class, () -> service.saveRestaurant(RestaurantRegistrationDto.Request.builder().build()));
    }
}