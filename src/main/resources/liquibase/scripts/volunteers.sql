-- liquibase formatted sql

CREATE TABLE volunteers
(
    id               SERIAL primary key,
    name             VARCHAR(15) NOT NULL,
    tgUserId             INTEGER NOT NULL,
    shelterTableName VARCHAR(15) NOT NULL
);