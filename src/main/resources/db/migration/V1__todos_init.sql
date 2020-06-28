create table todos
(
    id                 bigint auto_increment primary key,
    title              varchar(256)                        not null,
    details            varchar(4000)                       null,
    created_date       timestamp default CURRENT_TIMESTAMP not null,
    last_modified_date timestamp default CURRENT_TIMESTAMP not null
);
