package simplejdbc.delete;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;

/**
 * DeleteJdbcFactoryのテストクラス
 * @author yasu
 *
 */
public class DeleteJdbcFactoryTest extends AbstractOracleTestCase {

	/**
	 * createのテスト
	 */
	public void testCreate() {
		DeleteJdbc<GoodsMst> deleteJdbc = DeleteJdbcFactory.create(dbConnection, GoodsMst.class);
		assertTrue(deleteJdbc instanceof DeleteJdbcImpl);
	}

}
