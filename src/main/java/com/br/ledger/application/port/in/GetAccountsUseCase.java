package com.br.ledger.application.port.in;

import com.br.ledger.domain.model.Account;

import java.util.List;

public interface GetAccountsUseCase {
    List<Account> execute();
}