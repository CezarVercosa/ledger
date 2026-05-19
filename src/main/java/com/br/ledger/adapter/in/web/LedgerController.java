package com.br.ledger.adapter.in.web;

import com.br.ledger.adapter.in.web.mapper.LedgerWebMapper;
import com.br.ledger.adapter.in.web.request.TransferMoneyRequest;
import com.br.ledger.adapter.in.web.response.BalanceResponse;
import com.br.ledger.adapter.in.web.response.StatementResponse;
import com.br.ledger.application.port.in.GetBalanceUseCase;
import com.br.ledger.application.port.in.GetStatementUseCase;
import com.br.ledger.application.port.in.TransferMoneyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ledger")
@RequiredArgsConstructor
@Tag(name = "Endpoints de movimentação bancária")
public class LedgerController {
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetBalanceUseCase getBalanceUseCase;
    private final GetStatementUseCase getStatementUseCase;
    private final LedgerWebMapper mapper;

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Transfere valores entre contas"
    )
    public void transfer(
            @Valid @RequestBody TransferMoneyRequest request
    ) {
        transferMoneyUseCase.execute(
                mapper.toCommand(request)
        );
    }

    @GetMapping("/balance/{accountId}")
    @Operation(
            summary = "Resgata o saldo da conta"
    )
    public BalanceResponse getBalance(
            @PathVariable UUID accountId
    ) {
        return new BalanceResponse(
                accountId,
                getBalanceUseCase.execute(accountId)
        );
    }

    @GetMapping("/statement/{accountId}")
    @Operation(
            summary = "Todas as movimentações relacionadas àquela conta"
    )
    public List<StatementResponse> getStatement(
            @PathVariable UUID accountId
    ) {
        return mapper.toResponseList(
                getStatementUseCase.execute(accountId)
        );
    }
}