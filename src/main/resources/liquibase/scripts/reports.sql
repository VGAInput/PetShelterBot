-- liquibase formatted sql
-- changeset set:5

CREATE TABLE reports
(
    id           SERIAL primary key,
    message_id   INTEGER       NOT NULL,
    report_date  DATE,
    owner_id     BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owners (id),
    volunteer_id BIGINT,
    FOREIGN KEY (volunteer_id) REFERENCES volunteers (id),
    report_text  VARCHAR(1000) NOT NULL,
    is_approved  SMALLINT
);
