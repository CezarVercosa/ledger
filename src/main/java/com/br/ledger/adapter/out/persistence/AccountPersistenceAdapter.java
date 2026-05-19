package com.br.ledger.adapter.out.persistence;

import com.br.ledger.adapter.out.persistence.mapper.AccountPersistenceMapper;
import com.br.ledger.adapter.out.persistence.repository.SpringDataAccountRepository;
import com.br.ledger.domain.model.Account;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRepositoryPort {
    private final SpringDataAccountRepository repository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Account save(Account account) {
        return mapper.toDomain(
                repository.save(mapper.toEntity(account))
        );
    }

    @Override
    public boolean existsById(UUID accountId) {
        return repository.existsById(accountId);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}