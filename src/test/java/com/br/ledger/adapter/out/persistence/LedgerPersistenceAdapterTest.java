package com.br.ledger.adapter.out.persistence;

import com.br.ledger.adapter.out.persistence.entity.LedgerEntryEntity;
import com.br.ledger.adapter.out.persistence.entity.LedgerTransactionEntity;
import com.br.ledger.adapter.out.persistence.mapper.LedgerPersistenceMapper;
import com.br.ledger.adapter.out.persistence.repository.SpringDataLedgerEntryRepository;
import com.br.ledger.adapter.out.persistence.repository.SpringDataLedgerTransactionRepository;
import com.br.ledger.domain.model.LedgerTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LedgerPersistenceAdapterTest {
    @Mock
    private SpringDataLedgerTransactionRepository transactionRepository;

    @Mock
    private SpringDataLedgerEntryRepository entryRepository;

    @Mock
    private LedgerPersistenceMapper mapper;

    @InjectMocks
    private LedgerPersistenceAdapter adapter;

    @Test
    void shouldSaveLedgerTransactionSuccessfully() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        LedgerTransaction transaction = LedgerTransaction.transfer(
                from,
                to,
                BigDecimal.valueOf(100),
                "Transferência"
        );

        LedgerTransactionEntity entity = LedgerTransactionEntity.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();

        when(mapper.toEntity(transaction)).thenReturn(entity);
        when(transactionRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(transaction);

        LedgerTransaction result = adapter.save(transaction);

        assertEquals(transaction, result);

        verify(transactionRepository).save(entity);
    }

    @Test
    void shouldReturnBalanceSuccessfully() {
        UUID accountId = UUID.randomUUID();

        when(entryRepository.getBalanceByAccountId(accountId))
                .thenReturn(BigDecimal.valueOf(500));

        BigDecimal result = adapter.getBalance(accountId);

        assertEquals(BigDecimal.valueOf(500), result);
    }

    @Test
    void shouldReturnStatementSuccessfully() {
        UUID accountId = UUID.randomUUID();

        LedgerTransactionEntity transactionEntity = LedgerTransactionEntity.builder()
                .id(UUID.randomUUID())
                .description("Teste")
                .createdAt(Instant.now())
                .build();

        LedgerEntryEntity entryEntity = LedgerEntryEntity.builder()
                .id(UUID.randomUUID())
                .accountId(accountId)
                .amount(BigDecimal.valueOf(100))
                .transaction(transactionEntity)
                .build();

        LedgerTransaction transaction = LedgerTransaction.transfer(
                UUID.randomUUID(),
                accountId,
                BigDecimal.valueOf(100),
                "Teste"
        );

        when(entryRepository.findByAccountIdOrderByTransactionCreatedAtDesc(accountId))
                .thenReturn(List.of(entryEntity));

        when(mapper.toDomain(transactionEntity))
                .thenReturn(transaction);

        List<LedgerTransaction> result = adapter.findStatementByAccountId(accountId);

        assertEquals(1, result.size());
    }
}