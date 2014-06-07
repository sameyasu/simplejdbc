-- テスト用テーブル作成
CREATE TABLE GOODS_MST
(
    GOODS_ID                       integer NOT NULL,		-- 商品ID
    GOODS_NAME                     character varying(100) NOT NULL,	-- 商品名
    PRICE                          double precision,			-- 価格
    REGIST_DATE                    timestamp with time zone,					-- 登録日時
    UPDATE_DATE                    timestamp with time zone,					-- 更新日時
    VERSION                        character varying(17),			-- 楽観的排他制御用
    CONSTRAINT GOODS_MST_PKEY PRIMARY KEY (GOODS_ID)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE GOODS_MST OWNER TO simplejdbc;

-- テスト用テーブル２作成
CREATE TABLE GOODS_UPDATE_TRANS
(
    TRANS_ID                       character varying(15) NOT NULL,	-- トランザクションID
    GOODS_ID                       integer NOT NULL,		-- 商品ID
    UPDATE_KIND                    character varying(1) NOT NULL,	-- I:挿入 U:更新 D:削除
    UPDATE_DATE                    timestamp with time zone NOT NULL,			-- 更新日時
    VERSION                        character varying(20),			-- 楽観的排他制御用
    CONSTRAINT GOODS_UPDATE_TRANS_PKEY PRIMARY KEY (TRANS_ID)
)
;

-- テスト用テーブル作成
CREATE TABLE GOODS_MST_NOVERSION
(
    GOODS_ID                       integer NOT NULL,		-- 商品ID
    GOODS_NAME                     character varying(100) NOT NULL,	-- 商品名
    PRICE                          double precision,			-- 価格
    REGIST_DATE                    timestamp with time zone,					-- 登録日時
    UPDATE_DATE                    timestamp with time zone,					-- 更新日時
    CONSTRAINT GOODS_MST_NOVERSION_PKEY PRIMARY KEY (GOODS_ID)
)
;

-- テスト用テーブル３作成
CREATE TABLE DUMMY_TABLE
(
    DATE_COLUMN                      date,
    TIMESTAMP_COLUMN                 timestamp with time zone,
    BYTE_COLUMN                      double precision,
    CHAR_COLUMN                      double precision,
    SHORT_COLUMN                     double precision,
    INT_COLUMN                       double precision,
    LONG_COLUMN                      double precision,
    BIGINTEGER_COLUMN                double precision,
    FLOAT_COLUMN                     double precision,
    DOUBLE_COLUMN                    double precision,
    CLOB_COLUMN                      text,
    CONSTRAINT DUMMY_TABLE_PKEY PRIMARY KEY (DATE_COLUMN)
)
;

CREATE TABLE DUAL_TABLE (
	DUMMY character varying(1)
);

-- シーケンス
CREATE SEQUENCE SEQ_GOODS_ID
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 100
  CACHE 1;
ALTER TABLE SEQ_GOODS_ID OWNER TO simplejdbc;


-- シーケンス２
CREATE SEQUENCE SEQ_TRANS_ID
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE SEQ_TRANS_ID OWNER TO simplejdbc;


-- データ挿入
INSERT INTO GOODS_MST (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (1, 'りんご', 100, TO_DATE('2009/09/14','YYYY/MM/DD'), TO_DATE('2009/09/15','YYYY/MM/DD'));
INSERT INTO GOODS_MST (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (2, 'みかん', 30, TO_DATE('2009/09/15','YYYY/MM/DD'), TO_DATE('2009/09/16','YYYY/MM/DD'));
INSERT INTO GOODS_MST_NOVERSION (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (1, 'りんご', 100, TO_DATE('2009/09/14','YYYY/MM/DD'), TO_DATE('2009/09/15','YYYY/MM/DD'));
INSERT INTO GOODS_MST_NOVERSION (GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE) VALUES (2, 'みかん', 30, TO_DATE('2009/09/15','YYYY/MM/DD'), TO_DATE('2009/09/16','YYYY/MM/DD'));
