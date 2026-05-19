package com.br.ledger.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record StatementItem(
        UUID transactionId,
        String description,
        BigDecimal amount,
        Instant createdAt
) {
}