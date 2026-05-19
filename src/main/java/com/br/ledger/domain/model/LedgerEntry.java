package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class LedgerEntry {
    private final UUID accountId;
    private final BigDecimal amount;

    private LedgerEntry(
            UUID accountId,
            BigDecimal amount
    ) {
        validate(accountId, amount);

        this.accountId = accountId;
        this.amount = amount;
    }

    public static LedgerEntry create(
            UUID accountId,
            BigDecimal amount
    ) {
        return new LedgerEntry(
                accountId,
                amount
        );
    }

    private static void validate(
            UUID accountId,
            BigDecimal amount
    ) {
        Objects.requireNonNull(
                accountId,
                "A conta do lançamento é obrigatória."
        );

        Objects.requireNonNull(
                amount,
                "O valor do lançamento é obrigatório."
        );

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new DomainException(
                    "O valor do lançamento não pode ser zero."
            );
        }
    }
}