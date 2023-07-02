package com.restaurantreservation.api.global.exception;

import com.restaurantreservation.api.global.exception.impl.EmailNotFoundOnAuth;
import com.restaurantreservation.api.global.exception.impl.InternalServerError;
import com.restaurantreservation.api.global.exception.impl.InvalidApiArgument;
import com.restaurantreservation.api.global.exception.impl.WrongPassword;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponse> handleRestException(RestException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
            .body(new ErrorResponse(ex, request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return handleRestException(new InvalidApiArgument(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException ex, HttpServletRequest request) {
        return handleRestException(
            switch (ex) {
                case BadCredentialsException ignored -> new WrongPassword();
                case UsernameNotFoundException ignored -> new EmailNotFoundOnAuth();
                default -> new InternalServerError();
            },
            request
        );
    }
}
