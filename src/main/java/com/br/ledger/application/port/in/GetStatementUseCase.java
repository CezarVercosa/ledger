package com.br.ledger.application.port.in;

import com.br.ledger.application.dto.StatementItem;

import java.util.List;
import java.util.UUID;

public interface GetStatementUseCase {
    List<StatementItem> execute(UUID accountId);
}