CREATE TABLE IF NOT EXISTS players (
    uuid VARCHAR(36) NOT NULL,
    time_played BIGINT DEFAULT 0,
    times_joined INT DEFAULT 1,
    first_joined VARCHAR(50) NOT NULL,
    PRIMARY KEY (uuid)
);