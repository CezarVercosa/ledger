package com.br.ledger.application.service;

import com.br.ledger.domain.model.Account;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAccountsServiceTest {
    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @InjectMocks
    private GetAccountsService service;

    @Test
    void shouldReturnAllAccountsSuccessfully() {
        List<Account> accounts = List.of(
                Account.restore(
                        UUID.randomUUID(),
                        "Conta A",
                        Instant.now()
                ),
                Account.restore(
                        UUID.randomUUID(),
                        "Conta B",
                        Instant.now()
                )
        );

        when(accountRepositoryPort.findAll())
                .thenReturn(accounts);

        List<Account> result = service.execute();

        assertEquals(2, result.size());
    }
}