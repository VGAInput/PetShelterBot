-- liquibase formatted sql

CREATE TABLE dogShelter
(
    id           SERIAL primary key,
    name         VARCHAR(50) NOT NULL,
    age          INTEGER     NOT NULL,
    breed        VARCHAR(50) NOT NULL,
    sex          VARCHAR(1)  NOT NULL,
    isSterilized BOOLEAN     NOT NULL
);