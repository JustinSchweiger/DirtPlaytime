CREATE TABLE IF NOT EXISTS players (
    uuid VARCHAR(36) NOT NULL,
    username VARCHAR(16) NOT NULL,
    current_path VARCHAR(30) NOT NULL,
    time_played BIGINT DEFAULT 0,
    times_joined INT DEFAULT 1,
    first_joined VARCHAR(50) NOT NULL,
    PRIMARY KEY (uuid)
);