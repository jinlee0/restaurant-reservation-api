package com.restaurantreservation.api.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_API_ARGUMENT(HttpStatus.BAD_REQUEST),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    USER_ROLE_ALREADY_PARTNER(HttpStatus.BAD_REQUEST),
    AUTH_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED),
    AUTH_EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED),
    USER_ALREADY_HAS_RESTAURANT(HttpStatus.BAD_REQUEST),
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_CONFIRM_OR_REFUSE(HttpStatus.BAD_REQUEST),
    NOT_REQUEST_STATUS(HttpStatus.BAD_REQUEST),
    CONFIRMED_RESERVATION_CANNOT_CHANGE_OTHER_THAN_VISIT(HttpStatus.BAD_REQUEST),
    TOO_CLOSE_RESERVATION_TIME(HttpStatus.BAD_REQUEST),
    NOT_REVIEWABLE_RESERVATION(HttpStatus.BAD_REQUEST),
    PARTNER_HAS_NO_RESTAURANT(HttpStatus.BAD_REQUEST)
    ;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = "";
    }

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
