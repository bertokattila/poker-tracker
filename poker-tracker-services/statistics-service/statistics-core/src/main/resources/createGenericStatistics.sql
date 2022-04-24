CREATE TABLE generic_statistics
(
    ID                  serial primary key,
    UserId              integer unique not null,
    LastMonthResult     real    not null,
    LastYearResult      real    not null,
    AllTimeResult       real    not null,
    LastMonthPlayedTime integer not null,
    LastYearPlayedTime  integer not null,
    AllTimePlayedTime   integer not null
);