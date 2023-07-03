package com.restaurantreservation.api.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.config.JwtSecurityConfigAdapter;
import com.restaurantreservation.api.global.config.SecurityConfig;
import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.security.JwtAccessDeniedHandler;
import com.restaurantreservation.api.global.security.JwtAuthenticationEntryPoint;
import com.restaurantreservation.api.global.security.JwtManager;
import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.service.appservice.UserServiceImpl;
import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.dto.user.UserDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
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

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserControllerImpl.class)
@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class, JwtSecurityConfigAdapter.class, JwtManager.class})
class UserControllerTest {
    @MockBean
    UserServiceImpl userService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${api.v1.base}")
    String base;
    @Value("${api.v1.user.signup}")
    String signup;
    @Value("${api.v1.user.update-role}")
    String registerUserAsPartner;

    @Test
    @DisplayName("signup 성공")
    void signup_basic_path() throws Exception {
        String url = base + signup;
        String email = "email@a.com";
        String password = "asdf1234";
        UserRole role = UserRole.ROLE_CUSTOMER;
        LocalDateTime createdAt = LocaleUtil.DateTime.now();
        given(userService.saveUser(any()))
            .willReturn(
                UserDto.builder()
                    .email(email)
                    .createdAt(createdAt)
                    .role(role)
                    .build()
            );

        mvc.perform(
                post(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            SignupDto.Request.builder()
                                .email(email)
                                .password(password)
                                .build()
                        ))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.createdAt").value(createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .andExpect(jsonPath("$.role").value(role.toString()));

    }

    @Test
    @DisplayName("Signup 실패: validation")
    void signupBasicPath() throws Exception {

        mvc.perform(post(base + signup)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SignupDto.Request.builder()
                    .email("email@a.com")
                    .password("12")
                    .build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value(ErrorCode.INVALID_API_ARGUMENT.toString()));
    }

    @Test
    @DisplayName("registerUserAsPartner 성공")
    void registerUserAsPartnerBasicPath() throws Exception {
        User user = MockData.securityUser(UserRole.ROLE_CUSTOMER);
        given(
            userService.updateUserRoleToPartner(user.getId())
        ).willReturn(
            UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(UserRole.ROLE_PARTNER)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()
        );

        mvc
            .perform(
                post(base + registerUserAsPartner, user.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.role").value(UserRole.ROLE_PARTNER.toString()))
            .andExpect(jsonPath("$.createdAt").value(user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .andExpect(jsonPath("$.updatedAt").value(user.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }
}