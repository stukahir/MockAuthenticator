package com.cvs.digital.hc.ams.MockAuthenticator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailureException.class)
    public ResponseEntity<AuthErrorResponse> handleAuthenticationException(AuthFailureException authException) {
        AuthErrorResponse errorResponse = new AuthErrorResponse(authException.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthErrorResponse> handleException(Exception exception) {
        if (IllegalArgumentException.class.isAssignableFrom(exception.getClass()) || MethodArgumentNotValidException.class.isAssignableFrom(exception.getClass())) {
            AuthErrorResponse errorResponse = new AuthErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        AuthErrorResponse errorResponse = new AuthErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
