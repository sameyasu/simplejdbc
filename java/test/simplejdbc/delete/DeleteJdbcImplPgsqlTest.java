package simplejdbc.delete;

import java.sql.SQLException;
import java.text.ParseException;
import entity.GoodsMst;
import entity.GoodsMstErrorNoPrimaryKey;
import entity.GoodsMstNoversion;
import simplejdbc.AbstractPgsqlTestCase;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.select.SelectJdbcImplPgsql;
import simplejdbc.update.UpdateJdbcImplPgsql;

/**
 * DeleteJdbcImplのテストクラス
 * @author yasu
 */
public class DeleteJdbcImplPgsqlTest extends AbstractPgsqlTestCase {

	/**
	 * deleteByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testDeleteByPrimaryKey() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//削除前
		SelectJdbcImplPgsql<GoodsMst> selectJdbc = new SelectJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(entity);

		//NOT NULLのカラムが取得できていればレコードがある
		assertNotNull(entity.goodsName);
		assertNull(entity.version);		//VERSIONカラムの値はNULLでテスト

		DeleteJdbcImpl<GoodsMst> deleteJdbc = new DeleteJdbcImpl<GoodsMst>(dbConnection, GoodsMst.class);
		//1行削除（楽観的排他制御が有効になっているが、VERSIONカラムがNULLのため1件が正常に削除される。)
		assertEquals(1, deleteJdbc.deleteByPrimaryKey(entity));

		//更新後に取得した値と比較する
		selectJdbc = new SelectJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		//NOT NULLのカラムが取得できていなければ削除成功
		assertNull(selectedEntity.goodsName);
	}

	/**
	 * andWhereのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testAndWhere() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//削除前
		SelectJdbcImplPgsql<GoodsMst> selectJdbc = new SelectJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(entity);

		DeleteJdbcImpl<GoodsMst> deleteJdbc = new DeleteJdbcImpl<GoodsMst>(dbConnection, GoodsMst.class);

		deleteJdbc.andWhere("GOODS_ID = ?", 1);
		deleteJdbc.andWhereIfNotNull("GOODS_NAME = ?", "りんご");

		Object nullObject = null;
		deleteJdbc.andWhereIfNotNull("GOODS_NAME = ?", nullObject);	//ここはWHERE付加されないはず

		//1行削除
		assertEquals(1, deleteJdbc.delete());

		//更新後に取得した値と比較する
		selectJdbc = new SelectJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		//NOT NULLのカラムが取得できていなければ削除成功
		assertNull(selectedEntity.goodsName);
	}

	/**
	 * deleteByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testDeleteByPrimaryKey2() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//削除前
		SelectJdbcImplPgsql<GoodsMst> selectJdbc = new SelectJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(entity);

		//NOT NULLのカラムが取得できていればレコードがある
		assertNotNull(entity.goodsName);

		//排他制御チェック用に一度全カラム更新
		UpdateJdbcImplPgsql<GoodsMst> updateJdbc = new UpdateJdbcImplPgsql<GoodsMst>(dbConnection, GoodsMst.class);
		updateJdbc.updateAllColumns(entity);	//VERSIONカラムを最新の状態に更新

		DeleteJdbcImpl<GoodsMst> deleteJdbc = new DeleteJdbcImpl<GoodsMst>(dbConnection, GoodsMst.class);

		//ここでは楽観的排他制御により削除に失敗する
		try{
			deleteJdbc.deleteByPrimaryKey(entity);
			fail();	//成功してはならない
		}
		catch(Exception e){
			assertEquals(e.getClass().getName(), "simplejdbc.exception.UpdateOldRowException");
			assertEquals(e.getMessage(), "<entity.GoodsMst> 削除対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
		}

	}

	/**
	 * deleteByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testDeleteByPrimaryKey3() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//楽観的排他制御無効エンティティのテスト

		//削除前
		SelectJdbcImplPgsql<GoodsMstNoversion> selectJdbc = new SelectJdbcImplPgsql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);

		GoodsMstNoversion entity = new GoodsMstNoversion();
		entity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(entity);

		//NOT NULLのカラムが取得できていればレコードがある
		assertNotNull(entity.goodsName);

		DeleteJdbcImpl<GoodsMstNoversion> deleteJdbc = new DeleteJdbcImpl<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		//楽観的排他制御を有効にするメソッドを実行する（ただし、排他制御カラムが無いため排他制御が強制無効となるはず）
		deleteJdbc.setIgnoreVersion(false);

		//1行削除
		assertEquals(1, deleteJdbc.deleteByPrimaryKey(entity));

		//更新後に取得した値と比較する
		selectJdbc = new SelectJdbcImplPgsql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		GoodsMstNoversion selectedEntity = new GoodsMstNoversion();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		//NOT NULLのカラムが取得できていなければ削除成功
		assertNull(selectedEntity.goodsName);
	}

	/**
	 * 異常系エラーテスト
	 * @throws SQLException
	 */
	public void testError() throws SQLException {
		DeleteJdbc<GoodsMstErrorNoPrimaryKey> deleteJdbc = DeleteJdbcFactory.create(dbConnection, GoodsMstErrorNoPrimaryKey.class);
		GoodsMstErrorNoPrimaryKey entity = new GoodsMstErrorNoPrimaryKey();
		entity.goodsId = 1;
		try{
			deleteJdbc.deleteByPrimaryKey(entity);
			fail();
		}
		catch(SimpleJdbcRuntimeException e){
			assertEquals("<entity.GoodsMstErrorNoPrimaryKey> プライマリキーが存在しないエンティティです。", e.getMessage());
		}
	}
}
