package com.restaurantreservation.api.global.util;

import com.restaurantreservation.api.global.exception.impl.InternalServerError;
import com.restaurantreservation.api.service.entity.user.User;
import com.restaurantreservation.api.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public static Optional<String> getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.debug("Authentication not found");
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            return Optional.ofNullable(((UserDetails) authentication.getPrincipal()).getUsername());
        } else {
            return Optional.ofNullable((String) authentication.getPrincipal());
        }
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.debug("Authentication not found");
            throw new InternalServerError();
        }

        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new InternalServerError();
        }
        return userRepository.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername()).orElseThrow(InternalServerError::new);
    }

}
