WITH test_account AS (
INSERT INTO ledger.accounts (
    id,
    name,
    created_at
)
VALUES (
    gen_random_uuid(),
    'Conta Teste',
    NOW()
    )
    RETURNING id
    ),
    system_account AS (
INSERT INTO ledger.accounts (
    id,
    name,
    created_at
)
VALUES (
    gen_random_uuid(),
    'Conta Sistema',
    NOW()
    )
    RETURNING id
    ),
    initial_transaction AS (
INSERT INTO ledger.ledger_transactions (
    id,
    description,
    created_at
)
VALUES (
    gen_random_uuid(),
    'Saldo inicial',
    NOW()
    )
    RETURNING id
    )

INSERT INTO ledger.ledger_entries (
    id,
    transaction_id,
    account_id,
    amount
)
VALUES
    (
    gen_random_uuid(),
    (SELECT id FROM initial_transaction),
    (SELECT id FROM system_account),
    -1000
    ),
    (
    gen_random_uuid(),
    (SELECT id FROM initial_transaction),
    (SELECT id FROM test_account),
    1000
    );