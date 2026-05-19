package com.br.ledger.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ledger_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerTransactionEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<LedgerEntryEntity> entries = new ArrayList<>();

    public void addEntry(LedgerEntryEntity entry) {
        entries.add(entry);
        entry.setTransaction(this);
    }
}