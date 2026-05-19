package com.br.ledger.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank(message = "O nome da conta é obrigatório.")
        @Size(max = 150, message = "O nome da conta deve ter no máximo 150 caracteres.")
        String name
) {
}