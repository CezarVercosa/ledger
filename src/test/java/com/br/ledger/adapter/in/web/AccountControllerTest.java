package com.br.ledger.adapter.in.web;

import com.br.ledger.adapter.in.web.mapper.AccountWebMapper;
import com.br.ledger.adapter.in.web.request.CreateAccountRequest;
import com.br.ledger.adapter.in.web.response.AccountResponse;
import com.br.ledger.application.dto.CreateAccountCommand;
import com.br.ledger.application.port.in.CreateAccountUseCase;
import com.br.ledger.application.port.in.GetAccountsUseCase;
import com.br.ledger.domain.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateAccountUseCase createAccountUseCase;

    @MockitoBean
    private GetAccountsUseCase getAccountsUseCase;

    @MockitoBean
    private AccountWebMapper mapper;

    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest("Conta Teste");
        CreateAccountCommand command = new CreateAccountCommand("Conta Teste");

        Account account = Account.restore(
                UUID.randomUUID(),
                "Conta Teste",
                Instant.now()
        );

        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCreatedAt()
        );

        when(mapper.toCommand(request)).thenReturn(command);
        when(createAccountUseCase.execute(command)).thenReturn(account);
        when(mapper.toResponse(account)).thenReturn(response);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Conta Teste"));
    }

    @Test
    void shouldReturnAllAccountsSuccessfully() throws Exception {
        Account account = Account.restore(
                UUID.randomUUID(),
                "Conta Teste",
                Instant.now()
        );

        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCreatedAt()
        );

        when(getAccountsUseCase.execute()).thenReturn(List.of(account));
        when(mapper.toResponseList(List.of(account))).thenReturn(List.of(response));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Conta Teste"));
    }
}