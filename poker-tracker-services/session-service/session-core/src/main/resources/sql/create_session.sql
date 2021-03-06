CREATE table session
(
    ID         serial primary key,
    UserId     integer    not null,
    Type       varchar    not null,
    Currency   varchar(3) not null,
    BuyIn      real,
    CashOut    real,
    StartDate  timestamp not null default current_timestamp,
    EndDate    timestamp not null default current_timestamp,
    Comment    text,
    LocationID bigint,
    Access    varchar(10) not null default 'public'
);