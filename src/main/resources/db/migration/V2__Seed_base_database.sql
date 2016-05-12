INSERT INTO "user" VALUES (1, 'gdownes', 'gdownes@gmail.com', 'reset_token', 'access_token', 'password', TRUE, now(), now(), 1);

INSERT INTO role VALUES (1, 'admin');
INSERT INTO role VALUES (2, 'verified');
INSERT INTO role VALUES (3, 'registered');

INSERT INTO user_role VALUES (1, 1, 1);
INSERT INTO user_role VALUES (2, 1, 2);
INSERT INTO user_role VALUES (3, 1, 3);

INSERT INTO "check" VALUES (1, 1, NULL, 'Google', 'http://www.google.com/', 'Europe', 'UNKNOWN'::status, 'WAITING'::state, now(), now(), now(), NULL, 1, FALSE, 1);
INSERT INTO "check" VALUES (2, 1, NULL, 'Facebook', 'http://www.facebook.com/', 'Europe', 'UNKNOWN'::status, 'WAITING'::state, now(), now(), now(), NULL, 1, FALSE, 1);

INSERT INTO result VALUES (1, 1, NULL, 'UP'::status, 'Europe', 200, 1000, TRUE, FALSE, now(), now(), 1);
INSERT INTO result VALUES (2, 2, NULL, 'UP'::status, 'Europe', 200, 1000, TRUE, FALSE, now(), now(), 1);