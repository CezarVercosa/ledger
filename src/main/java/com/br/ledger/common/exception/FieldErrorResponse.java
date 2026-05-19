package com.br.ledger.common.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}