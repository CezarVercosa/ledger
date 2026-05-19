package com.br.ledger.application.port.in;

import com.br.ledger.application.dto.CreateAccountCommand;
import com.br.ledger.domain.model.Account;

public interface CreateAccountUseCase {
    Account execute(CreateAccountCommand command);
}