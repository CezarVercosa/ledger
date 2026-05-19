CREATE TABLE accounts (
                          id UUID PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          created_at TIMESTAMP NOT NULL
);

CREATE TABLE ledger_transactions (
                                     id UUID PRIMARY KEY,
                                     description VARCHAR(255),
                                     created_at TIMESTAMP NOT NULL
);

CREATE TABLE ledger_entries (
                                id UUID PRIMARY KEY,
                                transaction_id UUID NOT NULL,
                                account_id UUID NOT NULL,
                                amount NUMERIC(19,4) NOT NULL,
                                CONSTRAINT fk_ledger_transaction
                                    FOREIGN KEY (transaction_id)
                                        REFERENCES ledger_transactions(id),
                                CONSTRAINT fk_ledger_account
                                    FOREIGN KEY (account_id)
                                        REFERENCES accounts(id)
);