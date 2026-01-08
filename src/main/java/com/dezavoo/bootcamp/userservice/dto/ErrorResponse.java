package com.dezavoo.bootcamp.userservice.dto;

import java.time.OffsetDateTime;

public record ErrorResponse(
        String errorCode,
        String message,
        OffsetDateTime timestamp
 ) {
    public ErrorResponse(String errorCode, String message) {
        this(errorCode, message, OffsetDateTime.now());
    }
}
