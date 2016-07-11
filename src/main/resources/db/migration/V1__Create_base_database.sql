CREATE TYPE status AS ENUM ('UNKNOWN', 'UP', 'DOWN');
CREATE TYPE state AS ENUM ('WAITING', 'ELECTED');

CREATE TABLE "user" (
  id             BIGSERIAL    NOT NULL PRIMARY KEY,
  name           VARCHAR(255) NOT NULL,
  email          VARCHAR(255) NOT NULL UNIQUE,
  reset_token    VARCHAR(255) NOT NULL,
  access_token   VARCHAR(255) NOT NULL,
  password       VARCHAR(255) NOT NULL,
  email_verified BOOLEAN      NOT NULL,
  created        TIMESTAMP    NOT NULL,
  modified       TIMESTAMP    NOT NULL,
  version        BIGINT       NOT NULL
);

CREATE TABLE role (
  id                        BIGSERIAL    NOT NULL PRIMARY KEY,
  name                      VARCHAR(255) NOT NULL UNIQUE,
  check_limit               BIGINT       NOT NULL,
  result_event_limit        BIGINT       NOT NULL,
  result_retention_duration INTERVAL     NOT NULL
);

CREATE TABLE user_role (
  id      BIGSERIAL NOT NULL PRIMARY KEY,
  user_id BIGINT    NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
  role_id BIGINT    NOT NULL REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE "check" (
  id               BIGSERIAL     NOT NULL PRIMARY KEY,
  user_id          BIGINT        NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
  latest_result_id BIGINT        NULL,
  name             VARCHAR(255)  NOT NULL,
  url              VARCHAR(2000) NOT NULL,
  probe            VARCHAR(255)  NULL,
  status           status        NOT NULL DEFAULT 'UNKNOWN',
  state            state         NOT NULL DEFAULT 'WAITING',
  created          TIMESTAMP     NOT NULL,
  modified         TIMESTAMP     NOT NULL,
  refresh          TIMESTAMP     NOT NULL,
  locked           TIMESTAMP     NULL,
  interval         INT           NOT NULL,
  confirming       BOOLEAN       NOT NULL,
  version          BIGINT        NOT NULL
);

CREATE TABLE result (
  id                 BIGSERIAL    NOT NULL PRIMARY KEY,
  check_id           BIGINT       NOT NULL REFERENCES "check" (id) ON DELETE CASCADE,
  previous_result_id BIGINT       NULL,
  status             status       NOT NULL,
  probe              VARCHAR(255) NOT NULL,
  status_code        INT          NOT NULL,
  response_time      BIGINT       NULL,
  changed            BOOLEAN      NOT NULL,
  confirmation       BOOLEAN      NOT NULL,
  created            TIMESTAMP    NOT NULL,
  modified           TIMESTAMP    NOT NULL,
  version            BIGINT       NOT NULL
);

ALTER TABLE "check"
ADD FOREIGN KEY (latest_result_id) REFERENCES result (id);
ALTER TABLE result
ADD FOREIGN KEY (previous_result_id) REFERENCES result (id);