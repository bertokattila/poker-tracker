CREATE TABLE social
(
    ID           serial primary key,
    MasterUserId integer        not null,
    SlaveUserId  integer        not null,
    Active       boolean        not null default false
);