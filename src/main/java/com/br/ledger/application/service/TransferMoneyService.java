package com.br.ledger.application.service;

import com.br.ledger.application.dto.TransferMoneyCommand;
import com.br.ledger.application.port.in.TransferMoneyUseCase;
import com.br.ledger.domain.exception.DomainException;
import com.br.ledger.domain.model.LedgerTransaction;
import com.br.ledger.domain.port.out.AccountRepositoryPort;
import com.br.ledger.domain.port.out.LedgerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferMoneyService implements TransferMoneyUseCase {
    private final AccountRepositoryPort accountRepositoryPort;
    private final LedgerRepositoryPort ledgerRepositoryPort;

    @Override
    @Transactional
    public LedgerTransaction execute(TransferMoneyCommand command) {
        validateAccounts(command);
        validateAvailableBalance(command);

        LedgerTransaction transaction = LedgerTransaction.transfer(
                command.fromAccountId(),
                command.toAccountId(),
                command.amount(),
                command.description()
        );

        return ledgerRepositoryPort.save(transaction);
    }

    private void validateAccounts(TransferMoneyCommand command) {
        if (!accountRepositoryPort.existsById(command.fromAccountId())) {
            throw new DomainException("Conta de origem não encontrada.");
        }

        if (!accountRepositoryPort.existsById(command.toAccountId())) {
            throw new DomainException("Conta de destino não encontrada.");
        }

        if (command.fromAccountId().equals(command.toAccountId())) {
            throw new DomainException("A conta de origem e destino não podem ser iguais.");
        }
    }

    private void validateAvailableBalance(TransferMoneyCommand command) {
        BigDecimal currentBalance = ledgerRepositoryPort.getBalance(command.fromAccountId());

        if (currentBalance.compareTo(command.amount()) < 0) {
            throw new DomainException("Saldo insuficiente para realizar a transferência.");
        }
    }
}