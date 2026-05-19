package com.br.ledger.adapter.out.persistence.mapper;

import com.br.ledger.adapter.out.persistence.entity.AccountEntity;
import com.br.ledger.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    default AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .name(account.getName())
                .createdAt(account.getCreatedAt())
                .build();
    }

    default Account toDomain(AccountEntity entity) {
        return Account.restore(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt()
        );
    }
}