package com.restaurantreservation.api.service.dto.user;

import com.restaurantreservation.api.service.entity.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class RegisterUserAsPartnerDto {

    @AllArgsConstructor
    @Data
    @Builder
    public static class Response {
        private String email;
        private UserRole role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response from(UserDto userDto) {
            return new Response(userDto.getEmail(), userDto.getRole(), userDto.getCreatedAt(), userDto.getUpdatedAt());
        }
    }
}
