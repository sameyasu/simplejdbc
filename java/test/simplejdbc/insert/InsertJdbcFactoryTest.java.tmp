package simplejdbc.insert;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;

/**
 * InsertJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class InsertJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		InsertJdbc<GoodsMst> insertJdbc = InsertJdbcFactory.create(dbConnection, GoodsMst.class);
		assertTrue(insertJdbc instanceof InsertJdbcImplOracle);
	}

}
