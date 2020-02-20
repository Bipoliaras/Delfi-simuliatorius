CREATE TABLE IF NOT EXISTS t_comment
(
    id VARCHAR(128) NOT NULL,
    username VARCHAR(16384)  NOT NULL,
    comment_text VARCHAR(16384)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_headline
(
    id VARCHAR(128)  NOT NULL,
    title VARCHAR(16384) NOT NULL,
    date VARCHAR(16384) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_image
(
    id VARCHAR(128)             NOT NULL,
    image_link VARCHAR(16384)  NOT NULL,
    PRIMARY KEY (id)
);



