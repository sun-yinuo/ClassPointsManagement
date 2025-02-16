create table event
(
    id            int auto_increment
        primary key,
    eventType int not null ,
    time text not null,
    operator text not null ,
    target text not null ,
    event text not null,
    reason text not null
)
    collate = utf8mb3_bin;