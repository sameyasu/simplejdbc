package simplejdbc.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import entity.GoodsMst;
import entity.GoodsMstDualPrimaryKey;
import entity.GoodsMstErrorNoPrimaryKey;
import entity.GoodsMstNoversion;
import simplejdbc.AbstractMysqlTestCase;
import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * SelectJdbcImplMysqlのテストクラス
 * @author yasu
 */
public class SelectJdbcImplMysqlTest extends AbstractMysqlTestCase {

	/**
	 * selectByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectByPrimaryKey() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;

		selectJdbc.selectByPrimaryKey(entity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals("りんご", entity.goodsName);
		assertEquals(100.0, entity.price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00"), entity.registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.updateDate);
		assertNull(entity.version);

		SelectJdbcImplMysql<GoodsMstDualPrimaryKey> selectJdbc2 = new SelectJdbcImplMysql<GoodsMstDualPrimaryKey>(dbConnection, GoodsMstDualPrimaryKey.class);

		GoodsMstDualPrimaryKey entity2 = new GoodsMstDualPrimaryKey();
		entity2.goodsId = 1;
		entity2.goodsName = "りんご";

		selectJdbc2.selectByPrimaryKey(entity2);

		assertEquals("りんご", entity2.goodsName);
		assertEquals(100.0, entity2.price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00"), entity2.registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entity2.updateDate);
		assertNull(entity.version);


		//該当レコードが存在しない場合
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst emptyEntity = new GoodsMst();
		emptyEntity.goodsId = -1;
		selectJdbc.selectByPrimaryKey(emptyEntity);
		assertNull(emptyEntity.goodsName);
		assertNull(entity.version);
	}

	/**
	 * selectByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectByPrimaryKey2() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//楽観的排他制御が無効となるテーブルでのテスト

		SelectJdbcImplMysql<GoodsMstNoversion> selectJdbc = new SelectJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);

		GoodsMstNoversion entity = new GoodsMstNoversion();
		entity.goodsId = 1;

		selectJdbc.selectByPrimaryKey(entity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals("りんご", entity.goodsName);
		assertEquals(100.0, entity.price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00"), entity.registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.updateDate);

	}

	/**
	 * selectListのテストメソッド
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectList() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.addOrderBy("GOODS_ID", "GOODS_NAME DESC");
		List<GoodsMst> entityList = selectJdbc.selectList();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		int counter = 1;
		for(GoodsMst entity: entityList) {
			if(counter == 1){
				assertEquals(1, (int) entity.goodsId);
				assertEquals("りんご", entity.goodsName);
				assertEquals(100.0, entity.price.doubleValue());
				assertEquals(formatter.parse("2009/09/14 00:00:00"), entity.registDate);
				assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.updateDate);
				assertNull(entity.version);
			}
			else if(counter == 2){
				assertEquals(2, (int) entity.goodsId);
				assertEquals("みかん", entity.goodsName);
				assertEquals(30.0, entity.price.doubleValue());
				assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.registDate);
				assertEquals(formatter.parse("2009/09/16 00:00:00"), entity.updateDate);
				assertNull(entity.version);
			}
			counter++;
		}

		//該当レコードが存在しない場合
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhere("GOODS_ID = ?", -1);
		selectJdbc.addOrderBy("GOODS_ID");
		List<GoodsMst> emptyEntityList = selectJdbc.selectList();
		assertEquals(0, emptyEntityList.size());
	}

	/**
	 * selectArrayのテストメソッド
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectArray() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.addOrderBy("GOODS_ID", "GOODS_NAME DESC");
		GoodsMst[] entityArray = selectJdbc.selectArray();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(1, (int) entityArray[0].goodsId);
		assertEquals("りんご", entityArray[0].goodsName);
		assertEquals(100.0, entityArray[0].price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00"), entityArray[0].registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entityArray[0].updateDate);
		assertNull(entityArray[0].version);

		assertEquals(2, (int) entityArray[1].goodsId);
		assertEquals("みかん", entityArray[1].goodsName);
		assertEquals(30.0, entityArray[1].price.doubleValue());
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entityArray[1].registDate);
		assertEquals(formatter.parse("2009/09/16 00:00:00"), entityArray[1].updateDate);
		assertNull(entityArray[1].version);

		//該当レコードが存在しない場合
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhere("GOODS_ID = ?", -1);
		selectJdbc.addOrderBy("GOODS_ID");
		GoodsMst[] emptyEntityArray = selectJdbc.selectArray();
		assertEquals(0, emptyEntityArray.length);
	}

	/**
	 * selectSingleのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectSingle() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.addOrderBy("GOODS_ID", "GOODS_NAME DESC");
		GoodsMst entity = selectJdbc.selectSingle();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(1, (int) entity.goodsId);
		assertEquals("りんご", entity.goodsName);
		assertEquals(100.0, entity.price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00"), entity.registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.updateDate);
		assertNull(entity.version);
	}

	/**
	 * andWhere,andWhereIfNotNullのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testAndWhere() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		selectJdbc.andWhereIfNotNull("GOODS_ID = ?", 2);

		Object nullObject = null;
		selectJdbc.andWhereIfNotNull("GOODS_NAME = ?", nullObject);	//この条件は無視されなければならない

		GoodsMst entity = selectJdbc.selectSingle();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(2, (int) entity.goodsId);
		assertEquals("みかん", entity.goodsName);
		assertEquals(30.0, entity.price.doubleValue());
		assertEquals(formatter.parse("2009/09/15 00:00:00"), entity.registDate);
		assertEquals(formatter.parse("2009/09/16 00:00:00"), entity.updateDate);
		assertNull(entity.version);

		//該当レコードがない場合
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhere("GOODS_ID = ?", nullObject);

		GoodsMst emptyEntity = selectJdbc.selectSingle();
		assertNull(emptyEntity);
	}

	/**
	 * selectCountのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectCount() throws SecurityException, NoSuchFieldException, SQLException, ParseException {
		//1件選択
		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhereIfNotNull("GOODS_ID = ?", 2);
		Object nullObject = null;
		selectJdbc.andWhereIfNotNull("GOODS_NAME = ?", nullObject);	//この条件は無視されなければならない
		assertEquals(1, selectJdbc.selectCount());
		//０件選択
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhereIfNotNull("GOODS_ID = ? OR GOODS_NAME = ?", 9999999, "存在しない商品名");
		assertEquals(0, selectJdbc.selectCount());
	}


	/**
	 * 異常系エラーテスト
	 * @throws SQLException
	 */
	public void testError() throws SQLException {
		SelectJdbc<GoodsMstErrorNoPrimaryKey> selectJdbc2 = SelectJdbcFactory.create(dbConnection, GoodsMstErrorNoPrimaryKey.class);
		GoodsMstErrorNoPrimaryKey entity = new GoodsMstErrorNoPrimaryKey();
		entity.goodsId = 1;
		try{
			selectJdbc2.selectByPrimaryKey(entity);
			fail();
		}
		catch(SimpleJdbcRuntimeException e){
			assertEquals("<entity.GoodsMstErrorNoPrimaryKey> プライマリキーが存在しないエンティティです。", e.getMessage());
		}
	}

