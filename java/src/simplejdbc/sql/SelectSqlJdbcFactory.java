package simplejdbc.sql;

import java.sql.Connection;

/**
 * SQL文指定SELECTサポートクラスファクトリー
 * @author yasu
 *
 */
public class SelectSqlJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final SelectSqlJdbcFactory myObject = new SelectSqlJdbcFactory();

	/**
	 * SQL文指定SELECT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return SQL文指定SELECT用JDBCサポートクラスのインスタンス
	 */
	public static final <E> SelectSqlJdbc<E> create(Connection dbConnection, Class<E> entityClass) {
		return myObject.createJdbcInstance(dbConnection, entityClass);
	}

	/**
	 * SQL文指定SELECT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return SQL文指定SELECT用JDBCサポートクラスのインスタンス
	 */
	private <E> SelectSqlJdbc<E> createJdbcInstance(Connection dbConnection, Class<E> entityClass) {
		//DBMSに依存しない
		return new SelectSqlJdbcImpl<E>(dbConnection, entityClass);
	}
}
