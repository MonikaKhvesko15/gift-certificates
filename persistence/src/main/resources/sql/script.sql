DROP TABLE gift_certificates CASCADE;

CREATE TABLE gift_certificates
(
    id               bigserial      NOT NULL PRIMARY KEY,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision,
    duration         int,
    create_date      timestamptz           NOT NULL,
    last_update_date timestamptz          NOT NULL
);

DROP TABLE tags CASCADE;

CREATE TABLE tags
(
    id   bigserial      NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    UNIQUE (name)
);

DROP TABLE gift_certificates_tags;

create table gift_certificates_tags
(
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    tag_id              BIGINT NOT NULL REFERENCES tags (id),
    UNIQUE (gift_certificate_id, tag_id)
);

