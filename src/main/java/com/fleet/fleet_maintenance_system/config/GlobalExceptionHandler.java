package com.fleet.fleet_maintenance_system.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleNotValid(MethodArgumentNotValidException ex){
        Map<String, Object> response = new HashMap<>();
        String message = ex.getBindingResult().getFieldErrors().stream()
                        .map(err -> err.getField() + ": " + err.getDefaultMessage())
                                .reduce((a, b) -> a + "; " + b)
                                        .orElse("Validation failed");
        response.put("error", "Not Valid");
        response.put("message", message);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(IllegalArgumentException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("error", "Not Found");
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("timestamp", Instant.now().toString());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleNotAvailable(IllegalStateException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("error", "Conflict");
        response.put("status", HttpStatus.CONFLICT);
        response.put("timestamp", Instant.now().toString());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
