package com.restaurantreservation.api;

import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.service.entity.BaseEntity;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.restaurant.VoAddress;
import com.restaurantreservation.api.service.entity.restaurant.VoCoords;
import com.restaurantreservation.api.service.entity.restaurant.VoLocation;
import com.restaurantreservation.api.service.entity.type.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class MockData {
    public static User securityUser(String email, String password, UserRole role) {
        User user = User.builder()
            .email(email)
            .password(password)
            .role(role)
            .build();
        // unsafe code
        try {
            Field createdAt = BaseEntity.class.getDeclaredField("createdAt");
            createdAt.setAccessible(true);
            createdAt.set(user, LocaleUtil.DateTime.now());
            Field updatedAt = BaseEntity.class.getDeclaredField("updatedAt");
            updatedAt.setAccessible(true);
            updatedAt.set(user, LocaleUtil.DateTime.now());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //
        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(user.getEmail(), "", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(principal, "", user.getAuthorities())
        );
        return user;
    }

    public static User user(UserRole role) {
        LocalDateTime now = LocaleUtil.DateTime.now();
        return User
            .builder()
            .id(UUID.randomUUID().toString())
            .email("email@abc.com")
            .password("some encoded password")
            .createdAt(now)
            .updatedAt(now)
            .role(role)
            .build();
    }

    public static Restaurant restaurant(User manager) {
        return Restaurant
            .builder()
            .id(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .location(
                VoLocation
                    .builder()
                    .address(
                        VoAddress
                            .builder()
                            .main(UUID.randomUUID().toString())
                            .detail(UUID.randomUUID().toString())
                            .build()
                    ).coords(
                        VoCoords
                            .builder()
                            .latitude(new Random().nextDouble(-90, 91))
                            .longitude(new Random().nextDouble(-180, 181))
                            .build()
                    ).build()
            ).manager(manager)
            .build();
    }
}
