package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void shouldCreateAccountSuccessfully() {
        String name = "Conta Teste";

        Account account = Account.create(name);

        assertNotNull(account.getId());
        assertEquals(name, account.getName());
        assertNotNull(account.getCreatedAt());
    }

    @Test
    void shouldRestoreAccountSuccessfully() {
        UUID id = UUID.randomUUID();
        String name = "Conta Restaurada";
        Instant createdAt = Instant.now();

        Account account = Account.restore(id, name, createdAt);

        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(createdAt, account.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        String name = "";

        assertThrows(DomainException.class, () ->
                Account.create(name)
        );
    }
}