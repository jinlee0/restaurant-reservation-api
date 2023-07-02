package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.global.exception.impl.UserEmailAlreadyExists;
import com.restaurantreservation.api.global.exception.impl.UserRoleIsAlreadyPartner;
import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.dto.user.UserDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
import com.restaurantreservation.api.service.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    SecurityService securityService;

    @Test
    @DisplayName("save 성공")
    void save() {
        LocalDateTime now = LocaleUtil.DateTime.now();
        User exp = User.builder()
            .id(UUID.randomUUID().toString())
            .email("email@a.com")
            .password("pass1234")
            .role(UserRole.ROLE_CUSTOMER)
            .createdAt(now)
            .updatedAt(now)
            .build();
        given(userRepository.existsByEmail(any()))
            .willReturn(false);
        given(userRepository.save(any()))
            .willReturn(exp);

        UserDto act = userService.saveUser(
            SignupDto.Request
                .builder()
                .email(exp.getEmail())
                .password(exp.getPassword())
                .build()
        );

        assertNotNull(act);
        assertEquals(exp.getEmail(), act.getEmail());
        assertEquals(exp.getRole(), act.getRole());
        assertEquals(exp.getCreatedAt(), act.getCreatedAt());
        assertEquals(exp.getUpdatedAt(), act.getUpdatedAt());
    }


    @Test
    @DisplayName("save 실패: 이메일 중복")
    void saveEmailDuplication() {
        given(userRepository.existsByEmail(any()))
            .willReturn(true);

        assertThrows(UserEmailAlreadyExists.class, () -> userService.saveUser(
            SignupDto.Request
                .builder()
                .email("email@a.com")
                .password("asdf1234")
                .build()
        ));
    }

    @Test
    @DisplayName("update role to partner 성공")
    void updateRoleToPartner() {
        LocalDateTime now = LocaleUtil.DateTime.now();
        User exp = User.builder()
            .id(UUID.randomUUID().toString())
            .email("email@a.com")
            .password("pass1234")
            .role(UserRole.ROLE_CUSTOMER)
            .createdAt(now)
            .updatedAt(now)
            .build();
        given(securityService.getUser())
            .willReturn(exp);

        UserDto act = userService.updateUserRoleToPartner();

        assertNotNull(act);
        assertEquals(exp.getEmail(), act.getEmail());
        assertEquals(UserRole.ROLE_PARTNER, act.getRole());
        assertEquals(exp.getCreatedAt(), act.getCreatedAt());
        assertEquals(exp.getUpdatedAt(), act.getUpdatedAt());
    }

    @Test
    @DisplayName("update role to partner 실패: already partner")
    void alreadyPartner() {
        given(securityService.getUser())
            .willReturn(User.builder().role(UserRole.ROLE_PARTNER).build());
        assertThrows(UserRoleIsAlreadyPartner.class, () -> userService.updateUserRoleToPartner());
    }
}