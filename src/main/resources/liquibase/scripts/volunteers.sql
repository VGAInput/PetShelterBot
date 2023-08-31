-- liquibase formatted sql
-- changeset set:4

CREATE TABLE volunteers
(
    id                 SERIAL primary key,
    name               VARCHAR(15) NOT NULL,
    tg_user_id         INTEGER     NOT NULL,
    shelter_table_name VARCHAR(15) NOT NULL,
    is_ready           SMALLINT
);