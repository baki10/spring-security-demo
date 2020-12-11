CREATE TABLE IF NOT EXISTS workout (
    id SERIAL NOT NULL,
    username VARCHAR(45) NULL,
    start_date DATE NULL,
    end_date DATE NULL,
    difficulty INT NULL,
    PRIMARY KEY (id)
    );
