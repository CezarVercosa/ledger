package com.br.ledger.adapter.out.persistence;

import com.br.ledger.adapter.out.persistence.entity.AccountEntity;
import com.br.ledger.adapter.out.persistence.mapper.AccountPersistenceMapper;
import com.br.ledger.adapter.out.persistence.repository.SpringDataAccountRepository;
import com.br.ledger.domain.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountPersistenceAdapterTest {
    @Mock
    private SpringDataAccountRepository repository;

    @Mock
    private AccountPersistenceMapper mapper;

    @InjectMocks
    private AccountPersistenceAdapter adapter;

    @Test
    void shouldSaveAccountSuccessfully() {
        Account account = Account.create("Conta Teste");

        AccountEntity entity = AccountEntity.builder()
                .id(account.getId())
                .name(account.getName())
                .createdAt(account.getCreatedAt())
                .build();

        when(mapper.toEntity(account)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(account);

        Account result = adapter.save(account);

        assertEquals(account, result);

        verify(repository).save(entity);
    }

    @Test
    void shouldReturnAllAccountsSuccessfully() {
        AccountEntity entity = AccountEntity.builder()
                .id(UUID.randomUUID())
                .name("Conta Teste")
                .createdAt(Instant.now())
                .build();

        Account account = Account.restore(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt()
        );

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(account);

        List<Account> result = adapter.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldCheckIfAccountExists() {
        UUID accountId = UUID.randomUUID();

        when(repository.existsById(accountId)).thenReturn(true);

        boolean result = adapter.existsById(accountId);

        assertTrue(result);
    }
}