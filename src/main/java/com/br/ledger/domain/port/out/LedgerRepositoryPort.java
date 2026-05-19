package com.br.ledger.domain.port.out;

import com.br.ledger.domain.model.LedgerTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LedgerRepositoryPort {
    LedgerTransaction save(LedgerTransaction transaction);

    BigDecimal getBalance(UUID accountId);

    List<LedgerTransaction> findStatementByAccountId(UUID accountId);
}