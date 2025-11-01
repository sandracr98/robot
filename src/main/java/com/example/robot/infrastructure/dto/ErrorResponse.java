package com.example.robot.infrastructure.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse (

    String error,          // short code, e.g. "validation_error"
    String message,        // human-readable summary
    List<?> details,       // optional list of field errors or info
    String path,           // request path
    Instant timestamp      // server time
) { }
