package com.br.ledger.adapter.out.persistence.repository;

import com.br.ledger.adapter.out.persistence.entity.LedgerEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface SpringDataLedgerEntryRepository extends JpaRepository<LedgerEntryEntity, UUID> {

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM LedgerEntryEntity e
            WHERE e.accountId = :accountId
            """)
    BigDecimal getBalanceByAccountId(UUID accountId);

    List<LedgerEntryEntity> findByAccountIdOrderByTransactionCreatedAtDesc(UUID accountId);
}