CREATE TABLE member (
    id BIGINT AUTO_INCREMENT,
    userid VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (userid)
);

insert into member(userid, pw, roles) values ('admin', '$2a$10$XzoyNbw4v5C3PXpCxhlEv.W6bchW5FXfTPMOG8YgTV3lnzQn2vtOu', 'ADMIN');
