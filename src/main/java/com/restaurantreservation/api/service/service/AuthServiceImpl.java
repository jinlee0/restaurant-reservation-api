package com.restaurantreservation.api.service.service;

import com.restaurantreservation.api.global.exception.impl.EmailNotFoundOnAuth;
import com.restaurantreservation.api.global.exception.impl.WrongPassword;
import com.restaurantreservation.api.global.security.JwtManager;
import com.restaurantreservation.api.service.dto.auth.AuthenticateDto;
import com.restaurantreservation.api.service.dto.auth.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtManager jwtManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public TokenDto authenticate(AuthenticateDto.Request dto) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authToken);
            return new TokenDto(jwtManager.appendPrefix(jwtManager.generate(authenticate)));
        } catch (BadCredentialsException e) {
            throw new WrongPassword();
        } catch (UsernameNotFoundException e) {
            throw new EmailNotFoundOnAuth();
        }
    }
}
