package simplejdbc.sql;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;
import simplejdbc.annotation.Column;
import simplejdbc.annotation.Table;
/**
 * SelectSqlJdbcImplのテストクラス
 * @author yasu
 *
 */
public class SelectSqlJdbcImplTest extends AbstractOracleTestCase {

	/**
	 * selectSingleメソッドのテスト
	 * @throws SQLException
	 */
	public void testSelectSingle() throws SQLException {

		String tableName = "GOODS_MST";
		String owner = "SIMPLEJDBC";

		SelectSqlJdbc<UserTableColumn> selectJdbc = SelectSqlJdbcFactory.create(dbConnection, UserTableColumn.class);
		//カスタマイズSQLをセット
		selectJdbc.addSql(
			"SELECT " +
				" A.TABLE_NAME AS TABLE_NAME, " +
				" A.COLUMN_NAME AS COLUMN_NAME, " +
				" DATA_TYPE,  " +
				" DATA_LENGTH,  " +
				" DATA_SCALE,  " +
				" PKEY " +
			"FROM " +
				"USER_TAB_COLUMNS A, " +
				"(" +
				"SELECT ACC.TABLE_NAME, COLUMN_NAME, 'P' AS PKEY " +
				"FROM ALL_CONSTRAINTS AC, ALL_CONS_COLUMNS ACC " +
				"WHERE ACC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME " +
				"AND   ACC.OWNER = AC.OWNER " +
				"AND   AC.TABLE_NAME = ? " +
				"AND   ACC.TABLE_NAME = ? " +
				"AND   AC.OWNER = ? " +
				"AND   AC.CONSTRAINT_TYPE = 'P'" +
				") B " +
			"WHERE A.TABLE_NAME = B.TABLE_NAME " +
			"AND   A.COLUMN_NAME = B.COLUMN_NAME " +
			"AND   A.TABLE_NAME = ? ",
				tableName, tableName, owner, tableName);

		UserTableColumn column = selectJdbc.selectSingle();

		assertEquals(tableName, column.tableName);
		assertEquals("GOODS_ID", column.columnName);
		assertEquals("NUMBER", column.dataType);
		assertEquals(22, column.dataLength);
		assertEquals(0, column.dataScale);
		assertEquals("P", column.pkey);

		//該当レコードが空の場合
		SelectSqlJdbc<GoodsMst> selectSqlJdbc = SelectSqlJdbcFactory.create(dbConnection, GoodsMst.class);
		selectSqlJdbc.addSql(
				"SELECT GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE FROM GOODS_MST WHERE GOODS_ID = ?",
					-1);
		Object nullObject1 = null;
		Object nullObject2 = null;
		selectSqlJdbc.addSqlIfNotNull("AND GOODS_ID = ?", nullObject1, nullObject2);
		selectSqlJdbc.selectSingle();
	}

	/**
	 * selectListのテスト
	 * @throws SQLException
	 */
	public void testSelectList() throws SQLException {

		String tableName = "GOODS_MST";
		String owner = "SIMPLEJDBC";

		SelectSqlJdbc<UserTableColumn> selectJdbc = SelectSqlJdbcFactory.create(dbConnection, UserTableColumn.class);
		//カスタマイズSQLをセット
		selectJdbc.addSql(
			"SELECT " +
				" A.TABLE_NAME AS TABLE_NAME, " +
				" A.COLUMN_NAME AS COLUMN_NAME, " +
				" DATA_TYPE,  " +
				" DATA_LENGTH,  " +
				" DATA_SCALE,  " +
				" PKEY " +
			"FROM " +
				"USER_TAB_COLUMNS A, " +
				"(" +
				"SELECT ACC.TABLE_NAME, COLUMN_NAME, 'P' AS PKEY " +
				"FROM ALL_CONSTRAINTS AC, ALL_CONS_COLUMNS ACC " +
				"WHERE ACC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME " +
				"AND   ACC.OWNER = AC.OWNER " +
				"AND   AC.TABLE_NAME = ? " +
				"AND   ACC.TABLE_NAME = ? " +
				"AND   AC.OWNER = ? " +
				"AND   AC.CONSTRAINT_TYPE = 'P'" +
				") B " +
			"WHERE A.TABLE_NAME = B.TABLE_NAME (+) " +
			"AND   A.COLUMN_NAME = B.COLUMN_NAME (+) " +
			"AND   A.TABLE_NAME = ? " +
			"ORDER BY COLUMN_ID",
				tableName, tableName, owner, tableName);

		List<UserTableColumn> columns = selectJdbc.selectList();

		Iterator<UserTableColumn> it = columns.iterator();

		UserTableColumn column = it.next();
		assertEquals(tableName, column.tableName);
		assertEquals("GOODS_ID", column.columnName);
		assertEquals("NUMBER", column.dataType);
		assertEquals(22, column.dataLength);
		assertEquals(0, column.dataScale);
		assertEquals("P", column.pkey);

		column = it.next();
		assertEquals(tableName, column.tableName);
		assertEquals("GOODS_NAME", column.columnName);
		assertEquals("VARCHAR2", column.dataType);
		assertEquals(100, column.dataLength);
		assertEquals(0, column.dataScale);
		assertEquals(null, column.pkey);

		column = it.next();
		assertEquals(tableName, column.tableName);
		assertEquals("PRICE", column.columnName);
		assertEquals("NUMBER", column.dataType);
		assertEquals(22, column.dataLength);
		assertEquals(2, column.dataScale);
		assertEquals(null, column.pkey);

		column = it.next();
		assertEquals(tableName, column.tableName);
		assertEquals("REGIST_DATE", column.columnName);
		assertEquals("DATE", column.dataType);
		assertEquals(7, column.dataLength);
		assertEquals(0, column.dataScale);
		assertEquals(null, column.pkey);

		column = it.next();
		assertEquals(tableName, column.tableName);
		assertEquals("UPDATE_DATE", column.columnName);
		assertEquals("DATE", column.dataType);
		assertEquals(7, column.dataLength);
		assertEquals(0, column.dataScale);
		assertEquals(null, column.pkey);

		//該当レコードが空の場合
		SelectSqlJdbc<GoodsMst> selectSqlJdbc = SelectSqlJdbcFactory.create(dbConnection, GoodsMst.class);
		selectSqlJdbc.addSql(
				"SELECT GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE FROM GOODS_MST WHERE GOODS_ID = ?",
					-1);
		List<GoodsMst> emptyList = selectSqlJdbc.selectList();
		assertEquals(0, emptyList.size());
	}

