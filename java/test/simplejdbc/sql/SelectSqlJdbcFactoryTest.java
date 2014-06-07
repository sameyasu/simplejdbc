package simplejdbc.sql;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;

/**
 * SelectSqlJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class SelectSqlJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		SelectSqlJdbc<GoodsMst> selectSqlJdbc = SelectSqlJdbcFactory.create(dbConnection, GoodsMst.class);
		assertTrue(selectSqlJdbc instanceof SelectSqlJdbcImpl);
	}

}
