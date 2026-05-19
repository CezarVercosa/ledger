package com.br.ledger.application.service;

import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.application.mapper.StatementMapper;
import com.br.ledger.application.port.in.GetStatementUseCase;
import com.br.ledger.domain.exception.DomainException;
import com.br.ledger.domain.model.LedgerTransaction;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import com.br.ledger.domain.port.out.LedgerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetStatementService implements GetStatementUseCase {
    private final AccountRepositoryPort accountRepositoryPort;
    private final LedgerRepositoryPort ledgerRepositoryPort;
    private final StatementMapper statementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StatementItem> execute(UUID accountId) {
        if (!accountRepositoryPort.existsById(accountId)) {
            throw new DomainException("Conta não encontrada.");
        }

        List<LedgerTransaction> transactions =
                ledgerRepositoryPort.findStatementByAccountId(accountId);

        return statementMapper.toStatementItems(transactions, accountId);
    }
}