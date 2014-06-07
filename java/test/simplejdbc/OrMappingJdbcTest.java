package simplejdbc;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.DummyTable;
import entity.DummyTablePrimitiveColumns;
import entity.GoodsMstErrorInvalidColumn;
import entity.GoodsMstErrorNoColumns;
import entity.GoodsMstErrorNoTable;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.insert.InsertJdbc;
import simplejdbc.insert.InsertJdbcFactory;
import simplejdbc.select.SelectJdbc;
import simplejdbc.select.SelectJdbcFactory;

/**
 * AbstractOrMappingJdbcのテストクラス
 * @author yasu
 *
 */
public class OrMappingJdbcTest extends AbstractOracleTestCase {


	/**
	 * 例外スローのテスト
	 */
	public void testThrowsException() {
		try {
			SelectJdbcFactory.create(dbConnection, GoodsMstErrorNoTable.class);
			fail();
		}catch(SimpleJdbcRuntimeException e){
			assertEquals("<entity.GoodsMstErrorNoTable> Tableアノテーションを付加していないエンティティクラスを指定しています", e.getMessage());
		}
		try {
			SelectJdbcFactory.create(dbConnection, GoodsMstErrorNoColumns.class);
			fail();
		}catch(SimpleJdbcRuntimeException e){
			assertEquals("<entity.GoodsMstErrorNoColumns> Columnアノテーションを1つも付加していないエンティティクラスを指定しています。", e.getMessage());
		}
		try {
			SelectJdbcFactory.create(dbConnection, GoodsMstErrorInvalidColumn.class);
			fail();
		}catch(SimpleJdbcRuntimeException e){
			assertEquals("<entity.GoodsMstErrorInvalidColumn> IncrementValueアノテーションのsequence属性が指定されていません。 フィールド名:goodsId", e.getMessage());
		}
	}

	/**
	 * bindメソッドのテスト
	 * @throws SQLException
	 */
	public void testBind() throws SQLException {
		DummyTable entity = new DummyTable();
		Date now = new Date();
		entity.dateColumn = now;
		entity.timestampColumn = new java.sql.Timestamp(now.getTime());
		entity.byteColumn = Byte.MAX_VALUE;
		entity.charColumn = Character.MAX_VALUE;
		entity.shortColumn = Short.MAX_VALUE;
		entity.intColumn = Integer.MAX_VALUE;
		entity.longColumn = Long.MAX_VALUE;
		entity.bigintegerColumn = new BigInteger("123456789012345678901234567890");	//30桁整数
		entity.floatColumn = 12345.1234f;
		entity.doubleColumn = 1234567890.0123456789;
		entity.clobColumn = "ABCDEFGHIJKLMNOPQRSTUVWXYZマルチバイト文字列";

		InsertJdbc<DummyTable> insertJdbc = InsertJdbcFactory.create(dbConnection, DummyTable.class);
		insertJdbc.insert(entity);

		DummyTable result = new DummyTable();
		result.dateColumn = now;
		SelectJdbc<DummyTable> selectJdbc = SelectJdbcFactory.create(dbConnection, DummyTable.class);
		selectJdbc.selectByPrimaryKey(result);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		assertEquals(format.format(now), format.format(result.dateColumn));
		assertEquals(format2.format(now), format2.format(result.timestampColumn));
		assertEquals(Byte.valueOf(Byte.MAX_VALUE), result.byteColumn);
		assertEquals(Character.valueOf(Character.MAX_VALUE), result.charColumn);
		assertEquals(Short.valueOf(Short.MAX_VALUE), result.shortColumn);
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), result.intColumn);
		assertEquals(Long.valueOf(Long.MAX_VALUE), result.longColumn);
		assertEquals("123456789012345678901234567890", result.bigintegerColumn.toString());
		assertEquals(12345.1234f, result.floatColumn);
		assertEquals(1234567890.0123456789, result.doubleColumn);
		assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZマルチバイト文字列", result.clobColumn);
	}

	/**
	 * bindメソッドのテスト
	 * @throws SQLException
	 */
	public void testBindPrimitive() throws SQLException {
		DummyTablePrimitiveColumns entity = new DummyTablePrimitiveColumns();
		Date now = new Date();
		entity.dateColumn = now;
		entity.timestampColumn = new java.sql.Timestamp(now.getTime());
		entity.byteColumn = Byte.MAX_VALUE;
		entity.charColumn = Character.MAX_VALUE;
		entity.shortColumn = Short.MAX_VALUE;
		entity.intColumn = Integer.MAX_VALUE;
		entity.longColumn = Long.MAX_VALUE;
		entity.bigintegerColumn = new BigInteger("123456789012345678901234567890");	//30桁整数
		entity.floatColumn = 12345.1234f;
		entity.doubleColumn = 1234567890.0123456789;
		entity.clobColumn = "ABCDEFGHIJKLMNOPQRSTUVWXYZマルチバイト文字列";

		InsertJdbc<DummyTablePrimitiveColumns> insertJdbc = InsertJdbcFactory.create(dbConnection, DummyTablePrimitiveColumns.class);
		insertJdbc.insert(entity);

		DummyTablePrimitiveColumns result = new DummyTablePrimitiveColumns();
		result.dateColumn = now;
		SelectJdbc<DummyTablePrimitiveColumns> selectJdbc = SelectJdbcFactory.create(dbConnection, DummyTablePrimitiveColumns.class);
		selectJdbc.selectByPrimaryKey(result);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		assertEquals(format.format(now), format.format(result.dateColumn));
		assertEquals(format2.format(now), format2.format(result.timestampColumn));
		assertEquals(Byte.MAX_VALUE, result.byteColumn);
		assertEquals(Character.MAX_VALUE, result.charColumn);
		assertEquals(Short.MAX_VALUE, result.shortColumn);
		assertEquals(Integer.MAX_VALUE, result.intColumn);
		assertEquals(Long.MAX_VALUE, result.longColumn);
		assertEquals("123456789012345678901234567890", result.bigintegerColumn.toString());
		assertEquals(12345.1234f, result.floatColumn);
		assertEquals(1234567890.0123456789, result.doubleColumn);
		assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZマルチバイト文字列", result.clobColumn);
	}

}
