-- DROP TABLE persons;

CREATE TABLE IF NOT EXISTS person
(
    idi          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(255)       NOT NULL,
    password      VARCHAR(255)       NOT NULL,
    email         INTEGER            NOT NULL,
    regdate     TIMESTAMP                NOT NULL,
);
