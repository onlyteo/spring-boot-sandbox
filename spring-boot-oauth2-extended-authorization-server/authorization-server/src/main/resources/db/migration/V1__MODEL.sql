-- USERS
CREATE TABLE IF NOT EXISTS USERS
(
    USERNAME VARCHAR(200) NOT NULL PRIMARY KEY,
    PASSWORD VARCHAR(500) NOT NULL,
    ENABLED  BOOLEAN      NOT NULL
    );

-- AUTHORITIES
CREATE TABLE IF NOT EXISTS AUTHORITIES
(
    USERNAME  VARCHAR(200) NOT NULL,
    AUTHORITY VARCHAR(50)  NOT NULL,
    CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME),
    CONSTRAINT USERNAME_AUTHORITY UNIQUE (USERNAME, AUTHORITY)
    );

-- CONSENT
CREATE TABLE IF NOT EXISTS OAUTH2_AUTHORIZATION_CONSENT
(
    REGISTERED_CLIENT_ID VARCHAR(100)  NOT NULL,
    PRINCIPAL_NAME       VARCHAR(200)  NOT NULL,
    AUTHORITIES          VARCHAR(1000) NOT NULL,
    PRIMARY KEY (REGISTERED_CLIENT_ID, PRINCIPAL_NAME)
    );

-- SESSIONS
CREATE TABLE IF NOT EXISTS SPRING_SESSION
(
    PRIMARY_ID            CHARACTER(36) PRIMARY KEY NOT NULL,
    SESSION_ID            CHARACTER(36)             NOT NULL,
    CREATION_TIME         BIGINT                    NOT NULL,
    LAST_ACCESS_TIME      BIGINT                    NOT NULL,
    MAX_INACTIVE_INTERVAL INTEGER                   NOT NULL,
    EXPIRY_TIME           BIGINT                    NOT NULL,
    PRINCIPAL_NAME        CHARACTER VARYING(100)
    );
CREATE UNIQUE INDEX IF NOT EXISTS SPRING_SESSION_IX1 ON SPRING_SESSION USING BTREE (SESSION_ID);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX2 ON SPRING_SESSION USING BTREE (EXPIRY_TIME);
CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX3 ON SPRING_SESSION USING BTREE (PRINCIPAL_NAME);

-- SESSION ATTRIBUTES
CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHARACTER(36)          NOT NULL,
    ATTRIBUTE_NAME     CHARACTER VARYING(200) NOT NULL,
    ATTRIBUTE_BYTES    BYTEA                  NOT NULL,
    PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID)
    --MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE
    );

-- CLIENTS
CREATE TABLE IF NOT EXISTS OAUTH2_REGISTERED_CLIENT
(
    ID                            VARCHAR(100)                            NOT NULL,
    CLIENT_ID                     VARCHAR(100)                            NOT NULL,
    CLIENT_ID_ISSUED_AT           TIMESTAMP     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CLIENT_SECRET                 VARCHAR(200)  DEFAULT NULL,
    CLIENT_SECRET_EXPIRES_AT      TIMESTAMP     DEFAULT NULL,
    CLIENT_NAME                   VARCHAR(200)                            NOT NULL,
    CLIENT_AUTHENTICATION_METHODS VARCHAR(1000)                           NOT NULL,
    AUTHORIZATION_GRANT_TYPES     VARCHAR(1000)                           NOT NULL,
    REDIRECT_URIS                 VARCHAR(1000) DEFAULT NULL,
    POST_LOGOUT_REDIRECT_URIS     VARCHAR(1000) DEFAULT NULL,
    SCOPES                        VARCHAR(1000)                           NOT NULL,
    CLIENT_SETTINGS               VARCHAR(2000)                           NOT NULL,
    TOKEN_SETTINGS                VARCHAR(2000)                           NOT NULL,
    PRIMARY KEY (ID)
    );

-- AUTHORIZATION
CREATE TABLE IF NOT EXISTS OAUTH2_AUTHORIZATION
(
    ID                            VARCHAR(100) NOT NULL,
    REGISTERED_CLIENT_ID          VARCHAR(100) NOT NULL,
    PRINCIPAL_NAME                VARCHAR(200) NOT NULL,
    AUTHORIZATION_GRANT_TYPE      VARCHAR(100) NOT NULL,
    AUTHORIZED_SCOPES             VARCHAR(1000) DEFAULT NULL,
    ATTRIBUTES                    TEXT          DEFAULT NULL,
    STATE                         VARCHAR(500)  DEFAULT NULL,
    AUTHORIZATION_CODE_VALUE      TEXT          DEFAULT NULL,
    AUTHORIZATION_CODE_ISSUED_AT  TIMESTAMP     DEFAULT NULL,
    AUTHORIZATION_CODE_EXPIRES_AT TIMESTAMP     DEFAULT NULL,
    AUTHORIZATION_CODE_METADATA   TEXT          DEFAULT NULL,
    ACCESS_TOKEN_VALUE            TEXT          DEFAULT NULL,
    ACCESS_TOKEN_ISSUED_AT        TIMESTAMP     DEFAULT NULL,
    ACCESS_TOKEN_EXPIRES_AT       TIMESTAMP     DEFAULT NULL,
    ACCESS_TOKEN_METADATA         TEXT          DEFAULT NULL,
    ACCESS_TOKEN_TYPE             VARCHAR(100)  DEFAULT NULL,
    ACCESS_TOKEN_SCOPES           VARCHAR(1000) DEFAULT NULL,
    OIDC_ID_TOKEN_VALUE           TEXT          DEFAULT NULL,
    OIDC_ID_TOKEN_ISSUED_AT       TIMESTAMP     DEFAULT NULL,
    OIDC_ID_TOKEN_EXPIRES_AT      TIMESTAMP     DEFAULT NULL,
    OIDC_ID_TOKEN_METADATA        TEXT          DEFAULT NULL,
    REFRESH_TOKEN_VALUE           TEXT          DEFAULT NULL,
    REFRESH_TOKEN_ISSUED_AT       TIMESTAMP     DEFAULT NULL,
    REFRESH_TOKEN_EXPIRES_AT      TIMESTAMP     DEFAULT NULL,
    REFRESH_TOKEN_METADATA        TEXT          DEFAULT NULL,
    USER_CODE_VALUE               TEXT          DEFAULT NULL,
    USER_CODE_ISSUED_AT           TIMESTAMP     DEFAULT NULL,
    USER_CODE_EXPIRES_AT          TIMESTAMP     DEFAULT NULL,
    USER_CODE_METADATA            TEXT          DEFAULT NULL,
    DEVICE_CODE_VALUE             TEXT          DEFAULT NULL,
    DEVICE_CODE_ISSUED_AT         TIMESTAMP     DEFAULT NULL,
    DEVICE_CODE_EXPIRES_AT        TIMESTAMP     DEFAULT NULL,
    DEVICE_CODE_METADATA          TEXT          DEFAULT NULL,
    PRIMARY KEY (ID)
    );