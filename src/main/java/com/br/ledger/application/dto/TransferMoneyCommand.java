package com.br.ledger.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyCommand(
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        String description
) {
}