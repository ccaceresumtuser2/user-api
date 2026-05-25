CREATE DATABASE IF NOT EXISTS demo_db;

USE demo_db;

CREATE TABLE IF NOT EXISTS users (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    nombres  VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email   VARCHAR(150) NOT NULL,
    edad    VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
);
