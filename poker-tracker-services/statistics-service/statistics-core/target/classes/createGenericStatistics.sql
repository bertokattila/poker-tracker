CREATE TABLE generic_statistics
(
    ID                  serial primary key,
    UserId              integer unique not null,
    LastMonthResult     real    not null,
    LastYearResult      real    not null,
    AllTimeResult       bigint    not null,
    LastMonthPlayedTime integer not null,
    LastYearPlayedTime  integer not null,
    AllTimePlayedTime   bigint not null,
    NumberOfCashGames   integer not null,
    NumberOfTournaments integer not null,
    NumberOfTableSize2  integer not null default 0,
    NumberOfTableSize3 integer not null default 0,
    NumberOfTableSize4  integer not null default 0,
    NumberOfTableSize5  integer not null default 0,
    NumberOfTableSize6  integer not null default 0,
    NumberOfTableSize7  integer not null default 0,
    NumberOfTableSize8  integer not null default 0,
    NumberOfTableSize9  integer not null default 0,
    NumberOfTableSize10  integer not null default 0
);
