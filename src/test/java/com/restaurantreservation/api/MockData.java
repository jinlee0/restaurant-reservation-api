package com.restaurantreservation.api;

import com.restaurantreservation.api.global.util.LocaleUtil;
import com.restaurantreservation.api.service.entity.BaseEntity;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;

public class MockData {
    public static User user(String email, String password, UserRole role) {
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
}