	/**
	 * getForUpdateSqlStringのテスト
	 */
	public void testGetForUpdateSqlString() {
		SelectForUpdate selectForUpdate = new SelectForUpdate();
		selectForUpdate.setWaitSeconds(100000);

		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.setSelectForUpdate(selectForUpdate);

		assertEquals(" FOR UPDATE", selectJdbc.getForUpdateSqlString());

		selectForUpdate.setWaitSeconds(-99999999);
		assertEquals(" FOR UPDATE", selectJdbc.getForUpdateSqlString());

		selectForUpdate.setWaitSeconds(SelectForUpdate.WAITSECONDS_NOWAIT);
		assertEquals(" FOR UPDATE", selectJdbc.getForUpdateSqlString());

		selectForUpdate.setWaitSeconds(SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED);
		assertEquals(" FOR UPDATE", selectJdbc.getForUpdateSqlString());

		selectForUpdate.setWaitSeconds(0);
		assertEquals(" FOR UPDATE", selectJdbc.getForUpdateSqlString());

		//setSelectForUpdateを実行しない場合はFOR UPDATEしない
		SelectJdbcImplMysql<GoodsMst> selectJdbc2 = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		assertEquals("", selectJdbc2.getForUpdateSqlString());

		//nullをセットした場合にもFOR UPDATEしない
		selectJdbc2.setSelectForUpdate(null);
		assertEquals("", selectJdbc2.getForUpdateSqlString());
	}

