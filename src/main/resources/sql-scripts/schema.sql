CREATE TABLE stalker
(
    id          SERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL
);

CREATE TABLE monitoring
(
    id         SERIAL PRIMARY KEY,
    stalker_id INT  NOT NULL REFERENCES stalker (id),
    network    TEXT NOT NULL,
    target     INT  NOT NULL
);

CREATE TABLE relation_update
(
    id         SERIAL PRIMARY KEY,
    stalker_id INT       NOT NULL REFERENCES stalker (id),
    network    TEXT      NOT NULL,
    target     TEXT      NOT NULL,
    suspected  TEXT      NOT NULL,
    was        TEXT,
    now        TEXT,
    time       TIMESTAMP NOT NULL
);
