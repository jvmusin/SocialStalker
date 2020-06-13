CREATE TABLE seeker
(
    id       SERIAL NOT NULL PRIMARY KEY,
    resource TEXT   NOT NULL,
    owner    TEXT   NOT NULL
);

CREATE TABLE relation_update
(
    id       SERIAL    NOT NULL PRIMARY KEY,
    resource TEXT      NOT NULL,
    owner    TEXT      NOT NULL,
    target   TEXT      NOT NULL,
    was      TEXT      NULL,
    now      TEXT      NULL,
    time     TIMESTAMP NOT NULL,
    hidden   BOOLEAN   NOT NULL
);