CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    phone_number VARCHAR(20)  NOT NULL,
    password     VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE accounts
(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER REFERENCES users (id),
    account_name VARCHAR(50) NOT NULL,
    balance      INTEGER     NOT NULL
);

CREATE TABLE transactions
(
    id              SERIAL PRIMARY KEY,
    from_account_id INTEGER REFERENCES accounts (id),
    to_account_id   INTEGER REFERENCES accounts (id),
    amount_paid     INTEGER   NOT NULL,
    created_date    TIMESTAMP NOT NULL

);

CREATE TABLE categories
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    user_id INTEGER REFERENCES users (id)
);

CREATE TABLE transactions_categories
(
    transaction_id INTEGER REFERENCES transactions (id),
    category_id    INTEGER REFERENCES categories (id),
    PRIMARY KEY (transaction_id, category_id)
);
