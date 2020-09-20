create table users(
    id serial primary key,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    is_active boolean not null,
    role varchar(255) not null
);

create table clients(
    id serial primary key,
    chat_id bigint not null unique,
    name varchar(255),
    phone_number varchar(255),
    city varchar(255),
    address varchar(255),
    is_active boolean not null
);

create table categories(
    id serial primary key,
    name varchar(255) not null
);

create table products(
    id serial primary key,
    category_id integer references categories(id) on delete set null,
    photo_url varchar(255) not null,
    name varchar(255) not null,
    description varchar(2550) not null,
    price real not null
);

create table orders(
    id serial primary key,
    client_id integer references clients(id) not null,
    created_date timestamp without time zone not null,
    status varchar(255) not null,
    amount real not null
);

create table order_items(
    id serial primary key,
    order_id integer references orders(id) on delete cascade not null,
    product_id integer references products(id) on delete set null,
    quantity integer not null,
    product_name varchar(255) not null,
    product_price real not null
);
