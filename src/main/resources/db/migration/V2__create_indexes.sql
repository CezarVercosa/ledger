CREATE INDEX idx_ledger_entries_account
    ON ledger_entries(account_id);

CREATE INDEX idx_ledger_entries_transaction
    ON ledger_entries(transaction_id);