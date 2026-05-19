package com.br.ledger.adapter.in.web.mapper;

import com.br.ledger.adapter.in.web.request.TransferMoneyRequest;
import com.br.ledger.adapter.in.web.response.StatementResponse;
import com.br.ledger.application.dto.StatementItem;
import com.br.ledger.application.dto.TransferMoneyCommand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LedgerWebMapper {
    TransferMoneyCommand toCommand(TransferMoneyRequest request);
    StatementResponse toResponse(StatementItem item);
    List<StatementResponse> toResponseList(List<StatementItem> items);
}