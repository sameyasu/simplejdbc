package simplejdbc.update;

import java.sql.SQLException;

import simplejdbc.exception.UpdateOldRowException;

/**
 * UPDATE文発行インターフェイス
 * @author yasu
 * @param <E> エンティティ
 */
public interface UpdateJdbc<E> {

	/**
	 * 特定カラムのみ更新メソッド.
	 * <pre>
	 * 楽観的排他制御が有効となっている場合（デフォルト）は、排他制御カラムを指定して更新します。
	 * 排他制御カラムが新しく更新されている場合は<code>UpdateOldRowException</code>がスローされます。
	 * ただし、排他制御カラムがNULLの場合には排他制御カラムの値が指定されていても関係なく更新します。
	 *
	 * 楽観的排他制御が無効となっている場合には、排他制御カラムも更新フィールド名を指定すればエンティティオブジェクトの値で更新されます。
	 * </pre>
	 * @param entity エンティティオブジェクト
	 * @param fieldNames エンティティのフィールド名
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int updateColumns(E entity, String... fieldNames) throws SQLException, UpdateOldRowException ;

	/**
	 * 全カラム更新メソッド.
	 * <pre>
	 * 楽観的排他制御が有効となっている場合（デフォルト）は、排他制御カラムを指定して更新します。
	 * 排他制御カラムが新しく更新されている場合は<code>UpdateOldRowException</code>がスローされます。
	 * ただし、排他制御カラムがNULLの場合には排他制御カラムの値が指定されていても関係なく更新します。
	 *
	 * 楽観的排他制御が無効となっている場合には、排他制御カラムもエンティティオブジェクトの値で更新されます。
	 * ヒットしない場合でも更新件数は0件で戻ります。（<code>UpdateOldRowException</code>はスローされません。）
	 * </pre>
	 * @param entity エンティティオブジェクト
	 * @return 更新件数
	 * @throws SQLException SQL例外
	 */
	public int updateAllColumns(E entity) throws SQLException, UpdateOldRowException ;

	/**
	 * 楽観的排他制御無効セットメソッド
	 * <pre>
	 * 排他制御カラムがエンティティに存在しない場合は楽観的排他制御は無効となります。
	 * </pre>
	 * @param ignoreFlag true:楽観的排他制御しない false:楽観的排他制御する(デフォルト)
	 */
	public void setIgnoreVersion(boolean ignoreFlag);

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

}
