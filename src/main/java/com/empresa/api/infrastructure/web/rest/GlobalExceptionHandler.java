package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.infrastructure.config.AppMessages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AppMessages messages;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
        log.warn("Error de validación @Valid: {}", errores);
        return ResponseEntity.badRequest().body(error(messages.getErrorValidacion(), errores));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<UserResponse> handleBusinessErrors(IllegalArgumentException ex) {
        log.warn("Error de regla de negocio: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(error(messages.getErrorNegocio(), ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UserResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Violación de integridad en BD: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(error(messages.getErrorDuplicadoBd(), messages.getErrorDuplicadoBdDetalle()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserResponse> handleNotFound(RuntimeException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(404).body(error(messages.getErrorNoEncontrado(), ex.getMessage()));
    }

    private UserResponse error(String mensaje, String detalle) {
        return UserResponse.builder()
            .status("ERROR")
            .message(mensaje)
            .error(detalle)
            .timestamp(LocalDateTime.now().toString())
            .build();
    }
}
