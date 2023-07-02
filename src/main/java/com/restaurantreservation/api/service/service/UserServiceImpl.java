package com.restaurantreservation.api.service.service;

import com.restaurantreservation.api.global.exception.impl.InternalServerError;
import com.restaurantreservation.api.global.exception.impl.UserEmailAlreadyExists;
import com.restaurantreservation.api.global.exception.impl.UserRoleIsAlreadyPartner;
import com.restaurantreservation.api.global.util.SecurityUtil;
import com.restaurantreservation.api.service.dto.user.SignupDto;
import com.restaurantreservation.api.service.dto.user.UserDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.type.UserRole;
import com.restaurantreservation.api.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto saveUser(SignupDto.Request dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new UserEmailAlreadyExists();
        User user = userRepository.save(User.builder()
                .email(dto.getEmail())
                .role(UserRole.CUSTOMER)
                .password(passwordEncoder.encode(dto.getPassword()))
                .build());
        return UserDto.from(user);
    }

    @Override
    @Transactional
    public UserDto updateUserRoleToPartner() {
        String userEmail = SecurityUtil.getUserEmail().orElseThrow(InternalServerError::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow(InternalServerError::new);
        if (user.isPartner()) throw new UserRoleIsAlreadyPartner();
        user.registerToPartner();
        return UserDto.from(user);
    }
}
