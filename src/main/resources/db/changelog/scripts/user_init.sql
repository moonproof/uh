create table T_USER
(
    id         serial PRIMARY KEY,
    name     VARCHAR(50) not null,
    email      VARCHAR(50) not null,
    urlPicture VARCHAR(50) not null
);