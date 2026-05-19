package com.br.ledger.application.service;

import com.br.ledger.application.port.in.GetAccountsUseCase;
import com.br.ledger.domain.model.Account;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAccountsService implements GetAccountsUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<Account> execute() {
        return accountRepositoryPort.findAll();
    }
}