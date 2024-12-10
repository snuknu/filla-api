create table account(
    id serial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table employee(
    id serial primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    phone varchar(20) not null,
	enrollment varchar(6) not null,
    service varchar(255) not null,
    street varchar(100) not null,
    district varchar(100) not null,
    zipcode varchar(9) not null,
    complement varchar(100),
    number varchar(20),
    state char(2) not null,
    city varchar(100) not null,
    active boolean not null
);

create table customer(
    id serial primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    phone varchar(20) not null,
    street varchar(100) not null,
    district varchar(100) not null,
    zipcode varchar(9) not null,
    complement varchar(100),
    number varchar(20),
    state char(2) not null,
    city varchar(100) not null,
    active boolean not null
);

create table appointment(
	id serial primary key,
  	customer_id bigint not null,
	employee_id bigint not null,
	appointment_date timestamp not null,
	reason_cancellation varchar(100) not null,
	constraint fk_appointment_customer_id foreign key (customer_id) references customer(id),
	constraint fk_appointment_employee_id foreign key (employee_id) references employee(id)
);
