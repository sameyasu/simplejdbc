package simplejdbc.insert;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.LogFactory;

import entity.GoodsMst;
import entity.GoodsMstMysql;
import entity.GoodsMstNoversion;
import entity.GoodsUpdateTransMysql;
import simplejdbc.AbstractMysqlTestCase;
import simplejdbc.select.SelectJdbcImplMysql;

/**
 * InsertJdbcImplMysqlのテストクラス
 * @author yasu
 */
public class InsertJdbcImplMysqlTest extends AbstractMysqlTestCase {

	/**
	 * insertのテスト
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsert() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplMysql<GoodsMst> insertJdbc = new InsertJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
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
		SelectJdbcImplMysql<GoodsMst> selectJdbc = new SelectJdbcImplMysql<GoodsMst>(dbConnection, GoodsMst.class);
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
		LogFactory.getLog(InsertJdbcImplMysqlTest.class).debug("VERSION:"+entity.version);
	}

	/**
	 * insertのテスト2
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsertByIncrementColumn() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplMysql<GoodsMstMysql> insertJdbc = new InsertJdbcImplMysql<GoodsMstMysql>(dbConnection, GoodsMstMysql.class);
		Date now = new Date();

		GoodsMstMysql entity = new GoodsMstMysql();
		entity.goodsId = null;	//自動インクリメント
		entity.goodsName = "テスト対象データ";
		entity.price = BigDecimal.valueOf(124.26);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;

		//無事にINSERTが完了すればOK
		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbcImplMysql<GoodsMstMysql> selectJdbc = new SelectJdbcImplMysql<GoodsMstMysql>(dbConnection, GoodsMstMysql.class);
		GoodsMstMysql selectedEntity = new GoodsMstMysql();
		selectedEntity.goodsId = entity.goodsId;
		assertTrue(selectJdbc.selectByPrimaryKey(selectedEntity));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		assertNotNull(entity.goodsId);
		assertEquals(entity.goodsName, selectedEntity.goodsName);
		assertEquals(entity.price, selectedEntity.price);
		assertEquals(formatter.format(entity.registDate), formatter.format(selectedEntity.registDate));
		assertEquals(formatter.format(entity.updateDate), formatter.format(selectedEntity.updateDate));
		assertNotNull(selectedEntity.version);
	}

	/**
	 * insertのテスト3
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testInsertByIncrementSql() throws SecurityException, NoSuchFieldException, SQLException, ParseException {

		InsertJdbcImplMysql<GoodsUpdateTransMysql> insertJdbc = new InsertJdbcImplMysql<GoodsUpdateTransMysql>(dbConnection, GoodsUpdateTransMysql.class);
		Date now = new Date();

		GoodsUpdateTransMysql entity = new GoodsUpdateTransMysql();
		entity.transId = null;	//自動インクリメント
		entity.goodsId = 1;
		entity.updateKind = "I";
		entity.updateDate = now;
		entity.version = null;

		//無事にINSERTが完了すればOK
		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbcImplMysql<GoodsUpdateTransMysql> selectJdbc = new SelectJdbcImplMysql<GoodsUpdateTransMysql>(dbConnection, GoodsUpdateTransMysql.class);
		GoodsUpdateTransMysql selectedEntity = new GoodsUpdateTransMysql();
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

		InsertJdbcImplMysql<GoodsMstMysql> insertJdbc = new InsertJdbcImplMysql<GoodsMstMysql>(dbConnection, GoodsMstMysql.class);
		//楽観的排他制御を無視する設定
		insertJdbc.setIgnoreVersion(true);
		Date now = new Date();

		GoodsMstMysql entity = new GoodsMstMysql();
		entity.goodsId = 3;
		entity.goodsName = "いちご";
		entity.price = BigDecimal.valueOf(45.5);
		entity.registDate = now;
		entity.updateDate = now;
		entity.version = null;

		assertEquals(1, insertJdbc.insert(entity));

		//取得した値と比較する
		SelectJdbcImplMysql<GoodsMstMysql> selectJdbc = new SelectJdbcImplMysql<GoodsMstMysql>(dbConnection, GoodsMstMysql.class);
		GoodsMstMysql selectedEntity = new GoodsMstMysql();
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

		LogFactory.getLog(InsertJdbcImplMysqlTest.class).debug("VERSION:"+entity.version);
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

		InsertJdbcImplMysql<GoodsMstNoversion> insertJdbc = new InsertJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
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
		SelectJdbcImplMysql<GoodsMstNoversion> selectJdbc = new SelectJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
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

		InsertJdbcImplMysql<GoodsMstNoversion> insertJdbc = new InsertJdbcImplMysql<GoodsMstNoversion>(dbConnection, GoodsMstNoversion.class);
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
