package simplejdbc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.GoodsMstDao;

import entity.GoodsMst;
import simplejdbc.AbstractOracleTestCase;

/**
 * AbstractStandardDaoのテストクラス
 * @author yasu
 *
 */
public class StandardDaoTest extends AbstractOracleTestCase {

	/**
	 * テストに使うDaoクラス
	 */
	private GoodsMstDao goodsMstDao;

	/**
	 * 実行前準備
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.goodsMstDao = new GoodsMstDao(this.dbConnection);
	}

	/**
	 * selectByPrimaryKeyのテスト
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSelectByPrimaryKey() throws SecurityException, NoSuchFieldException, SQLException, ParseException {
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;

		this.goodsMstDao.selectByPrimaryKey(entity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		assertEquals("りんご", entity.goodsName);
		assertEquals(100.0, entity.price.doubleValue());
		assertEquals(formatter.parse("2009/09/14 00:00:00.000"), entity.registDate);
		assertEquals(formatter.parse("2009/09/15 00:00:00.000"), entity.updateDate);
	}

	/**
	 * insertのテスト
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsert() throws SecurityException, NoSuchFieldException, SQLException, ParseException {
		Date now = new Date();
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 3;
		entity.goodsName = "いちご";
		entity.price = BigDecimal.valueOf(45.5);
		entity.registDate = now;
		entity.updateDate = now;

		assertEquals(1, this.goodsMstDao.insert(entity));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		//取得した値と比較する
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = entity.goodsId;
		this.goodsMstDao.selectByPrimaryKey(selectedEntity);

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(dateFormat.format(entity.registDate), dateFormat.format(selectedEntity.registDate));
		assertEquals(dateFormat.format(entity.updateDate), dateFormat.format(selectedEntity.updateDate));
	}

	/**
	 * updateAllColumnsのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testUpdateAllColumns() throws SecurityException, NoSuchFieldException, SQLException, ParseException {
		Date now = new Date();
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//ここは変えてはいけません。(プライマリキーなので)
		entity.goodsName = "新しいりんご";
		entity.price = BigDecimal.valueOf(99.74);
		entity.registDate = now;
		entity.updateDate = now;

		assertEquals(1, this.goodsMstDao.updateAllColumns(entity));

		//更新後に取得した値と比較する
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		this.goodsMstDao.selectByPrimaryKey(selectedEntity);

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
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		this.goodsMstDao.selectByPrimaryKey(entity);

		Date now = new Date();
		//更新するフィールドだけセット
		entity.goodsName = "新しいりんご";
		entity.updateDate = now;
		entity.price = BigDecimal.valueOf(123.0);

		assertEquals(1, this.goodsMstDao.updateColumns(entity, "goodsName", "updateDate", "price"));

		//更新後に取得した値と比較する
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		this.goodsMstDao.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
	}

	/**
	 * deleteByPrimaryKeyのテストメソッド
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testDeleteByPrimaryKey() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//更新後に取得した値と比較する
		GoodsMst entity = new GoodsMst();
		entity.goodsId = 1;	//プライマリキー
		this.goodsMstDao.selectByPrimaryKey(entity);

		//NOT NULLのカラムが取得できていればレコードがある
		assertNotNull(entity.goodsName);

		//1行削除
		assertEquals(1, this.goodsMstDao.deleteByPrimaryKey(entity));

		//更新後に取得した値と比較する
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = 1;	//プライマリキー
		this.goodsMstDao.selectByPrimaryKey(selectedEntity);

		//NOT NULLのカラムが取得できていなければ削除成功
		assertNull(selectedEntity.goodsName);
	}
}
