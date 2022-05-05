CREATE TABLE notification
(
    ID        serial primary key,
    UserId    integer not null,
    SessionId integer not null,
    Seen      boolean not null default false
);