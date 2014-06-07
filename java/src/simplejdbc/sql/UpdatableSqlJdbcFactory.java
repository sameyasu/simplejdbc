package simplejdbc.sql;

import java.sql.Connection;

/**
 * SQL文指定SELECTサポートクラスファクトリー
 * @author yasu
 *
 */
public class UpdatableSqlJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final UpdatableSqlJdbcFactory myObject = new UpdatableSqlJdbcFactory();

	/**
	 * SQL文指定 更新SQL用JDBCサポートクラスのインスタンス生成メソッド
	 * @param dbConnection データベース接続オブジェクト
	 * @return SQL文指定 更新SQL用JDBCサポートクラスのインスタンス
	 */
	public static final UpdatableSqlJdbc create(Connection dbConnection) {
		return myObject.createJdbcInstance(dbConnection);
	}

	/**
	 * SQL文指定 更新SQL用JDBCサポートクラスのインスタンス生成メソッド
	 * @param dbConnection データベース接続オブジェクト
	 * @return SQL文指定 更新SQL用JDBCサポートクラスのインスタンス
	 */
	private UpdatableSqlJdbc createJdbcInstance(Connection dbConnection) {
		//DBMSに依存しない
		return new UpdatableSqlJdbcImpl(dbConnection);
	}

}
