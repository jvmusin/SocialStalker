CREATE TABLE stalker
(
    id          SERIAL PRIMARY KEY,
    telegram_id BIGINT NOT NULL
);

CREATE TABLE monitoring
(
    id         SERIAL PRIMARY KEY,
    stalker_id INT     NOT NULL REFERENCES stalker (id),
    network    TEXT    NOT NULL,
    target_id  INT     NOT NULL,
    enabled    BOOLEAN NOT NULL
);

CREATE TABLE relation_update
(
    id           SERIAL PRIMARY KEY,
    stalker_id   INT       NOT NULL REFERENCES stalker (id),
    network      TEXT      NOT NULL,
    target_id    TEXT      NOT NULL,
    suspected_id TEXT      NOT NULL,
    was_type     TEXT,
    now_type     TEXT,
    created_at   TIMESTAMP NOT NULL
);
