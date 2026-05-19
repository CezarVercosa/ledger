package com.br.ledger.application.service;

import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.application.mapper.StatementMapper;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStatementServiceTest {
    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private LedgerRepositoryPort ledgerRepositoryPort;

    @Mock
    private StatementMapper statementMapper;

    @InjectMocks
    private GetStatementService service;

    @Test
    void shouldReturnStatementSuccessfully() {
        UUID accountId = UUID.randomUUID();

        List<LedgerTransaction> transactions = List.of();

        List<StatementItem> items = List.of(
                new StatementItem(
                        UUID.randomUUID(),
                        "Teste",
                        BigDecimal.valueOf(100),
                        java.time.Instant.now()
                )
        );

        when(accountRepositoryPort.existsById(accountId))
                .thenReturn(true);

        when(ledgerRepositoryPort.findStatementByAccountId(accountId))
                .thenReturn(transactions);

        when(statementMapper.toStatementItems(transactions, accountId))
                .thenReturn(items);

        List<StatementItem> result = service.execute(accountId);

        assertEquals(1, result.size());
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