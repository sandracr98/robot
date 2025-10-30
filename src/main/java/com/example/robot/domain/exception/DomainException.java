package com.example.robot.domain.exception;



/**
 * Exception raised when a domain rule is violated.
 *
 * This is a semantic error in the business logic,
 * not a technical failure.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}