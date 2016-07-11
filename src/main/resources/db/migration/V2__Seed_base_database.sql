INSERT INTO "user"
VALUES (DEFAULT, 'gdownes', 'gdownes@gmail.com', 'reset_token', 'access_token', 'password', TRUE, now(), now(), 1);

INSERT INTO role VALUES (DEFAULT, 'registered', 0, 0, '00:00:00');
INSERT INTO role VALUES (DEFAULT, 'verified', 10, 20, '24:00:00');
INSERT INTO role VALUES (DEFAULT, 'admin', 50, 50, '168:00:00');

INSERT INTO user_role VALUES (DEFAULT, 1, 1);
INSERT INTO user_role VALUES (DEFAULT, 1, 2);
INSERT INTO user_role VALUES (DEFAULT, 1, 3);

INSERT INTO "check" VALUES
  (DEFAULT, 1, NULL, 'Google', 'http://www.google.com/', 'Europe', 'UNKNOWN' ::status, 'WAITING' ::state, now(), now(),
            now(), NULL, 1, FALSE, 1);
INSERT INTO "check" VALUES
  (DEFAULT, 1, NULL, 'Facebook', 'http://www.facebook.com/', 'Europe', 'UNKNOWN' ::status, 'WAITING' ::state, now(),
            now(), now(), NULL, 1, FALSE, 1);

INSERT INTO result VALUES (DEFAULT, 1, NULL, 'UP' ::status, 'Europe', 200, 1000, TRUE, FALSE, now(), now(), 1);
INSERT INTO result VALUES (DEFAULT, 2, NULL, 'UP' ::status, 'Europe', 200, 1000, TRUE, FALSE, now(), now(), 1);