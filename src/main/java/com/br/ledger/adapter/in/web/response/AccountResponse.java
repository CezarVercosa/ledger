package com.br.ledger.adapter.in.web.response;

import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String name,
        Instant createdAt
) {
}