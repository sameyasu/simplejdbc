package simplejdbc.delete;

import java.sql.Connection;

/**
 * DELETEサポートクラスファクトリー
 * @author yasu
 *
 */
public class DeleteJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final DeleteJdbcFactory myObject = new DeleteJdbcFactory();

	/**
	 * DELETE用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return DELETE用JDBCサポートクラスのインスタンス
	 */
	public static final <E> DeleteJdbc<E> create(Connection dbConnection, Class<E> entityClass) {
		return myObject.createJdbcInstance(dbConnection, entityClass);
	}

	/**
	 * DELETE用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return DELETE用JDBCサポートクラスのインスタンス
	 */
	private <E> DeleteJdbc<E> createJdbcInstance(Connection dbConnection, Class<E> entityClass) {
		//DBMSに関係ない
		return new DeleteJdbcImpl<E>(dbConnection, entityClass);
	}

}
