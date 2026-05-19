package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LedgerTransactionTest {
    @Test
    void shouldCreateTransferSuccessfully() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        BigDecimal amount = BigDecimal.valueOf(100);
        String description = "Teste";

        LedgerTransaction transaction = LedgerTransaction.transfer(
                from,
                to,
                amount,
                description
        );

        assertNotNull(transaction.getId());

        assertEquals(
                description,
                transaction.getDescription()
        );

        assertEquals(
                2,
                transaction.getEntries().size()
        );

        BigDecimal totalAmount = transaction.getEntries()
                .stream()
                .map(LedgerEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(
                BigDecimal.ZERO,
                totalAmount
        );
    }

    @Test
    void shouldThrowExceptionWhenTransactionDoesNotBalance() {
        UUID accountA = UUID.randomUUID();
        UUID accountB = UUID.randomUUID();

        LedgerEntry debitEntry =
                LedgerEntry.create(accountA, BigDecimal.valueOf(100));

        LedgerEntry creditEntry =
                LedgerEntry.create(accountB, BigDecimal.valueOf(50));

        List<LedgerEntry> entries = List.of(
                debitEntry,
                creditEntry
        );

        UUID transactionId = UUID.randomUUID();
        String description = "Inválida";
        Instant createdAt = Instant.now();

        assertThrows(DomainException.class, () ->
                LedgerTransaction.restore(
                        transactionId,
                        description,
                        entries,
                        createdAt
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenTransferAmountIsZero() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        BigDecimal amount = BigDecimal.ZERO;
        String description = "Inválida";

        assertThrows(DomainException.class, () ->
                LedgerTransaction.transfer(
                        from,
                        to,
                        amount,
                        description
                )
        );
    }
}