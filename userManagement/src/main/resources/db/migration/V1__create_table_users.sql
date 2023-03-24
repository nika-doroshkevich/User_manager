CREATE TABLE users (
    id                INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(255)  NOT NULL,
    password          VARCHAR(255) NOT NULL,
    email             VARCHAR(255)  NOT NULL,
    registration_date DATE         NOT NULL,
    last_login_date   DATE,
    status            ENUM('ACTIVE', 'BLOCKED', 'DELETED') NOT NULL
);