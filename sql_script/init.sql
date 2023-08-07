create table account
(
    id      bigserial primary key,
    balance float default 0,
    name    varchar(255)
);

create table transaction
(
    id         bigserial primary key,
    balance    float,
    account_id bigint references account (id)
);

create table category
(
    id   bigserial primary key,
    name varchar(255)
);

create table transaction_category
(
    transaction_id bigint references transaction (id),
    category_id    bigint references category (id)
);