	/**
	 * selectListのテスト
	 * @throws SQLException
	 */
	public void testSelectArray() throws SQLException {

		String tableName = "GOODS_MST";
		String owner = "SIMPLEJDBC";

		SelectSqlJdbc<UserTableColumn> selectJdbc = SelectSqlJdbcFactory.create(dbConnection, UserTableColumn.class);
		//カスタマイズSQLをセット
		selectJdbc.addSql(
			"SELECT " +
				" A.TABLE_NAME AS TABLE_NAME, " +
				" A.COLUMN_NAME AS COLUMN_NAME, " +
				" DATA_TYPE,  " +
				" DATA_LENGTH,  " +
				" DATA_SCALE,  " +
				" PKEY " +
			"FROM " +
				"USER_TAB_COLUMNS A, " +
				"(" +
				"SELECT ACC.TABLE_NAME, COLUMN_NAME, 'P' AS PKEY " +
				"FROM ALL_CONSTRAINTS AC, ALL_CONS_COLUMNS ACC " +
				"WHERE ACC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME " +
				"AND   ACC.OWNER = AC.OWNER " +
				"AND   AC.TABLE_NAME = ? " +
				"AND   ACC.TABLE_NAME = ? " +
				"AND   AC.OWNER = ? " +
				"AND   AC.CONSTRAINT_TYPE = 'P'" +
				") B " +
			"WHERE A.TABLE_NAME = B.TABLE_NAME (+) " +
			"AND   A.COLUMN_NAME = B.COLUMN_NAME (+) " +
			"AND   A.TABLE_NAME = ? " +
			"ORDER BY COLUMN_ID",
				tableName, tableName, owner, tableName);

		UserTableColumn[] columns = selectJdbc.selectArray();

		assertEquals(tableName, columns[0].tableName);
		assertEquals("GOODS_ID", columns[0].columnName);
		assertEquals("NUMBER", columns[0].dataType);
		assertEquals(22, columns[0].dataLength);
		assertEquals(0, columns[0].dataScale);
		assertEquals("P", columns[0].pkey);

		assertEquals(tableName, columns[1].tableName);
		assertEquals("GOODS_NAME", columns[1].columnName);
		assertEquals("VARCHAR2", columns[1].dataType);
		assertEquals(100, columns[1].dataLength);
		assertEquals(0, columns[1].dataScale);
		assertEquals(null, columns[1].pkey);

		assertEquals(tableName, columns[2].tableName);
		assertEquals("PRICE", columns[2].columnName);
		assertEquals("NUMBER", columns[2].dataType);
		assertEquals(22, columns[2].dataLength);
		assertEquals(2, columns[2].dataScale);
		assertEquals(null, columns[2].pkey);

		assertEquals(tableName, columns[3].tableName);
		assertEquals("REGIST_DATE", columns[3].columnName);
		assertEquals("DATE", columns[3].dataType);
		assertEquals(7, columns[3].dataLength);
		assertEquals(0, columns[3].dataScale);
		assertEquals(null, columns[3].pkey);

		assertEquals(tableName, columns[4].tableName);
		assertEquals("UPDATE_DATE", columns[4].columnName);
		assertEquals("DATE", columns[4].dataType);
		assertEquals(7, columns[4].dataLength);
		assertEquals(0, columns[4].dataScale);
		assertEquals(null, columns[4].pkey);

		//該当レコードが空の場合
		SelectSqlJdbc<GoodsMst> selectSqlJdbc = SelectSqlJdbcFactory.create(dbConnection, GoodsMst.class);
		selectSqlJdbc.addSql(
				"SELECT GOODS_ID, GOODS_NAME, PRICE, REGIST_DATE, UPDATE_DATE FROM GOODS_MST WHERE GOODS_ID = ?",
					-1);
		GoodsMst[] emptyArray = selectSqlJdbc.selectArray();
		assertEquals(0, emptyArray.length);
	}

	/**
	 * カスタマイズSQL用エンティティクラス
	 * @author yasu
	 */
	@Table(name="USER_TAB_COLUMNS")
	public static class UserTableColumn {

		@Column(name="TABLE_NAME")
		public String tableName;

		@Column(name="COLUMN_NAME")
		public String columnName;

		@Column(name="DATA_TYPE")
		public String dataType;

		@Column(name="DATA_LENGTH")
		public int dataLength;

		@Column(name="DATA_SCALE")
		public int dataScale;

		@Column(name="PKEY")
		public String pkey;
	}

}
