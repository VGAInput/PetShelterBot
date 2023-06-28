-- liquibase formatted sql

CREATE TABLE owners
(
    id       SERIAL primary key,
    tgUserId INTEGER     NOT NULL,
    name     VARCHAR(15) NOT NULL,
    telephoneNumber varchar(25),
    carNumber varchar(15);
);