package com.br.ledger.adapter.in.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyRequest(
        @NotNull(message = "A conta de origem é obrigatória.")
        UUID fromAccountId,

        @NotNull(message = "A conta de destino é obrigatória.")
        UUID toAccountId,

        @NotNull(message = "O valor é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        BigDecimal amount,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
        String description
) {
}