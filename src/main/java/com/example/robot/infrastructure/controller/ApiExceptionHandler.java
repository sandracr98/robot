package com.example.robot.infrastructure.controller;

import com.example.robot.domain.exception.DomainException;
import com.example.robot.infrastructure.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // 400: JSON malformado / tipos incorrectos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex, WebRequest req) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        "bad_request",
                        "Request body is invalid or unreadable.",
                        null,
                        path(req),
                        Instant.now()
                )
        );
    }

    // 400: @Valid en @RequestBody (DTOs con anotaciones)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        List<Map<String, String>> details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::fieldErrorToMap)
                .toList();
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        "validation_error",
                        "Some fields are invalid.",
                        details,
                        path(req),
                        Instant.now()
                )
        );
    }

    // 400: @Validated en params/path (ConstraintViolation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest req) {
        List<Map<String, String>> details = ex.getConstraintViolations().stream()
                .map(v -> Map.of("field", v.getPropertyPath().toString(), "message", v.getMessage()))
                .toList();
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        "validation_error",
                        "Some parameters are invalid.",
                        details,
                        path(req),
                        Instant.now()
                )
        );
    }

    // 422: reglas del dominio (negocio)
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ErrorResponse(
                        "domain_error",
                        ex.getMessage(),
                        null,
                        path(req),
                        Instant.now()
                )
        );
    }

    // 400: inputs inválidos genéricos lanzados por el propio código
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegal(IllegalArgumentException ex, WebRequest req) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        "bad_request",
                        ex.getMessage(),
                        null,
                        path(req),
                        Instant.now()
                )
        );
    }

    // 500: fallback (última red)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        "internal_error",
                        "Unexpected error.",
                        null,
                        path(req),
                        Instant.now()
                )
        );
    }

    private Map<String, String> fieldErrorToMap(FieldError fe) {
        return Map.of(
                "field", fe.getField(),
                "message", fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "invalid"
        );
    }

    private String path(WebRequest req) {
        String desc = req.getDescription(false); // e.g. "uri=/api/v1/robots/execute"
        int idx = desc.indexOf('=');
        return (idx >= 0 && idx + 1 < desc.length()) ? desc.substring(idx + 1) : desc;
    }

}
