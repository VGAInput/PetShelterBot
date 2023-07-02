-- liquibase formatted sql
-- changeset set:1

CREATE TABLE owners
(
    id       SERIAL primary key,
    tg_user_id INTEGER     NOT NULL,
    name     VARCHAR(15) NOT NULL,
    telephone_number VARCHAR,
    car_number VARCHAR
);