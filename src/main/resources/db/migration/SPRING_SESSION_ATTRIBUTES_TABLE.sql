CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL
    , ATTRIBUTE_NAME VARCHAR(200) NOT NULL
    , ATTRIBUTE_BYTES BLOB NOT NULL
    , PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME)
    , FOREIGN KEY FK_SPRING_SES_ATTRS_SES_PR_ID (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);