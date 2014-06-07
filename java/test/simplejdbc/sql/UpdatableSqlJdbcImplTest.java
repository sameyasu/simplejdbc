package simplejdbc.sql;

import java.sql.SQLException;
import java.util.List;

import entity.GoodsMst;

import simplejdbc.AbstractOracleTestCase;
import simplejdbc.select.SelectJdbc;
import simplejdbc.select.SelectJdbcFactory;

/**
 * UpdatableSqlJdbcImplのテストクラス
 * @author yasu
 *
 */
public class UpdatableSqlJdbcImplTest extends AbstractOracleTestCase {

	/**
	 * executeUpdateのテスト
	 * @throws SQLException
	 */
	public void testExecuteUpdate() throws SQLException {

		//UPDATE文
		UpdatableSqlJdbc updatableJdbc = UpdatableSqlJdbcFactory.create(dbConnection);
		//複数行更新
		updatableJdbc.addSql("UPDATE GOODS_MST SET GOODS_NAME = ? ", "テスト商品名");
		updatableJdbc.executeUpdate();

		SelectJdbc<GoodsMst> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsMst.class);

		List<GoodsMst> entityList = selectJdbc.selectList();

		assertTrue(entityList.size() > 0);	//1件以上

		for(GoodsMst entity : entityList){
			//全て同じ商品名になっていれば良い
			assertEquals("テスト商品名", entity.goodsName);
		}

	}

	/**
	 * executeUpdateのテスト2
	 * @throws SQLException
	 */
	public void testExecuteUpdate2() throws SQLException {
		//UPDATE文
		UpdatableSqlJdbc updatableJdbc = UpdatableSqlJdbcFactory.create(dbConnection);
		//単一行更新
		updatableJdbc.addSql("UPDATE GOODS_MST SET GOODS_NAME = ? ", "テスト商品名");
		updatableJdbc.addSql("WHERE GOODS_ID = ? ", 1);
		Object nullObject = null;
		updatableJdbc.addSqlIfNotNull("AND GOODS_ID = ? ", nullObject);
		updatableJdbc.executeUpdate();

		SelectJdbc<GoodsMst> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsMst.class);

		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;
		selectJdbc.selectByPrimaryKey(entity);

		//同じ商品名になっていれば良い
		assertEquals("テスト商品名", entity.goodsName);
	}

}
