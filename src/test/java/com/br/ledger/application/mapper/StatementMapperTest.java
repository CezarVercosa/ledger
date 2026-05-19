package com.br.ledger.application.mapper;

import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.domain.model.LedgerTransaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StatementMapperTest {
    private final StatementMapper mapper = new StatementMapperImpl();

    @Test
    void shouldMapStatementItemsSuccessfully() {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        LedgerTransaction transaction = LedgerTransaction.transfer(
                from,
                to,
                BigDecimal.valueOf(100),
                "Transferência"
        );

        List<StatementItem> result = mapper.toStatementItems(
                List.of(transaction),
                from
        );

        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.getFirst().transactionId());
        assertEquals(BigDecimal.valueOf(-100), result.getFirst().amount());
    }
}