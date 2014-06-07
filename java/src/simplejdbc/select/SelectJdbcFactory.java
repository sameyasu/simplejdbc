package simplejdbc.select;

import java.sql.Connection;

import simplejdbc.DataBaseType;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.util.DataBaseUtil;

/**
 * SELECTサポートクラスファクトリー
 * @author yasu
 *
 */
public class SelectJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final SelectJdbcFactory myObject = new SelectJdbcFactory();

	/**
	 * SELECT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return SELECT用JDBCサポートクラスのインスタンス
	 */
	public static final <E> SelectJdbc<E> create(Connection dbConnection, Class<E> entityClass) {
		return myObject.createJdbcInstance(dbConnection, entityClass);
	}

	/**
	 * SELECT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return SELECT用JDBCサポートクラスのインスタンス
	 */
	private <E> SelectJdbc<E> createJdbcInstance(Connection dbConnection, Class<E> entityClass) {
		//インスタンスのキャッシングは行わず、とりあえずは毎回インスタンス化
		DataBaseType type = DataBaseUtil.getRunningType();
		if(type == DataBaseType.Oracle) {
			return new SelectJdbcImplOracle<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Pgsql) {
			return new SelectJdbcImplPgsql<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Mysql) {
			return new SelectJdbcImplMysql<E>(dbConnection, entityClass);
		}
		else {
			throw new SimpleJdbcRuntimeException(entityClass, "実行中のDBが識別できません。");
		}
	}

}
