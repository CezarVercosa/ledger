package com.br.ledger.application.service;

import com.br.ledger.application.dto.TransferMoneyCommand;
import com.br.ledger.domain.exception.DomainException;
import com.br.ledger.domain.model.LedgerTransaction;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferMoneyServiceTest {
    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private LedgerRepositoryPort ledgerRepositoryPort;

    @InjectMocks
    private TransferMoneyService service;

    @Test
    void shouldTransferMoneySuccessfully() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        TransferMoneyCommand command =
                new TransferMoneyCommand(
                        from,
                        to,
                        BigDecimal.valueOf(100),
                        "Transferência"
                );

        when(accountRepositoryPort.existsById(from))
                .thenReturn(true);

        when(accountRepositoryPort.existsById(to))
                .thenReturn(true);

        when(ledgerRepositoryPort.getBalance(from))
                .thenReturn(BigDecimal.valueOf(500));

        when(ledgerRepositoryPort.save(any(LedgerTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LedgerTransaction result = service.execute(command);

        assertNotNull(result);
        assertEquals(2, result.getEntries().size());

        verify(ledgerRepositoryPort)
                .save(any(LedgerTransaction.class));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        TransferMoneyCommand command =
                new TransferMoneyCommand(
                        from,
                        to,
                        BigDecimal.valueOf(100),
                        "Transferência"
                );

        when(accountRepositoryPort.existsById(from))
                .thenReturn(true);

        when(accountRepositoryPort.existsById(to))
                .thenReturn(true);

        when(ledgerRepositoryPort.getBalance(from))
                .thenReturn(BigDecimal.valueOf(50));

        assertThrows(DomainException.class, () ->
                service.execute(command)
        );

        verify(ledgerRepositoryPort, never())
                .save(any());
    }
}