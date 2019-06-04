CREATE TABLE seeker
(
    id    SERIAL NOT NULL PRIMARY KEY,
    owner INT    NOT NULL
);

CREATE TABLE relation_change
(
    id       SERIAL    NOT NULL PRIMARY KEY,
    owner    INT       NOT NULL,
    target   INT       NOT NULL,
    prev_type TEXT      NOT NULL,
    cur_type  TEXT      NOT NULL,
    time     TIMESTAMP NOT NULL,
    hidden   BOOLEAN   NOT NULL
);