create table accounts(
    id serial primary key,
    username varchar(100) not null unique,
    password varchar(255) not null
);
