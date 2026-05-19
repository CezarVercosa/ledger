package com.br.ledger.application.service;

import com.br.ledger.application.dto.CreateAccountCommand;
import com.br.ledger.domain.model.Account;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAccountServiceTest {
    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @InjectMocks
    private CreateAccountService service;

    @Test
    void shouldCreateAccountSuccessfully() {
        CreateAccountCommand command =
                new CreateAccountCommand("Conta Teste");

        when(accountRepositoryPort.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account result = service.execute(command);

        assertNotNull(result);
        assertEquals("Conta Teste", result.getName());
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
    }
}