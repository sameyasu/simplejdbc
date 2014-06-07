package simplejdbc.update;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;

/**
 * UpdateJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class UpdateJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		UpdateJdbc<GoodsMst> updateJdbc = UpdateJdbcFactory.create(dbConnection, GoodsMst.class);
		assertTrue(updateJdbc instanceof UpdateJdbcImplOracle);
	}

}
