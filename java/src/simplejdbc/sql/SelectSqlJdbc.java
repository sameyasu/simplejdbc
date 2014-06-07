package simplejdbc.sql;

import java.sql.SQLException;
import java.util.List;

/**
 * SQL文指定のSELECTサポートクラス
 * @author yasu
 *
 */
public interface SelectSqlJdbc<E> {

	/**
	 * SELECT文発行メソッド
	 * <pre>
	 * 該当レコードなしの場合にはNULLが返ります。
	 * </pre>
	 * @return 該当レコードのエンティティオブジェクト
	 * @throws SQLException SQL例外発生
	 */
	public E selectSingle() throws SQLException ;

	/**
	 * SELECT文発行メソッド
	 * <pre>
	 * 結果をエンティティのリストとして戻します
	 * </pre>
	 * @return 該当レコードのエンティティオブジェクトリスト
	 * @throws SQLException SQL例外発生
	 */
	public List<E> selectList() throws SQLException ;

	/**
	 * SELECT文発行メソッド
	 * <pre>
	 * 結果をエンティティの配列として戻します
	 * </pre>
	 * @return 該当レコードのエンティティオブジェクト配列
	 * @throws SQLException SQL例外発生
	 */
	public E[] selectArray() throws SQLException ;

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
