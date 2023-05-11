DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(1024),
    created_on TIMESTAMP,
    updated_on TIMESTAMP
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);
