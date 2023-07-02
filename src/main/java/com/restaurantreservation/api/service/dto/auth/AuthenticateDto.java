package com.restaurantreservation.api.service.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

public class AuthenticateDto {
    @Data
    @Builder
    public static class Request {
        @Email
        private String email;
        @Size(min = 4, max = 20)
        private String password;
    }

    @Data
    @Builder
    public static class Response {
        private String token;

        public static Response from(TokenDto dto) {
            return new Response(dto.getToken());
        }
    }
}
