package simplejdbc;

import java.sql.SQLException;

import entity.DummyTable;
import entity.GoodsMst;
import entity.GoodsMstNoversion;
import simplejdbc.update.UpdateJdbcFactory;

/**
 * AbstractManageVersionJdbcのテストクラス
 * @author yasu
 *
 */
public class ManageVersionJdbcTest extends AbstractOracleTestCase {

	/**
	 * getNewVersionのテスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void testGetNewVersionOracle() throws SQLException {
		AbstractManageVersionJdbc<DummyTable> jdbc = (AbstractManageVersionJdbc<DummyTable>) UpdateJdbcFactory.create(dbConnection, DummyTable.class);
		String newVersion = jdbc.getNewVersionOracle();
		assertEquals(17, newVersion.length());
		assertTrue(newVersion.matches("[0-9]{17}"));
	}

	/**
	 * isIgnoreVersionのテスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void testIsIgnoreVersion() throws SQLException {

		//GoodsMstエンティティはバージョンカラムが存在する
		AbstractManageVersionJdbc<GoodsMst> jdbc = (AbstractManageVersionJdbc<GoodsMst>) UpdateJdbcFactory.create(dbConnection, GoodsMst.class);
		//デフォルト値は楽観的排他制御が有効となっている（無視フラグがfalse）
		assertFalse(jdbc.isIgnoreVersion());
		jdbc.setIgnoreVersion(true);
		assertTrue(jdbc.isIgnoreVersion());		//排他制御が無効になっている

		//DummyTableエンティティはバージョンカラムが存在しない
		AbstractManageVersionJdbc<GoodsMstNoversion> jdbc2 = (AbstractManageVersionJdbc<GoodsMstNoversion>) UpdateJdbcFactory.create(dbConnection, GoodsMstNoversion.class);
		//バージョンカラムが存在しない場合はデフォルトで楽観的排他制御を無効になる
		assertTrue(jdbc2.isIgnoreVersion());
		jdbc2.setIgnoreVersion(false);
		assertTrue(jdbc2.isIgnoreVersion());		//排他制御を有効にしても無効となる
	}

}
