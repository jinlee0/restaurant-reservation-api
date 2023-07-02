package com.restaurantreservation.api.service.dto.user;

import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto from(User user) {
        return new UserDto(user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
