package simplejdbc.sql;

import simplejdbc.AbstractOracleTestCase;

/**
 * UpdatableSqlJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class UpdatableSqlJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		UpdatableSqlJdbc updateSqlJdbc = UpdatableSqlJdbcFactory.create(dbConnection);
		assertTrue(updateSqlJdbc instanceof UpdatableSqlJdbcImpl);
	}

}
