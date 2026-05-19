package com.br.ledger.application.service;

import com.br.ledger.application.port.in.GetBalanceUseCase;
import com.br.ledger.domain.exception.DomainException;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import com.br.ledger.domain.port.out.LedgerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBalanceService implements GetBalanceUseCase {
    private final AccountRepositoryPort accountRepositoryPort;
    private final LedgerRepositoryPort ledgerRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal execute(UUID accountId) {
        if (!accountRepositoryPort.existsById(accountId)) {
            throw new DomainException("Conta não encontrada.");
        }

        return ledgerRepositoryPort.getBalance(accountId);
    }
}