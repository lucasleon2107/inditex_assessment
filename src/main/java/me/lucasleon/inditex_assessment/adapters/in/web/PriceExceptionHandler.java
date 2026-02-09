package me.lucasleon.inditex_assessment.adapters.in.web;

import me.lucasleon.inditex_assessment.application.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class PriceExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<PriceErrorResponse> handleNotFound(PriceNotFoundException ex, ServerHttpRequest request) {
        PriceErrorResponse body = new PriceErrorResponse(
                OffsetDateTime.now(ZoneOffset.UTC),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getPath().value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
