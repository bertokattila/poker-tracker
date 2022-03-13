CREATE table session
(
    ID         serial primary key,
    UserId     integer    not null,
    Type       varchar    not null,
    Currency   varchar(3) not null,
    BuyIn      real,
    CashOut    real,
    Date       date not null default current_date,
    Comment    text,
    LocationID integer
);