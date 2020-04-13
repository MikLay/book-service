create table if not exists book
(
    id     INT auto_increment
        primary key,
    title  VARCHAR(50) not null,
    isbn   VARCHAR(50) not null,
    author VARCHAR(50) not null,
    page   VARCHAR(50) not null
);