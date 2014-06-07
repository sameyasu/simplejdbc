package simplejdbc.insert;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.LogFactory;

import entity.GoodsMst;
import entity.GoodsMstNoversion;
import entity.GoodsUpdateTrans;
import simplejdbc.AbstractOracleTestCase;
import simplejdbc.select.SelectJdbc;
import simplejdbc.select.SelectJdbcFactory;

/**
 * InsertJdbcImplOracleのテストクラス
 * @author yasu
 */
public class InsertJdbcImplOracleTest extends AbstractOracleTestCase {

	/**
	 * insertのテスト
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsert() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplOracle<GoodsMst> insertJdbc = new InsertJdbcImplOracle<GoodsMst>(dbConnection, GoodsMst.class);
		Date now = new Date();

		GoodsMst entity = new GoodsMst();
		entity.goodsId = 3;
		entity.goodsName = "いちご";
		entity.price = BigDecimal.valueOf(45.5);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;

		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbc<GoodsMst> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = entity.goodsId;	//プライマリキーだけ同じ
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
		assertEquals(entity.version, selectedEntity.version);
		assertNotNull(entity.version);
		LogFactory.getLog(InsertJdbcImplOracleTest.class).debug("VERSION:"+entity.version);
	}

	/**
	 * insertのテスト2
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsertByIncrementColumn() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplOracle<GoodsMst> insertJdbc = new InsertJdbcImplOracle<GoodsMst>(dbConnection, GoodsMst.class);
		Date now = new Date();

		GoodsMst entity = new GoodsMst();
		entity.goodsId = null;	//自動インクリメント
		entity.goodsName = "テスト対象データ";
		entity.price = BigDecimal.valueOf(124.26);
		entity.registDate = now;
		entity.updateDate = null;
		entity.version = null;

		//無事にINSERTが完了すればOK
		assertEquals(1, insertJdbc.insert(entity));
	}

	/**
	 * insertのテスト3
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsertByIncrementSql() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplOracle<GoodsUpdateTrans> insertJdbc = new InsertJdbcImplOracle<GoodsUpdateTrans>(dbConnection, GoodsUpdateTrans.class);
		Date now = new Date();

		GoodsUpdateTrans entity = new GoodsUpdateTrans();
		entity.transId = null;	//自動インクリメント
		entity.goodsId = 1;
		entity.updateKind = "I";
		entity.updateDate = now;
		entity.version = null;

		//無事にINSERTが完了すればOK
		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbc<GoodsUpdateTrans> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsUpdateTrans.class);
		GoodsUpdateTrans selectedEntity = new GoodsUpdateTrans();
		selectedEntity.transId = entity.transId;
		assertTrue(selectJdbc.selectByPrimaryKey(selectedEntity));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.transId, selectedEntity.transId);
		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.updateKind, selectedEntity.updateKind);
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
		assertEquals(entity.version, selectedEntity.version);
	}

	/**
	 * insertのテスト
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsert2() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplOracle<GoodsMst> insertJdbc = new InsertJdbcImplOracle<GoodsMst>(dbConnection, GoodsMst.class);
		//楽観的排他制御を無視する設定
		insertJdbc.setIgnoreVersion(true);
		Date now = new Date();

		GoodsMst entity = new GoodsMst();
		entity.goodsId = 3;
		entity.goodsName = "いちご";
		entity.price = BigDecimal.valueOf(45.5);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;

		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbc<GoodsMst> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsMst.class);
		GoodsMst selectedEntity = new GoodsMst();
		selectedEntity.goodsId = entity.goodsId;	//プライマリキーだけ同じ
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
		assertEquals(entity.version, selectedEntity.version);
		assertNull(entity.version);

		LogFactory.getLog(InsertJdbcImplOracleTest.class).debug("VERSION:"+entity.version);
	}

	/**
	 * insertのテスト
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsert3() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//楽観的排他制御無効エンティティのテスト

		InsertJdbcImplOracle<GoodsMstNoversion> insertJdbc = new InsertJdbcImplOracle<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		//楽観的排他制御を有効にするメソッドを実行する（ただし、排他制御カラムが無いため排他制御が強制無効となるはず）
		insertJdbc.setIgnoreVersion(false);

		Date now = new Date();

		GoodsMstNoversion entity = new GoodsMstNoversion();
		entity.goodsId = 3;
		entity.goodsName = "いちご";
		entity.price = BigDecimal.valueOf(45.5);
		entity.registDate = now;
		entity.updateDate = now;

		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbc<GoodsMstNoversion> selectJdbc = SelectJdbcFactory.create(dbConnection, GoodsMstNoversion.class);
		GoodsMstNoversion selectedEntity = new GoodsMstNoversion();
		selectedEntity.goodsId = entity.goodsId;	//プライマリキーだけ同じ
		selectJdbc.selectByPrimaryKey(selectedEntity);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertEquals(entity.goodsId, selectedEntity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price.doubleValue(), selectedEntity.price.doubleValue());
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));

	}

	/**
	 * insertのテスト2
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsertByIncrementColumn2() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		//楽観的排他制御無効エンティティのテスト

		InsertJdbcImplOracle<GoodsMstNoversion> insertJdbc = new InsertJdbcImplOracle<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
		//楽観的排他制御を有効にするメソッドを実行する（ただし、排他制御カラムが無いため排他制御が強制無効となるはず）
		insertJdbc.setIgnoreVersion(false);
		Date now = new Date();

		GoodsMstNoversion entity = new GoodsMstNoversion();
		entity.goodsId = null;	//自動インクリメント
		entity.goodsName = "テスト対象データ";
		entity.price = BigDecimal.valueOf(124.26);
		entity.registDate = now;
		entity.updateDate = now;

		//無事にINSERTが完了すればOK
		assertEquals(1, insertJdbc.insert(entity));
	}
}
