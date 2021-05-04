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

DROP TABLE IF EXISTS orders;

CREATE TYPE order_status AS ENUM (
    'PENDING',
    'APPROVED',
    'REJECTED'
)

CREATE TABLE orders
(
    id                  bigserial      NOT NULL PRIMARY KEY,
    total_price         double precision NOT NULL,
    create_date         timestamptz           NOT NULL,
    is_deleted          boolean DEFAULT false,
    status              order_status DEFAULT 'PENDING',
	user_id              BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS gift_certificates_orders;

CREATE TABLE gift_certificates_orders
(
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id) ON DELETE CASCADE ,
    order_id              BIGINT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    PRIMARY KEY (gift_certificate_id, order_id)
);

CREATE TYPE user_role AS ENUM (
    'GUEST',
    'USER',
    'ADMIN'
)

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               bigserial      NOT NULL PRIMARY KEY,
    username         VARCHAR(20)  NOT NULL,
    password         VARCHAR(255) NOT NULL,
    is_deleted       boolean DEFAULT false,
    role             user_role DEFAULT 'GUEST'
);
