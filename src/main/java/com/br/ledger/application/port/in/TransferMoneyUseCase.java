package com.br.ledger.application.port.in;

import com.br.ledger.application.dto.TransferMoneyCommand;
import com.br.ledger.domain.model.LedgerTransaction;

public interface TransferMoneyUseCase {
    LedgerTransaction execute(TransferMoneyCommand command);
}