package org.bosf.moondance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public ApiException(String message, HttpStatus status) {
        super(message);

        this.status = status;
        this.code = null;
    }

    public ApiException(String message, HttpStatus status, String code) {
        super(message);
        
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends ApiException {
        public NotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND, "NOT_FOUND");
        }

        public NotFoundException(String entity, Long id) {
            super(entity + " not found with id: " + id, HttpStatus.NOT_FOUND, "NOT_FOUND");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends ApiException {
        public BadRequestException(String message) {
            super(message, HttpStatus.BAD_REQUEST, "BAD_REQUEST");
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends ApiException {
        public UnauthorizedException(String message) {
            super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenException extends ApiException {
        public ForbiddenException(String message) {
            super(message, HttpStatus.FORBIDDEN, "FORBIDDEN");
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class ConflictException extends ApiException {
        public ConflictException(String message) {
            super(message, HttpStatus.CONFLICT, "CONFLICT");
        }
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class RateLimitException extends ApiException {
        public RateLimitException(String message) {
            super(message, HttpStatus.TOO_MANY_REQUESTS, "RATE_LIMITED");
        }
    }
}
