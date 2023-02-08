create table products (
                          id          bigserial primary key,
                          title       varchar(255),
                          price       decimal,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Sleek Iron Bottle', 10424),
       ('Fantastic Granite Car', 20563),
       ('Rustic Cotton Bench', 305353),
       ('Fantastic Steel Computer', 70240),
       ('Small Marble Bench', 80632),
       ('Big Glass Stick', 20032),
       ('Fantastic Wooden Mobile', 180632),
       ('Little Cotton Book', 10632),
       ('Rustic Wooden Computer', 110900);

create table orders (
                        id              bigserial primary key,
                        username        varchar(255) not null,
                        total_price     decimal not null,
                        address         varchar(255),
                        phone           varchar(255),
                        created_at  timestamp default current_timestamp,
                        updated_at  timestamp default current_timestamp
);

create table order_items (
                             id                      bigserial primary key,
                             product_id              bigint not null references products (id),
                             order_id                bigint not null references orders (id),
                             quantity                int not null,
                             price_per_product       decimal not null,
                             price                   decimal not null,
                             created_at              timestamp default current_timestamp,
                             updated_at              timestamp default current_timestamp
);








