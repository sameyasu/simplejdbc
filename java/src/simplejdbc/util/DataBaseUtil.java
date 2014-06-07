package simplejdbc.util;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.DataBaseType;

/**
 * データベースタイプ
 * @author yasu
 */
public class DataBaseUtil {

	/**
	 * ロガー
	 */
	private static final Log log = LogFactory.getLog(DataBaseUtil.class);

	/**
	 * SINGLETON
	 */
	private static final DataBaseUtil myObject = new DataBaseUtil();

	/**
	 * DBタイプ
	 */
	protected DataBaseType supportedType;

	/**
	 * 内部コンストラクタ
	 */
	protected DataBaseUtil() {
		ResourceBundle bundle = ResourceBundle.getBundle("simplejdbc");
		String dbType = bundle.getString("database.type");
		if(dbType.equalsIgnoreCase("oracle")) {
			supportedType = DataBaseType.Oracle;
			log.info("simplejdbcはOracleに最適化したSQLを発行します。");
		}
		else if(dbType.equalsIgnoreCase("pgsql") || dbType.equalsIgnoreCase("postgresql")) {
			supportedType = DataBaseType.Pgsql;
			log.info("simplejdbcはPostgreSQLに最適化したSQLを発行します。");
		}
		else if(dbType.equalsIgnoreCase("mysql")) {
			supportedType = DataBaseType.Mysql;
			log.info("simplejdbcはMySQLに最適化したSQLを発行します。");
		}
		else {
			throw new IllegalStateException("simplejdbcがサポートしていないデータベース型です。");
		}
	}

	/**
	 * 実行中のデータベース型を取得する
	 * @return データベース型
	 */
	public static final DataBaseType getRunningType() {
		return myObject.supportedType;
	}

}
