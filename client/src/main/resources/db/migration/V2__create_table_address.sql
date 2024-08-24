create table address(
    id bigint not null auto_increment,
    street varchar(80) not null,
    city varchar(80) not null,
    state varchar(80) not null,
    zipcode varchar(80) not null,
    country varchar(80) not null,

    primary key (id)
);