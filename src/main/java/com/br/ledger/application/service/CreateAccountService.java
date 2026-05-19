package com.br.ledger.application.service;

import com.br.ledger.application.dto.CreateAccountCommand;
import com.br.ledger.application.port.in.CreateAccountUseCase;
import com.br.ledger.domain.model.Account;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    @Transactional
    public Account execute(CreateAccountCommand command) {
        Account account = Account.create(command.name());

        return accountRepositoryPort.save(account);
    }
}