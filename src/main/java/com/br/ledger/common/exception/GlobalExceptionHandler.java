package com.br.ledger.common.exception;

import com.br.ledger.domain.exception.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String TRACE_ID_KEY = "traceId";

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(
            DomainException exception,
            HttpServletRequest request
    ) {
        String traceId = getOrCreateTraceId();

        log.warn(
                "Domain exception. traceId={}, path={}, message={}",
                traceId,
                request.getRequestURI(),
                exception.getMessage()
        );

        ErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                traceId,
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String traceId = getOrCreateTraceId();

        List<FieldErrorResponse> fields = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        log.warn(
                "Validation exception. traceId={}, path={}, fields={}",
                traceId,
                request.getRequestURI(),
                fields
        );

        ErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação nos campos enviados.",
                request.getRequestURI(),
                traceId,
                fields
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {
        String traceId = getOrCreateTraceId();

        log.error(
                "Unexpected exception. traceId={}, path={}",
                traceId,
                request.getRequestURI(),
                exception
        );

        ErrorResponse response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno inesperado.",
                request.getRequestURI(),
                traceId,
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String message,
            String path,
            String traceId,
            List<FieldErrorResponse> fields
    ) {
        return new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                traceId,
                fields
        );
    }

    private String getOrCreateTraceId() {
        String traceId = MDC.get(TRACE_ID_KEY);

        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
            MDC.put(TRACE_ID_KEY, traceId);
        }

        return traceId;
    }
}