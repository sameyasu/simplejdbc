package simplejdbc.insert;

import java.sql.SQLException;

/**
 * INSERT文発行インターフェイス
 * @author yasu
 * @param <E> エンティティ
 */
public interface InsertJdbc<E> {

	/**
	 * INSERT発行メソッド
	 * <pre>
	 * <code>IncrementValue</code>アノテーションを指定している場合には、シーケンス値を先に取得してエンティティにセットしてからINSERTが発行されます。
	 * つまり、実行後のエンティティオブジェクトにはINSERTされたシーケンス値を取得することができます。
	 *
	 * 楽観的排他制御が有効となっている場合（デフォルト）は、引数で指定されたエンティティオブジェクトの排他制御カラムの値がセットされていても
	 * 排他制御カラムには最新の排他制御カラムデータ（挿入時の日付文字列）が自動的に上書きされます。
	 *
	 * 楽観的排他制御が無効となっている場合には、引数で指定されたエンティティオブジェクトの排他制御カラムの値が使用されて挿入されます。
	 *
	 * 排他制御カラムがエンティティに存在しない場合は楽観的排他制御は無効となります。
	 * </pre>
	 * @param entity エンティティオブジェクト
	 * @return 挿入件数
	 * @throws SQLException 挿入に失敗した場合
	 */
	public int insert(E entity) throws SQLException ;

	/**
	 * 楽観的排他制御無効セットメソッド
	 * <pre>
	 * 排他制御カラムがエンティティに存在しない場合は楽観的排他制御は無効となります。
	 * </pre>
	 * @param ignoreFlag true:楽観的排他制御しない false:楽観的排他制御する(デフォルト)
	 */
	public void setIgnoreVersion(boolean ignoreFlag);

}
