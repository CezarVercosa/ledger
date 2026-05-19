package com.br.ledger.domain.model;

import com.br.ledger.domain.exception.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Account {
    private final UUID id;
    private final String name;
    private final Instant createdAt;

    private Account(
            UUID id,
            String name,
            Instant createdAt
    ) {
        validate(id, name, createdAt);

        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Account create(String name) {
        return new Account(
                UUID.randomUUID(),
                name,
                Instant.now()
        );
    }

    public static Account restore(
            UUID id,
            String name,
            Instant createdAt
    ) {
        return new Account(
                id,
                name,
                createdAt
        );
    }

    private void validate(
            UUID id,
            String name,
            Instant createdAt
    ) {
        Objects.requireNonNull(id, "O id da conta é obrigatório.");
        Objects.requireNonNull(createdAt, "A data de criação da conta é obrigatória.");

        if (name == null || name.isBlank()) {
            throw new DomainException("O nome da conta é obrigatório.");
        }
    }
}