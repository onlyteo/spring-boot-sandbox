CREATE TABLE ACCOUNT
(
    ID              VARCHAR(50)  NOT NULL,
    URL             VARCHAR(200) NOT NULL,
    AVATAR          VARCHAR(200) NOT NULL,
    USERNAME        VARCHAR(100) NOT NULL,
    ACCOUNT         VARCHAR(200) NOT NULL,
    DISPLAY_NAME    VARCHAR(200) NOT NULL,
    FOLLOWERS_COUNT INTEGER      NOT NULL,
    FOLLOWING_COUNT INTEGER      NOT NULL,
    STATUSES_COUNT  INTEGER      NOT NULL,
    CREATED_AT      TIMESTAMP(6) NOT NULL,
    CONSTRAINT ACCOUNT_PK PRIMARY KEY (ID),
    CONSTRAINT ACCOUNT_ACCOUNT_UC UNIQUE (ACCOUNT)
);

CREATE TABLE STATUS
(
    ID                     VARCHAR(50)   NOT NULL,
    URL                    VARCHAR(200)  NOT NULL,
    LANGUAGE               VARCHAR(50)   NOT NULL,
    VISIBILITY             VARCHAR(50)   NOT NULL,
    CONTENT                VARCHAR(8000) NOT NULL,
    IN_REPLY_TO_ID         VARCHAR(50),
    IN_REPLY_TO_ACCOUNT_ID VARCHAR(50),
    REPLIES_COUNT          INTEGER       NOT NULL,
    REBLOGS_COUNT          INTEGER       NOT NULL,
    FAVOURITES_COUNT       INTEGER       NOT NULL,
    CREATED_AT             TIMESTAMP(6)  NOT NULL,
    ACCOUNT_ID             VARCHAR(50)   NOT NULL,
    CONSTRAINT STATUS_PK PRIMARY KEY (ID),
    CONSTRAINT STATUS_ACCOUNT_ID_FK FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT (ID)
);