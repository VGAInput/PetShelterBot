-- liquibase formatted sql
-- changeset set:2

CREATE TABLE cat_shelter
(
    id           SERIAL primary key,
    name         VARCHAR(50) NOT NULL,
    age          INTEGER     NOT NULL,
    breed        VARCHAR(50) NOT NULL,
    sex          VARCHAR(1)  NOT NULL,
    is_sterilized BOOLEAN     NOT NULL,
    owner_id     BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owners (id)
);
