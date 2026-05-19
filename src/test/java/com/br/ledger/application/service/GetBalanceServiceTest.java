package com.br.ledger.application.service;

import com.br.ledger.domain.exception.DomainException;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import com.br.ledger.domain.port.out.LedgerRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBalanceServiceTest {
    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private LedgerRepositoryPort ledgerRepositoryPort;

    @InjectMocks
    private GetBalanceService service;

    @Test
    void shouldReturnBalanceSuccessfully() {
        UUID accountId = UUID.randomUUID();

        when(accountRepositoryPort.existsById(accountId))
                .thenReturn(true);

        when(ledgerRepositoryPort.getBalance(accountId))
                .thenReturn(BigDecimal.valueOf(1000));

        BigDecimal result = service.execute(accountId);

        assertEquals(BigDecimal.valueOf(1000), result);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        UUID accountId = UUID.randomUUID();

        when(accountRepositoryPort.existsById(accountId))
                .thenReturn(false);

        assertThrows(DomainException.class, () ->
                service.execute(accountId)
        );
    }
}