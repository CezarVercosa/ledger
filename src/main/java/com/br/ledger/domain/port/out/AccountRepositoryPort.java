package com.br.ledger.domain.port.out;

import com.br.ledger.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {
    Account save(Account account);

    boolean existsById(UUID accountId);

    List<Account> findAll();
}