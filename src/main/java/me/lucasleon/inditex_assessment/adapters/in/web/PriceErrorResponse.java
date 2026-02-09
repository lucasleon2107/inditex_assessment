package me.lucasleon.inditex_assessment.adapters.in.web;

import java.time.OffsetDateTime;

public record PriceErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
