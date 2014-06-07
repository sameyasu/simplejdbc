package simplejdbc.insert;

import java.sql.Connection;

import simplejdbc.DataBaseType;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.insert.InsertJdbcImplMysql;
import simplejdbc.insert.InsertJdbcImplOracle;
import simplejdbc.insert.InsertJdbcImplPgsql;
import simplejdbc.util.DataBaseUtil;

/**
 * INSERT用JDBCサポートクラスのファクトリー
 * @author yasu
 *
 */
public class InsertJdbcFactory {

	/**
	 * SINGLETON
	 */
	private static final InsertJdbcFactory myObject = new InsertJdbcFactory();

	/**
	 * INSERT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return INSERT用JDBCサポートクラスのインスタンス
	 */
	public static final <E> InsertJdbc<E> create(Connection dbConnection, Class<E> entityClass) {
		return myObject.createJdbcInstance(dbConnection, entityClass);
	}

	/**
	 * INSERT用JDBCサポートクラスのインスタンス生成メソッド
	 * @param <E> エンティティ
	 * @param dbConnection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 * @return INSERT用JDBCサポートクラスのインスタンス
	 */
	private <E> InsertJdbc<E> createJdbcInstance(Connection dbConnection, Class<E> entityClass) {
		//インスタンスのキャッシングは行わず、とりあえずは毎回インスタンス化
		DataBaseType type = DataBaseUtil.getRunningType();
		if(type == DataBaseType.Oracle) {
			return new InsertJdbcImplOracle<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Pgsql) {
			return new InsertJdbcImplPgsql<E>(dbConnection, entityClass);
		}
		else if(type == DataBaseType.Mysql) {
			return new InsertJdbcImplMysql<E>(dbConnection, entityClass);
		}
		else {
			throw new SimpleJdbcRuntimeException(entityClass, "実行中のDBが識別できません。");
		}
	}

}