	/**
	 * selectByPrimaryKeyのSELECT FOR UPDATE構文テスト(WAIT UNLIMITEDオプション)
	 * @throws SQLException
	 */
	public void testSelectByPrimaryKeyForUpdateWaitUnlimited() throws Exception {

		//コネクションを2つ使うために
		this.setUpDoubleDbConnection();

		//スレッド1
		//NOWAITオプションでのSELECTスレッド
		TestSelectForUpdateThread thread1 = new TestSelectForUpdateThread(this.dbConnection, SelectForUpdate.noWait());
		//開始
		thread1.start();

		//SQL問い合わせが終わるまで待つ
		while(thread1.isFinished() == false){
			Thread.sleep(100);
		}

		//例外が発生していないこと
		assertNull(thread1.getException());

		GoodsMst entity = thread1.getEntity();
		assertEquals("りんご", entity.goodsName);	//SELECTが正常終了したことの証明

		//スレッド2(永久に待ち続ける)
		TestSelectForUpdateThread thread2 = new TestSelectForUpdateThread(this.dbConnection2, SelectForUpdate.waitUnlimited());
		//遅れて開始
		thread2.start();

		//約2秒待つ　(100ミリ秒待機×20回＝約2秒)
		for(int i=0;i<20;i++) {
			Thread.sleep(100);
			assertFalse(thread2.isFinished());
		}

		GoodsMst entity2 = thread2.getEntity();
		assertNull(entity2.goodsName);	//SELECTがまだ終わっていない

		//スレッド1のトランザクション終了（コミット）
		this.dbConnection.commit();

		//SQL問い合わせが終わるまで待つ
		while(thread2.isFinished() == false){
			Thread.sleep(100);
		}

		//例外が発生していないこと
		assertNull(thread2.getException());
		assertEquals("りんご", entity2.goodsName);	//スレッド2のSELECTが正常終了したことの証明
	}

	/**
	 * selectByPrimaryKeyのSELECT FOR UPDATE構文テスト(WAITオプション)
	 * @throws SQLException
	 */
	public void testSelectByPrimaryKeyForUpdateWaitThread() throws Exception {

		//コネクションを2つ使うために
		this.setUpDoubleDbConnection();

		//スレッド1
		//NOWAITオプションでのSELECTスレッド
		TestSelectForUpdateThread thread1 = new TestSelectForUpdateThread(this.dbConnection, SelectForUpdate.noWait());
		//開始
		thread1.start();

		//SQL問い合わせが終わるまで待つ
		while(thread1.isFinished() == false){
			Thread.sleep(100);
		}

		//例外が発生していないこと
		assertNull(thread1.getException());

		GoodsMst entity = thread1.getEntity();
		assertEquals("りんご", entity.goodsName);	//SELECTが正常終了したことの証明

		//スレッド2（100秒待つ）
		TestSelectForUpdateThread thread2 = new TestSelectForUpdateThread(this.dbConnection2, SelectForUpdate.wait(100));
		//遅れて開始
		thread2.start();

		//約1秒待つ　(100ミリ秒待機×10回＝約1秒)
		for(int i=0;i<10;i++) {
			Thread.sleep(100);
			assertFalse(thread2.isFinished());
		}

		GoodsMst entity2 = thread2.getEntity();
		assertNull(entity2.goodsName);	//SELECTがまだ終わっていない

		//スレッド1のトランザクション終了（コミット）
		this.dbConnection.commit();

		//SQL問い合わせが終わるまで待つ
		while(thread2.isFinished() == false){
			Thread.sleep(100);
		}

		//例外が発生していないこと
		assertNull(thread2.getException());
		assertEquals("りんご", entity2.goodsName);	//スレッド2のSELECTが正常終了したことの証明
	}

	/**
	 * SELECT FOR UPDATEをテストするためのスレッドクラス
	 * @author yasu
	 */
	private static class TestSelectForUpdateThread extends Thread {

		protected SelectJdbcImplMysql<GoodsMst> selectJdbc = null;

		protected Exception exception = null;

		protected GoodsMst entity = null;

		private boolean finishFlag = false;

		protected TestSelectForUpdateThread(Connection connection, SelectForUpdate forUpdate) {
			selectJdbc = new SelectJdbcImplMysql<GoodsMst>(connection, GoodsMst.class);
			selectJdbc.setSelectForUpdate(forUpdate);
			entity = new GoodsMst();
			entity.goodsId = 1;
		}

		@Override
		public void run() {
			try {
				selectJdbc.selectByPrimaryKey(entity);
			} catch (Exception e) {
				exception = e;
			}
			finishFlag = true;
		}

		public Exception getException() {
			return exception;
		}

		public GoodsMst getEntity() {
			return entity;
		}

		public boolean isFinished() {
			return this.finishFlag;
		}
	}

}
