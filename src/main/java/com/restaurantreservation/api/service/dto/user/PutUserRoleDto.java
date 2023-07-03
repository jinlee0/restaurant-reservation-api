package com.restaurantreservation.api.service.dto.user;

import com.restaurantreservation.api.service.entity.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class PutUserRoleDto {

    @AllArgsConstructor
    @Data
    @Builder
    public static class Response {
        private String id;
        private String email;
        private UserRole role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response from(UserDto userDto) {
            return Response
                .builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .build();
        }
    }
}
