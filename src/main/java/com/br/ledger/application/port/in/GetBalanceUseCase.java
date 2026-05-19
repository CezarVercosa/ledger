package com.br.ledger.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface GetBalanceUseCase {
    BigDecimal execute(UUID accountId);
}