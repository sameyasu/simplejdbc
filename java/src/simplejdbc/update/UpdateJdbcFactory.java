package simplejdbc.update;

import java.sql.Connection;

import simplejdbc.DataBaseType;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.update.UpdateJdbcImplOracle;
import simplejdbc.util.DataBaseUtil;

/**
 * UPDATEサポートクラスファクトリー
 * @author yasu
 *
 */
public class UpdateJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final UpdateJdbcFactory myObject = new UpdateJdbcFactory();

	/**
	 * UPDATE用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return UPDATE用JDBCサポートクラスのインスタンス
	 */
	public static final <E> UpdateJdbc<E> create(Connection dbConnection, Class<E> entityClass) {
		return myObject.createJdbcInstance(dbConnection, entityClass);
	}

	/**
	 * UPDATE用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return UPDATE用JDBCサポートクラスのインスタンス
	 */
	private <E> UpdateJdbc<E> createJdbcInstance(Connection dbConnection, Class<E> entityClass) {
		DataBaseType type = DataBaseUtil.getRunningType();
		if(type == DataBaseType.Oracle) {
			return new UpdateJdbcImplOracle<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Pgsql) {
			return new UpdateJdbcImplPgsql<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Mysql) {
			return new UpdateJdbcImplMysql<E>(dbConnection, entityClass);
		}
		else {
			throw new SimpleJdbcRuntimeException(entityClass, "実行中のDBが識別できません。");
		}
	}

}
