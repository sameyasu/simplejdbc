package simplejdbc.select;

import java.sql.SQLException;
import java.util.List;

/**
 * Select文発行インターフェイス
 * @author yasu
 * @param <E> エンティティ
 */
public interface SelectJdbc<E> {

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
	 * エンティティのプライマリキーを条件としたSELECT文の結果を返します
	 * </pre>
	 * @param entity エンティティ
	 * @return true:該当レコード有り false:該当レコードなし
	 * @throws SQLException SQL例外発生
	 */
	public boolean selectByPrimaryKey(E entity) throws SQLException ;

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
	 * SELECT文発行メソッド
	 * <pre>
	 * 指定した条件により該当レコード数を取得します
	 * </pre>
	 * @return 該当レコード数
	 * @throws SQLException SQL例外発生
	 */
	public int selectCount()throws SQLException ;

	/**
	 * Where句AND条件追加メソッド
	 * @param whereQuery Where句のSQL（WHEREは含めず）
	 * @param values 値(?の順番に)
	 */
	public void andWhere(String whereQuery, Object... values);

	/**
	 * 値がNULL以外Where句AND条件追加メソッド
	 * @param whereQuery Where句のSQL（WHEREは含めず）
	 * @param values 値(?の順番に)
	 */
	public void andWhereIfNotNull(String whereQuery, Object... values);

	/**
	 * ORDER BY 句の追加メソッド
	 * @param orderbys
	 */
	public void addOrderBy(String... orderbys);

	/**
	 * SELECT FOR UPDATE構文の設定メソッド
	 * @param forUpdate SELECT FOR UPDATE構文オブジェクト
	 * @see simplejdbc.select.SelectForUpdate SELECT FOR UPDATE構文クラス
	 */
	public void setSelectForUpdate(SelectForUpdate forUpdate);

}
