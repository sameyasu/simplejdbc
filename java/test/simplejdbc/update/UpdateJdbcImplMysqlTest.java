package simplejdbc.update;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.GoodsMst;
import entity.GoodsMstNoversion;
import simplejdbc.AbstractMysqlTestCase;
import simplejdbc.select.SelectJdbcImplMysql;

/**
 * UpdateJdbcImplMysqlのテストクラス
 * @author yasu
 */
public class UpdateJdbcImplMysqlTest extends AbstractMysqlTestCase {

	/**
	 * updateAllColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateAllColumns() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		UpdateJdbcImplMysql<GoodsMst> updateJdbc = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		Date now = new Date();
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entity.goodsName = "新しいりんご";
		entity.price = BigDecimal.valueOf(99.74);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;

		assertEquals(1, updateJdbc.updateAllColumns(entity));

		//更新後に取得した値と比較する
		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
	}

	/**
	 * updateColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateColums() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//更新前の状態を取得
		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(entity);

		UpdateJdbcImplMysql<GoodsMst> updateJdbc = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		Date now = new Date();
		//更新するフィールドだけセット
		entity.goodsName = "新しいりんご";
		entity.updateDate = now;
		entity.price = BigDecimal.valueOf(123.0);
		entity.version = null;

		assertEquals(1, updateJdbc.updateColumns(entity, "goodsName", "updateDate", "price"));

		//更新後に取得した値と比較する
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
	}

	/**
	 * andWhereのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testAndWhere() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//更新前の状態を取得
		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		selectJdbc.andWhere("GOODS_ID = ?", 1);
		selectJdbc.andWhere("GOODS_NAME = ?", "りんご");
		GoodsMst beforeEntity = selectJdbc.selectSingle();

		UpdateJdbcImplMysql<GoodsMst> updateJdbc = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		Date now = new Date();
		//更新するフィールドだけセット
		GoodsMst entity = new GoodsMst();
		entity.goodsName = "新しいりんご";
		entity.updateDate = now;
		entity.price = BigDecimal.valueOf(123.0);

		updateJdbc.andWhere("GOODS_ID = ?", 1);
		updateJdbc.andWhereIfNotNull("GOODS_NAME = ?", "りんご");

		Object nullObject = null;
		updateJdbc.andWhereIfNotNull("GOODS_NAME = ?", nullObject);	//ここはWHERE付加されないはず

		assertEquals(1, updateJdbc.updateColumns(entity, "goodsName", "updateDate", "price"));

		//更新後に取得した値と比較する
		selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(beforeEntity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(beforeEntity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
	}

	/**
	 * updateAllColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateAllColumns2() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		UpdateJdbcImplMysql<GoodsMst> updateJdbc = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		Date now = new Date();
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entity.goodsName = "新しいりんご";
		entity.price = BigDecimal.valueOf(99.74);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;			//NULL にしても更新時には最新のVERSION値に更新される。

		assertEquals(1, updateJdbc.updateAllColumns(entity));

		UpdateJdbcImplMysql<GoodsMst> updateJdbc2 = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		GoodsMst entityAfter = new GoodsMst();
		entityAfter.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entityAfter.goodsName = "新しいりんご";
		entityAfter.price = BigDecimal.valueOf(99.74);
		entityAfter.registDate = now;
		entityAfter.updateDate = now;
		entityAfter.version = "20090909123456123";		//古いVERSIONカラム値

		//ここでは楽観的排他制御により更新に失敗する
		try{
			updateJdbc2.updateAllColumns(entityAfter);
			fail();	//成功してはならない
		}
		catch(Exception e){
			assertEquals(e.getClass().getName(), "simplejdbc.exception.UpdateOldRowException");
			assertEquals(e.getMessage(), "<entity.GoodsMst> 更新対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
		}

		UpdateJdbcImplMysql<GoodsMst> updateJdbc3 = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		//楽観的排他制御を無視
		updateJdbc3.setIgnoreVersion(true);

		//ここでは楽観的排他制御を無視するため正常に更新できる。
		//（VERSIONカラムの値は更新する条件に入らない。）
		assertEquals(1, updateJdbc3.updateAllColumns(entityAfter));
	}

	/**
	 * updateAllColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateAllColumns3() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		UpdateJdbcImplMysql<GoodsMst> updateJdbc = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		//楽観的排他制御を無視する
		updateJdbc.setIgnoreVersion(true);

		Date now = new Date();
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entity.goodsName = "新しいりんご";
		entity.price = BigDecimal.valueOf(99.74);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;		//楽観的排他制御が無視されているのでNULLのまま更新される

		assertEquals(1, updateJdbc.updateAllColumns(entity));

		UpdateJdbcImplMysql<GoodsMst> updateJdbc2 = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);

		GoodsMst entityAfter = new GoodsMst();
		entityAfter.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entityAfter.goodsName = "新しいりんご";
		entityAfter.price = BigDecimal.valueOf(99.74);
		entityAfter.registDate = now;
		entityAfter.updateDate = now;
		entityAfter.version = "20090909123456123";

		//ここでは楽観的排他制御により更新するが、VERSIONカラムがNULLなのでどのようなVERSIONでも更新される。
		assertEquals(1, updateJdbc2.updateAllColumns(entityAfter));

		//ヒットしないはずのレコードを更新する。
		UpdateJdbcImplMysql<GoodsMst> updateJdbc3 = new UpdateJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
		//楽観的排他制御を無視
		updateJdbc3.setIgnoreVersion(true);

		GoodsMst notfoundEntity = new GoodsMst();
		notfoundEntity.goodsId = -99;	//ヒットしないプライマリキー
		notfoundEntity.goodsName = "新しいりんご";
		notfoundEntity.price = BigDecimal.valueOf(99.74);
		notfoundEntity.registDate = now;
		notfoundEntity.updateDate = now;
		notfoundEntity.version = "20090909123456123";

		//ここでは楽観的排他制御を無視するため正常に更新できるが、ヒットしないため0件で更新件数が返る。
		//（VERSIONカラムの値は更新する条件に入らない。）
		assertEquals(0, updateJdbc3.updateAllColumns(notfoundEntity));

	}

	/**
	 * updateAllColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateAllColumns4() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//楽観的排他制御無効エンティティのテスト

		UpdateJdbcImplMysql<GoodsMstNoversion> updateJdbc = new UpdateJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		//楽観的排他制御を有効にするメソッドを実行する（ただし、排他制御カラムが無いため排他制御が強制無効となるはず）
		updateJdbc.setIgnoreVersion(false);

		Date now = new Date();
		GoodsMstNoversion entity = new GoodsMstNoversion();
		entity.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entity.goodsName = "新しいりんご";
		entity.price = BigDecimal.valueOf(99.74);
		entity.registDate = now;
		entity.updateDate = now;

		assertEquals(1, updateJdbc.updateAllColumns(entity));

		//更新後に取得した値と比較する
		SelectJdbcImplMysql<GoodsMstNoversion> selectJdbc = new SelectJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		GoodsMstNoversion selectedEntity = new GoodsMstNoversion();
		selectedEntity.goodsId = 1;	//プライマリキー
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
	}
}
