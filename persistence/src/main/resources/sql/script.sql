DROP TABLE IF EXISTS gift_certificates CASCADE;

CREATE TABLE gift_certificates
(
    id               bigserial      NOT NULL PRIMARY KEY,
    name             varchar(50) NOT NULL,
    description      varchar(255),
    price            double precision NOT NULL,
    duration         int NOT NULL,
    create_date      timestamptz           NOT NULL,
    last_update_date timestamptz          NOT NULL,
    is_deleted       boolean DEFAULT false
);

DROP TABLE IF EXISTS tags CASCADE;

CREATE TABLE tags
(
    id      bigserial      NOT NULL PRIMARY KEY,
    name    varchar(150) NOT NULL,
    UNIQUE (name)
);

DROP TABLE IF EXISTS gift_certificates_tags;

CREATE TABLE gift_certificates_tags
(
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id) ON DELETE CASCADE ,
    tag_id              BIGINT NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    PRIMARY KEY (tag_id,gift_certificate_id )
);

DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE orders
(
    id                  bigserial      NOT NULL PRIMARY KEY,
    total_price         double precision NOT NULL,
    create_date         timestamptz           NOT NULL,
    is_deleted          boolean DEFAULT false,
    status              VARCHAR (50) DEFAULT 'PENDING',
    user_id              BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS gift_certificates_orders;

CREATE TABLE gift_certificates_orders
(
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id) ON DELETE CASCADE ,
    order_id              BIGINT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    PRIMARY KEY (gift_certificate_id, order_id)
);


DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               bigserial      NOT NULL PRIMARY KEY,
    email            VARCHAR(50)  NOT NULL,
    password         VARCHAR(60) NOT NULL,
    first_name       VARCHAR (50) NOT NULL ,
    last_name        VARCHAR (50) NOT NULL,
    is_deleted       boolean DEFAULT false,
    is_blocked       boolean DEFAULT false
);

DROP TABLE IF EXISTS roles;

CREATE TABLE roles(
                      id               bigserial      NOT NULL PRIMARY KEY,
                      name             VARCHAR(50)  NOT NULL
);

insert into roles(name) values('USER');
insert into roles(name) values('ADMIN');


DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles
(
    user_id              BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE ,
    role_id              BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


DROP TABLE IF EXISTS events;

create TABLE events
(
    id           bigserial NOT NULL PRIMARY KEY,
    content_name VARCHAR(255),
    content_id   BIGINT NOT NULL,
    operation    VARCHAR(255),
    date_time    timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);
