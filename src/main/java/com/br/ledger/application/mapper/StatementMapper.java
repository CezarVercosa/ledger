package com.br.ledger.application.mapper;

import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.domain.model.LedgerEntry;
import com.br.ledger.domain.model.LedgerTransaction;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StatementMapper {
    default List<StatementItem> toStatementItems(
            List<LedgerTransaction> transactions,
            UUID accountId
    ) {
        return transactions.stream()
                .flatMap(transaction ->
                        transaction.getEntries().stream()
                                .filter(entry -> entry.getAccountId().equals(accountId))
                                .map(entry -> toStatementItem(transaction, entry))
                )
                .toList();
    }

    default StatementItem toStatementItem(
            LedgerTransaction transaction,
            LedgerEntry entry
    ) {
        return new StatementItem(
                transaction.getId(),
                transaction.getDescription(),
                entry.getAmount(),
                transaction.getCreatedAt()
        );
    }
}