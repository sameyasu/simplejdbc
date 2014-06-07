package simplejdbc.sql;

import java.sql.SQLException;

/**
 * テーブル変更操作SQL文指定サポート抽象インターフェイス
 * @author yasu
 *
 */
public interface UpdatableSqlJdbc {

	/**
	 * SQL文実行メソッド
	 * @return 実行件数
	 * @throws SQLException SQL例外
	 */
	public int executeUpdate() throws SQLException ;

	/**
	 * SQL文追加メソッド
	 * @param sqlQuery SQL文
	 * @param paramValues パラメータ値
	 */
	public void addSql(String sqlQuery, Object... paramValues);

	/**
	 * パラメータ値NULL以外SQL文追加メソッド
	 * @param sqlQuery SQL文
	 * @param paramValues パラメータ値
	 */
	public void addSqlIfNotNull(String sqlQuery, Object... paramValues);

}
