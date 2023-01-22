create table products (
    id          bigserial primary key,
    title       varchar(255),
    price       decimal,
    manufacturer    varchar(255)
);

insert into products (title, price, manufacturer)
values ('Sleek Iron Bottle', 10424,' Iron INC'),
       ('Fantastic Granite Car', 20563,'Granite LLC'),
       ('Rustic Cotton Bench', 305353,'Iron INC'),
       ('Fantastic Steel Computer', 70240,'Iron INC'),
       ('Small Marble Bench', 80632,'Granite LLC'),
       ('Rustic Wooden Computer', 110900,'Iron INC');

create table users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$12$QGBgaJTay7oKTPQMx941ju9en7YbGSqyv51DLcgnDqUM3CftOrVnC', 'bob_johnson@gmail.com'),
       ('admin', '$2a$12$K20jFLEnn0Owx4T/3yxQ.uLjOMYGMF4JqOSDPEfbRgoU9UnNNW.wu', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (2, 1);

create table orders (
                        id              bigserial primary key,
                        user_id         bigint not null references users (id),
                        total_price     decimal not null,
                        address         varchar(255),
                        phone           varchar(255)
);

create table order_items (
                             id                      bigserial primary key,
                             product_id              bigint not null references products (id),
                             user_id                 bigint not null references users (id),
                             order_id                bigint not null references orders (id),
                             quantity                int not null,
                             price_per_product       decimal not null,
                             price                   decimal not null
);