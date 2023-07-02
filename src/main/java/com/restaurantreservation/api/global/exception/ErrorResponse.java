package com.restaurantreservation.api.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String requestUri;

    public ErrorResponse(RestException ex, String requestURI) {
        this.errorCode = ex.getErrorCode().name();
        this.requestUri = requestURI;
    }
}
