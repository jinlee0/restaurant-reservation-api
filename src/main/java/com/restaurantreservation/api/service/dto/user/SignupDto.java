package com.restaurantreservation.api.service.dto.user;

import com.restaurantreservation.api.service.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class SignupDto {
    @Data
    @Builder
    public static class Request {
        @Email
        private String email;
        @Size(min = 4, max = 20)
        private String password;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String id;
        private String email;
        private LocalDateTime createdAt;
        private UserRole role;

        public static Response from(UserDto userDto) {
            return Response
                .builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .createdAt(userDto.getCreatedAt())
                .role(userDto.getRole())
                .build();
        }
    }
}
