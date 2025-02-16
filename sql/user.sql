create table user
(
    id            int auto_increment
        primary key,
    studentNumber int not null ,
    userName      text collate utf8mb4_general_ci  not null,
    userPassword  text collate utf8mb4_general_ci  not null,
    administrator int                              not null,
    points int not null,
    salt int not null
)
    collate = utf8mb3_bin;