create table client(
    id bigint not null auto_increment,
    address_fk bigint not null,
    name varchar(80) not null,
    email varchar(60) not null unique,

    primary key (id)
);