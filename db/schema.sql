CREATE DATABASE item;

create table users
(
    id       serial primary key,
    email    varchar(255) not null unique,
    name     varchar(255) not null,
    password varchar(255) not null
);

create table items
(
    id          serial primary key,
    created     timestamp    not null,
    description varchar(255) not null,
    done        boolean      not null,
    user_id     int          not null references users (id)
);

