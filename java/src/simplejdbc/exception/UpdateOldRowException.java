package simplejdbc.exception;

/**
 * simplejdbc用古いバージョンの行更新例外クラス
 * @author yasu
 *
 */
public class UpdateOldRowException extends SimpleJdbcRuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7071397858615596152L;

	/**
	 * コンストラクタ
	 * @param entityClass エンティティクラス
	 * @param message 例外メッセージ
	 */
	public UpdateOldRowException(Class<?> entityClass, String message) {
		super(entityClass, message);
	}

}
