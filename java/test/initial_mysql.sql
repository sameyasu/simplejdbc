CREATE DATABASE simplejdbcdb CHARSET utf8;

GRANT ALL ON `simplejdbcdb`.* TO 'simplejdbc'@localhost IDENTIFIED BY 'simplejdbc';
GRANT ALL ON `simplejdbcdb`.* TO 'simplejdbc'@'%' IDENTIFIED BY 'simplejdbc';
FLUSH PRIVILEGES;

USE simplejdbcdb;

CREATE TABLE GOODS_MST
(
    GOODS_ID                       integer NOT NULL AUTO_INCREMENT,
    GOODS_NAME                     varchar(100) NOT NULL,
    PRICE                          double precision,
    REGIST_DATE                    datetime,
    UPDATE_DATE                    datetime,
    VERSION                        varchar(20),
    CONSTRAINT GOODS_MST_PKEY PRIMARY KEY (GOODS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE GOODS_UPDATE_TRANS
(
    TRANS_ID                       varchar(15) NOT NULL,
    GOODS_ID                       integer NOT NULL,
    UPDATE_KIND                    varchar(1) NOT NULL,
    UPDATE_DATE                    datetime NOT NULL,
    VERSION                        varchar(20),
    CONSTRAINT GOODS_UPDATE_TRANS_PKEY PRIMARY KEY (TRANS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE GOODS_MST_NOVERSION
(
    GOODS_ID                       integer NOT NULL AUTO_INCREMENT,
    GOODS_NAME                     varchar(100) NOT NULL,
    PRICE                          double precision,
    REGIST_DATE                    datetime,
    UPDATE_DATE                    datetime,
    CONSTRAINT GOODS_MST_NOVERSION_PKEY PRIMARY KEY (GOODS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE GOODS_MST_MYISAM
(
    GOODS_ID                       integer NOT NULL AUTO_INCREMENT,
    GOODS_NAME                     varchar(100) NOT NULL,
    PRICE                          double precision,
    REGIST_DATE                    datetime,
    UPDATE_DATE                    datetime,
    VERSION                        varchar(20),
    CONSTRAINT GOODS_MST_PKEY PRIMARY KEY (GOODS_ID)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE SEQ_TRANS_ID
(
    SEED_TRANS_ID                       integer NOT NULL AUTO_INCREMENT,
    CONSTRAINT SEQ_TRANS_ID_PKEY PRIMARY KEY (SEED_TRANS_ID)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE DUAL_TABLE (
 DUMMY character varying(1)
);

INSERT INTO GOODS_MST (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (1, 'りんご', 100, STR_TO_DATE('2009/09/14','%Y/%m/%d'), STR_TO_DATE('2009/09/15','%Y/%m/%d'));
INSERT INTO GOODS_MST (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (2, 'みかん', 30, STR_TO_DATE('2009/09/15','%Y/%m/%d'), STR_TO_DATE('2009/09/16','%Y/%m/%d'));
INSERT INTO GOODS_MST_NOVERSION (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (1, 'りんご', 100, STR_TO_DATE('2009/09/14','%Y/%m/%d'), STR_TO_DATE('2009/09/15','%Y/%m/%d'));
INSERT INTO GOODS_MST_NOVERSION (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (2, 'みかん', 30, STR_TO_DATE('2009/09/15','%Y/%m/%d'), STR_TO_DATE('2009/09/16','%Y/%m/%d'));
