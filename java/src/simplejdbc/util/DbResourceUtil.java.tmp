package simplejdbc.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * データベースリソースユーティリティクラス
 * @author yasu
 *
 */
public class DbResourceUtil {

	/**
	 * ロガー
	 */
	private static final Log log = LogFactory.getLog(DbResourceUtil.class);

	/**
	 * データベース接続オブジェクトクローズメソッド
	 * <pre>
	 * このメソッドを使用してデータベース接続オブジェクトをクローズする場合はトランザクション管理に気をつけてください。
	 * 自動コミットが設定されていない場合(<code>java.sql.Connection#setAutoCommit</code>メソッドに<code>true</code>をしていている場合)
	 * クローズ処理を行う前にコミットが強制的に行われます。
	 * </pre>
	 * @param dbConnection データベース接続オブジェクト
	 * @throws SQLException SQL例外
	 */
	public static final void close(Connection dbConnection) throws SQLException {
		if(dbConnection != null && dbConnection.isClosed() == false){
			try{
				if(dbConnection.getAutoCommit() == false){
					//コミット強制
					dbConnection.commit();
					dbConnection.setAutoCommit(true);
				}
			}
			finally{
				dbConnection.close();
			}
		}
	}

	/**
	 * 例外無視データベース接続オブジェクトクローズメソッド
	 * <pre>
	 * このメソッドを使用してデータベース接続オブジェクトをクローズする場合はトランザクション管理に気をつけてください。
	 * 自動コミットが設定されていない場合(<code>java.sql.Connection#setAutoCommit</code>メソッドに<code>true</code>をしていている場合)
	 * クローズ処理を行う前にコミットが強制的に行われます。
	 * また、このメソッドを使用してデータベース接続オブジェクトクローズすると内部でSQL例外がスローされても無視されます。
	 * 例外がスローされた場合には<code>commons-logging</code>により、エラーレベルでロギングされます。
	 * </pre>
	 * @param dbConnection データベース接続オブジェクト
	 */
	public static final void closeIgnoreException(Connection dbConnection) {
		if(dbConnection != null){
			try {
				if(dbConnection.isClosed() == false){
					try{
						if(dbConnection.getAutoCommit() == false){
							//コミット強制
							dbConnection.commit();
							dbConnection.setAutoCommit(true);
						}
					}
					finally{
						dbConnection.close();
					}
				}
			} catch (SQLException e) {
				log.error("データベースクローズ失敗",e);
			}
		}
	}

	/**
	 * リソースクローズメソッド
	 * @param stmt ステートメントオブジェクト
	 * @param resultSet 結果セットオブジェクト
	 * @throws SQLException SQL例外
	 */
	public static final void close(Statement stmt, ResultSet resultSet) throws SQLException {
		try{
			if(resultSet != null) {
				resultSet.close();
			}
		}
		finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * リソースクローズメソッド
	 * @param stmt ステートメントオブジェクト
	 * @throws SQLException SQL例外
	 */
	public static final void close(Statement stmt) throws SQLException {
		close(stmt, null);
	}
}
