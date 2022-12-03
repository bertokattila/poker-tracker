CREATE TABLE statistics_history
(
    ID         serial primary key,
    UserId     integer unique not null,
    SessionId  integer        not null,
    StartDate  timestamp not null default current_timestamp           not null,
    EndDate    timestamp not null default current_timestamp           not null,
    Result     real           not null,
    PlayedTime integer        not null,
    Type       varchar        not null
);
