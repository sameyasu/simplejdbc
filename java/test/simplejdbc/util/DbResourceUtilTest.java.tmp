package simplejdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import simplejdbc.AbstractOracleTestCase;

/**
 * DbResourceUtilのテストクラス
 * @author yasu
 *
 */
public class DbResourceUtilTest extends AbstractOracleTestCase {

	/**
	 * 通常クローズテスト
	 * @throws SQLException SQL例外
	 */
	public void testClose1() throws SQLException {
		dbConnection.setAutoCommit(false);
		DbResourceUtil.close(dbConnection);
		assertTrue(dbConnection.isClosed());
		//クローズ後に再度クローズ
		DbResourceUtil.close(dbConnection);
		//NULLオブジェクトをクローズ
		DbResourceUtil.close((Connection)null);
	}

	/**
	 * 通常クローズテスト
	 * @throws SQLException SQL例外
	 */
	public void testCloseIgnoreException() {
		try {
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			fail();
		}
		DbResourceUtil.closeIgnoreException(dbConnection);
		try {
			assertTrue(dbConnection.isClosed());
		} catch (SQLException e) {
			fail();
		}
		//クローズ後に再度クローズ
		DbResourceUtil.closeIgnoreException(dbConnection);
		//NULLオブジェクトをクローズ
		DbResourceUtil.closeIgnoreException(null);
	}

	/**
	 * closeのテスト2
	 * @throws SQLException SQL例外
	 */
	public void testClose2() throws SQLException {
		PreparedStatement pstmt = this.dbConnection.prepareStatement("SELECT DUMMY FROM DUAL");
		ResultSet result = pstmt.executeQuery();
		DbResourceUtil.close(pstmt, result);
		DbResourceUtil.close(null, null);
		DbResourceUtil.close(dbConnection);
	}

}
