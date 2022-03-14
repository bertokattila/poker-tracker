CREATE TABLE users
(
    ID              serial primary key,
    Name            varchar        not null,
    Email           varchar unique not null,
    Password        varchar        not null,
    DefaultCurrency varchar(3)     not null
);