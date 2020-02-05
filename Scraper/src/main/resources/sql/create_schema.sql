CREATE TABLE comment
(
    id VARCHAR(128)             NOT NULL,
    comment_text VARCHAR(16384)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE headline
(
    id VARCHAR(128)             NOT NULL,
    headline_text VARCHAR(16384)  NOT NULL,
    PRIMARY KEY (id)
);

