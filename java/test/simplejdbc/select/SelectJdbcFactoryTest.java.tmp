package simplejdbc.select;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;

/**
 * SelectJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class SelectJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		SelectJdbc<GoodsMst> goodsMst = SelectJdbcFactory.create(dbConnection, GoodsMst.class);
		assertTrue(goodsMst instanceof SelectJdbcImplOracle);
	}

}
