DROP TABLE IF EXISTS gift_certificates CASCADE;

CREATE TABLE gift_certificates
(
    id               bigint  auto_increment NOT NULL PRIMARY KEY,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision NOT NULL,
    duration         int NOT NULL,
    create_date      timestamp           NOT NULL,
    last_update_date timestamp          NOT NULL,
    isDeleted        int DEFAULT 0,
    UNIQUE (name)
);

DROP TABLE IF EXISTS tags CASCADE;

CREATE TABLE tags
(
    id   bigint  auto_increment  NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    UNIQUE (name)
);

DROP TABLE IF EXISTS gift_certificates_tags;

CREATE TABLE gift_certificates_tags
(
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id) ON DELETE CASCADE ,
    tag_id              BIGINT NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    PRIMARY KEY (tag_id,gift_certificate_id )
);

