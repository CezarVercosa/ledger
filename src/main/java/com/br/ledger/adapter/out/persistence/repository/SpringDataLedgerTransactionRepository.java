package com.br.ledger.adapter.out.persistence.repository;

import com.br.ledger.adapter.out.persistence.entity.LedgerTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataLedgerTransactionRepository extends JpaRepository<LedgerTransactionEntity, UUID> {
}