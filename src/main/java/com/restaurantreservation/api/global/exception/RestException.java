package com.restaurantreservation.api.global.exception;

import lombok.Getter;

@Getter
public abstract class RestException extends RuntimeException {
    private final ErrorCode errorCode;
    public RestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
