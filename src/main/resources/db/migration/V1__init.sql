create table if not exists book
(
    id     INT auto_increment
        primary key,
    title  VARCHAR(50) not null,
    isbn   VARCHAR(50) not null,
    author VARCHAR(50) not null,
    page   VARCHAR(50) not null
);

create table if not exists users
(
    id       int primary key auto_increment,
    login    varchar(30) not null,
    password varchar(40) not null,
    custom_auth_field varchar(40) not null,
    unique uniq_login (login)
);

create table if not exists permissions
(
    id         int primary key auto_increment,
    permission varchar(30) not null,
    unique uniq_permission (permission)
);

create table if not exists user_to_permissions
(
    user_id       int not null,
    permission_id int not null,
    constraint fk_user_to_permission_user foreign key (user_id) references users (id),
    constraint fk_user_to_permission_permission foreign key (permission_id) references permissions (id)
);

create table user_to_liked_book
(
    book_id int not null,
    user_id int not null,
    constraint fk_user_to_liked_book_user foreign key (user_id) references users (id),
    constraint fk_user_to_liked_book_book foreign key (book_id) references book (id)
);

insert into book (TITLE, ISBN, AUTHOR, PAGE)
VALUES ('Дюна', 'ISBN-10 0-496-51068-9', 'Франк Герберт', '300'),
       ('Order by Fire', '0 512 52068 9', 'Sarah Hawkswood', '250'),
       ('White Fang', 'ISBN-10 0-596-52068-9', 'Jack London', '310'),
       ('Війна та Мир', 'ISBN 978-0-136-54068-7', 'Лев Толстой', '1345');

insert into users (login, password, custom_auth_field) values
('admin', 'password', 'admin custom auth field'),
('catalog', 'password', 'some other custom auth field for catalog'),
('user', 'password', 'custom user field');

insert into permissions (permission)
values ('VIEW_ADMIN'),
       ('VIEW_CATALOG'),
       ('LIKED_BOOK');

insert into user_to_permissions (user_id, permission_id)
values ((select id from users where login = 'user'), (select id from permissions where permission = 'VIEW_CATALOG')),
       ((select id from users where login = 'admin'), (select id from permissions where permission = 'VIEW_ADMIN')),
       ((select id from users where login = 'user'), (select id from permissions where permission = 'LIKED_BOOK'));

insert into user_to_liked_book (user_id, book_id)
values ((select id from users where login = 'user'), (select id from book where title = 'Дюна'));
