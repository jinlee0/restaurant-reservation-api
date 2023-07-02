package com.restaurantreservation.api.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
    public static Optional<String> getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            log.debug("Authentication not found");
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            return Optional.ofNullable(((UserDetails) authentication.getPrincipal()).getUsername());
        } else {
            return Optional.ofNullable((String) authentication.getPrincipal());
        }
    }
}
