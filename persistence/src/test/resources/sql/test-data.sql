INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES
('The first', 'The first certificate', 55.0, 14, '2020-10-23T00:21:13Z', '2021-01-22T08:36:22Z');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES
('The second', 'The second certificate', 35.0, 18, '2020-06-21T20:36:18Z', '2021-01-25T00:23:21Z');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES
('The third', 'The third certificate', 50.0, 11, '2020-03-28T18:09:03Z', '2021-01-08T14:46:33Z');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES
('The fourth', 'The fourth certificate', 14.0, 12, '2020-06-18T09:25:19Z', '2021-03-10T04:51:49Z');

INSERT INTO tags(name) VALUES ('tag1');
INSERT INTO tags(name) VALUES ('tag2');
INSERT INTO tags(name) VALUES ('tag3');
INSERT INTO tags(name) VALUES ('tag4');

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (2, 4);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (3, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (3, 4);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 2);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 4);