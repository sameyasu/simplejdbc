package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;

import java.util.Date;
import java.math.BigInteger;

/**
 * DUMMY_TABLEテーブルのエンティティクラス
 * @author simpleJdbc (AutoGen)
 */
@Table(name="DUMMY_TABLE")
public class DummyTablePrimitiveColumns {

	@PrimaryKey
	@Column(name="DATE_COLUMN")
	public Date dateColumn;

	@Column(name="TIMESTAMP_COLUMN")
	public Date timestampColumn;

	@Column(name="INT_COLUMN")
	public int intColumn;

	@Column(name="CHAR_COLUMN")
	public char charColumn;

	@Column(name="FLOAT_COLUMN")
	public float floatColumn;

	@Column(name="SHORT_COLUMN")
	public short shortColumn;

	@Column(name="BIGINTEGER_COLUMN")
	public BigInteger bigintegerColumn;

	@Column(name="DOUBLE_COLUMN")
	public double doubleColumn;

	@Column(name="LONG_COLUMN")
	public long longColumn;

	@Column(name="BYTE_COLUMN")
	public byte byteColumn;

	@Column(name="CLOB_COLUMN")
	public String clobColumn;

}
