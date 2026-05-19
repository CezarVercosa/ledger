package com.br.ledger.adapter.out.persistence;

import com.br.ledger.adapter.out.persistence.entity.LedgerEntryEntity;
import com.br.ledger.adapter.out.persistence.mapper.LedgerPersistenceMapper;
import com.br.ledger.adapter.out.persistence.repository.SpringDataLedgerEntryRepository;
import com.br.ledger.adapter.out.persistence.repository.SpringDataLedgerTransactionRepository;
import com.br.ledger.domain.model.LedgerTransaction;
import com.br.ledger.domain.port.out.LedgerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LedgerPersistenceAdapter implements LedgerRepositoryPort {
    private final SpringDataLedgerTransactionRepository transactionRepository;
    private final SpringDataLedgerEntryRepository entryRepository;
    private final LedgerPersistenceMapper mapper;

    @Override
    public LedgerTransaction save(LedgerTransaction transaction) {
        return mapper.toDomain(
                transactionRepository.save(mapper.toEntity(transaction))
        );
    }

    @Override
    public BigDecimal getBalance(UUID accountId) {
        return entryRepository.getBalanceByAccountId(accountId);
    }

    @Override
    public List<LedgerTransaction> findStatementByAccountId(UUID accountId) {
        return entryRepository.findByAccountIdOrderByTransactionCreatedAtDesc(accountId)
                .stream()
                .map(LedgerEntryEntity::getTransaction)
                .distinct()
                .map(mapper::toDomain)
                .toList();
    }
}