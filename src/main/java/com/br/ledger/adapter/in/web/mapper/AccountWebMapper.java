package com.br.ledger.adapter.in.web.mapper;

import com.br.ledger.adapter.in.web.request.CreateAccountRequest;
import com.br.ledger.adapter.in.web.response.AccountResponse;
import com.br.ledger.application.dto.CreateAccountCommand;
import com.br.ledger.domain.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountWebMapper {
    CreateAccountCommand toCommand(CreateAccountRequest request);
    AccountResponse toResponse(Account account);
    List<AccountResponse> toResponseList(List<Account> accounts);
}