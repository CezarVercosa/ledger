package com.br.ledger.adapter.in.web;

import com.br.ledger.adapter.in.web.mapper.LedgerWebMapper;
import com.br.ledger.adapter.in.web.request.TransferMoneyRequest;
import com.br.ledger.adapter.in.web.response.StatementResponse;
import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.application.dto.TransferMoneyCommand;
import com.br.ledger.application.port.in.GetBalanceUseCase;
import com.br.ledger.application.port.in.GetStatementUseCase;
import com.br.ledger.application.port.in.TransferMoneyUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LedgerController.class)
class LedgerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransferMoneyUseCase transferMoneyUseCase;

    @MockitoBean
    private GetBalanceUseCase getBalanceUseCase;

    @MockitoBean
    private GetStatementUseCase getStatementUseCase;

    @MockitoBean
    private LedgerWebMapper mapper;

    @Test
    void shouldTransferMoneySuccessfully() throws Exception {
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        TransferMoneyRequest request = new TransferMoneyRequest(
                from,
                to,
                BigDecimal.valueOf(100),
                "Transferência"
        );

        TransferMoneyCommand command = new TransferMoneyCommand(
                from,
                to,
                BigDecimal.valueOf(100),
                "Transferência"
        );

        when(mapper.toCommand(request)).thenReturn(command);

        mockMvc.perform(post("/ledger/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(transferMoneyUseCase).execute(command);
    }

    @Test
    void shouldReturnBalanceSuccessfully() throws Exception {
        UUID accountId = UUID.randomUUID();

        when(getBalanceUseCase.execute(accountId))
                .thenReturn(BigDecimal.valueOf(500));

        mockMvc.perform(get("/ledger/balance/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    void shouldReturnStatementSuccessfully() throws Exception {
        UUID accountId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Instant createdAt = Instant.now();

        StatementItem item = new StatementItem(
                transactionId,
                "Teste",
                BigDecimal.valueOf(100),
                createdAt
        );

        StatementResponse response = new StatementResponse(
                transactionId,
                "Teste",
                BigDecimal.valueOf(100),
                createdAt
        );

        when(getStatementUseCase.execute(accountId))
                .thenReturn(List.of(item));

        when(mapper.toResponseList(List.of(item)))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/ledger/statement/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Teste"))
                .andExpect(jsonPath("$[0].amount").value(100));
    }
}