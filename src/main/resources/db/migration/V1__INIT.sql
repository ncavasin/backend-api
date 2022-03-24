-- create TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE', 'DELETED', 'OVERDUE');

create table if not exists user_data
(
    id                 varchar(255)                    not null
        constraint PK_user_data primary key,
    dni                numeric                         not null unique,
    password           varchar(255)                    not null,
    email              varchar(255)                    not null unique,
    phone              numeric                         not null,
    first_name         varchar(255),
    last_name          varchar(255),
    age                numeric,
    zip_code           varchar(255),
    status             varchar(255) default 'INACTIVE' not null,
    creation_timestamp timestamp    default now()      not null,
    update_timestamp   timestamp    default now()      not null
);
