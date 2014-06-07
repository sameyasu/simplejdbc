package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * DUMMY_TABLEテーブルのエンティティクラス
 * @author simpleJdbc (AutoGen)
 */
@Table(name="DUMMY_TABLE")
public class DummyTable {

	@PrimaryKey
	@Column(name="DATE_COLUMN")
	public Date dateColumn;

	@Column(name="TIMESTAMP_COLUMN")
	public Timestamp timestampColumn;

	@Column(name="INT_COLUMN")
	public Integer intColumn;

	@Column(name="CHAR_COLUMN")
	public Character charColumn;

	@Column(name="FLOAT_COLUMN")
	public Float floatColumn;

	@Column(name="SHORT_COLUMN")
	public Short shortColumn;

	@Column(name="BIGINTEGER_COLUMN")
	public BigInteger bigintegerColumn;

	@Column(name="DOUBLE_COLUMN")
	public Double doubleColumn;

	@Column(name="LONG_COLUMN")
	public Long longColumn;

	@Column(name="BYTE_COLUMN")
	public Byte byteColumn;

	@Column(name="CLOB_COLUMN")
	public String clobColumn;

}
