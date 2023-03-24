CREATE TABLE user_role (
    user_id INT         NOT NULL,
    role    VARCHAR(15) NOT NULL,
    CONSTRAINT PRIMARY KEY (user_id, role),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id)
);