package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class LedgerTransaction {
    private final UUID id;
    private final String description;
    private final List<LedgerEntry> entries;
    private final Instant createdAt;

    private LedgerTransaction(
            UUID id,
            String description,
            List<LedgerEntry> entries,
            Instant createdAt
    ) {
        validate(id, entries, createdAt);

        this.id = id;
        this.description = description;
        this.entries = List.copyOf(entries);
        this.createdAt = createdAt;
    }

    public static LedgerTransaction transfer(
            UUID fromAccountId,
            UUID toAccountId,
            BigDecimal amount,
            String description
    ) {
        validateTransferAmount(amount);

        return new LedgerTransaction(
                UUID.randomUUID(),
                description,
                List.of(
                        LedgerEntry.create(fromAccountId, amount.negate()),
                        LedgerEntry.create(toAccountId, amount)
                ),
                Instant.now()
        );
    }

    public static LedgerTransaction restore(
            UUID id,
            String description,
            List<LedgerEntry> entries,
            Instant createdAt
    ) {
        return new LedgerTransaction(
                id,
                description,
                entries,
                createdAt
        );
    }

    private static void validate(
            UUID id,
            List<LedgerEntry> entries,
            Instant createdAt
    ) {
        Objects.requireNonNull(id, "O id da transação é obrigatório.");
        Objects.requireNonNull(createdAt, "A data da transação é obrigatória.");

        if (entries == null || entries.size() < 2) {
            throw new DomainException("Uma transação precisa ter pelo menos dois lançamentos.");
        }

        BigDecimal total = entries.stream()
                .map(LedgerEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(BigDecimal.ZERO) != 0) {
            throw new DomainException("A transação do ledger precisa fechar em zero.");
        }
    }

    private static void validateTransferAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "O valor da transferência é obrigatório.");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("O valor da transferência deve ser maior que zero.");
        }
    }
}