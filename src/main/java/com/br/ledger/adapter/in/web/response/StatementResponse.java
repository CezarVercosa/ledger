package com.br.ledger.adapter.in.web.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record StatementResponse(
        UUID transactionId,
        String description,
        BigDecimal amount,
        Instant createdAt
) {
}