package com.restaurantreservation.api.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreservation.api.MockData;
import com.restaurantreservation.api.global.config.JwtSecurityConfigAdapter;
import com.restaurantreservation.api.global.config.SecurityConfig;
import com.restaurantreservation.api.global.exception.ErrorCode;
import com.restaurantreservation.api.global.security.JwtAccessDeniedHandler;
import com.restaurantreservation.api.global.security.JwtAuthenticationEntryPoint;
import com.restaurantreservation.api.global.security.JwtManager;
import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;
import com.restaurantreservation.api.service.dto.auth.TokenDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
import com.restaurantreservation.api.service.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthControllerImpl.class)
@Import({SecurityConfig.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class, JwtSecurityConfigAdapter.class, JwtManager.class})
class AuthControllerTest {
    @MockBean
    AuthService authService;
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${api.v1.base}")
    String base;
    @Value("${api.v1.auth.authenticate}")
    String authenticate;

    @Test
    @DisplayName("authenticate 성공")
    void authenticateBasicPath() throws Exception {
        String rawPsd = "asdf1234";
        User user = MockData.user("email@a.com", rawPsd, UserRole.CUSTOMER);

        String token = "Bearer dkfjsdofkejfdkfj";
        given(
            authService.authenticate(any())
        ).willReturn(
            TokenDto
                .builder()
                .token(token)
                .build()
        );

        mvc.perform(
                post(base + authenticate)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            AuthenticateDto.Request.builder()
                                .email(user.getEmail())
                                .password(rawPsd)
                                .build()
                        ))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("authenticate 실패: 이메일 불량")
    void authenticateFail() throws Exception {
        given(authService.authenticate(any()))
            .willThrow(UsernameNotFoundException.class);

        mvc.perform(
                post(base + authenticate)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            AuthenticateDto.Request.builder()
                                .email("some@email.com")
                                .password("some_password")
                                .build()
                        ))
            ).andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorCode").value(ErrorCode.AUTH_EMAIL_NOT_FOUND.name()));
    }

    @Test
    @DisplayName("authenticate 실패: 비밀번호 불량")
    void failWrongPassword() throws Exception {
        given(authService.authenticate(any()))
            .willThrow(BadCredentialsException.class);
        mvc.perform(
                post(base + authenticate)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            AuthenticateDto.Request.builder()
                                .email("some@email.com")
                                .password("some_password")
                                .build()
                        ))
            ).andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorCode").value(ErrorCode.AUTH_WRONG_PASSWORD.name()));
    }
}