package simplejdbc.delete;

import java.sql.SQLException;

/**
 * DELETE文発行インターフェイス
 * @author yasu
 * @param <E> エンティティ
 */
public interface DeleteJdbc<E> {

	/**
	 * 全レコード削除メソッド
	 * <pre>
	 * 楽観的排他制御は自動的に無視されます。
	 * </pre>
	 * @return 削除件数
	 * @throws SQLException SQL例外
	 */
	public int delete() throws SQLException ;

	/**
	 * プライマリキーでレコード削除メソッド
	 * <pre>
	 * セットされているWHERE句は無視されます。
	 * 楽観的排他制御が有効となっている場合（デフォルト）は、WHERE句に排他制御カラムを指定して削除します。
	 * 排他制御カラムが新しく更新されてヒットしない場合は<code>UpdateOldRowException</code>がスローされます。
	 * ただし、排他制御カラムがNULLの場合には排他制御カラムの値が指定されていても削除します。
	 *
	 * 楽観的排他制御が無効となっている場合には、排他制御カラムを無視してプライマリキーのみの条件で削除します。
	 * ヒットしない場合でも削除件数は0件で戻ります。（<code>UpdateOldRowException</code>はスローされません。）
	 * </pre>
	 * @return 削除件数
	 * @throws SQLException SQL例外
	 */
	public int deleteByPrimaryKey(E instance) throws SQLException ;

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
