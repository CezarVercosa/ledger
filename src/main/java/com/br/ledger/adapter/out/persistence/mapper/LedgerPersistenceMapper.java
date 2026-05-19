package com.br.ledger.adapter.out.persistence.mapper;

import com.br.ledger.adapter.out.persistence.entity.LedgerEntryEntity;
import com.br.ledger.adapter.out.persistence.entity.LedgerTransactionEntity;
import com.br.ledger.domain.model.LedgerEntry;
import com.br.ledger.domain.model.LedgerTransaction;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LedgerPersistenceMapper {
    default LedgerTransactionEntity toEntity(LedgerTransaction transaction) {
        LedgerTransactionEntity entity = LedgerTransactionEntity.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();

        transaction.getEntries().forEach(entry ->
                entity.addEntry(toEntryEntity(entry))
        );

        return entity;
    }

    default LedgerEntryEntity toEntryEntity(LedgerEntry entry) {
        return LedgerEntryEntity.builder()
                .id(UUID.randomUUID())
                .accountId(entry.getAccountId())
                .amount(entry.getAmount())
                .build();
    }

    default LedgerTransaction toDomain(LedgerTransactionEntity entity) {
        List<LedgerEntry> entries = entity.getEntries().stream()
                .map(this::toEntryDomain)
                .toList();

        return LedgerTransaction.restore(
                entity.getId(),
                entity.getDescription(),
                entries,
                entity.getCreatedAt()
        );
    }

    default LedgerEntry toEntryDomain(LedgerEntryEntity entity) {
        return LedgerEntry.create(
                entity.getAccountId(),
                entity.getAmount()
        );
    }
}