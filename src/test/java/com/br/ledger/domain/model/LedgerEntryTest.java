package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LedgerEntryTest {
    @Test
    void shouldCreateLedgerEntrySuccessfully() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        LedgerEntry entry = LedgerEntry.create(accountId, amount);

        assertEquals(accountId, entry.getAccountId());
        assertEquals(amount, entry.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.ZERO;

        assertThrows(DomainException.class, () ->
                LedgerEntry.create(accountId, amount)
        );
    }

    @Test
    void shouldThrowExceptionWhenAccountIdIsNull() {
        BigDecimal amount = BigDecimal.valueOf(100);

        assertThrows(NullPointerException.class, () ->
                LedgerEntry.create(null, amount)
        );
    }
}