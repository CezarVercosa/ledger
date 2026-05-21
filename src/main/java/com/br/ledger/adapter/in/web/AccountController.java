package com.br.ledger.adapter.in.web;

import com.br.ledger.adapter.in.web.mapper.AccountWebMapper;
import com.br.ledger.adapter.in.web.request.CreateAccountRequest;
import com.br.ledger.adapter.in.web.response.AccountResponse;
import com.br.ledger.application.port.in.CreateAccountUseCase;
import com.br.ledger.application.port.in.GetAccountsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Endpoints de contas")
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountWebMapper mapper;
    private final GetAccountsUseCase getAccountsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cria uma conta."
    )
    public AccountResponse create(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return mapper.toResponse(
                createAccountUseCase.execute(
                        mapper.toCommand(request)
                )
        );
    }

    @GetMapping
    @Operation(
            summary = "Lista todas as contas."
    )
    public List<AccountResponse> findAll() {
        return mapper.toResponseList(
                getAccountsUseCase.execute()
        );
    }
}