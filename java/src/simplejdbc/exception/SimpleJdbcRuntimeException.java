package simplejdbc.exception;

/**
 * simplejdbc用実行時例外クラス
 * @author yasu
 *
 */
public class SimpleJdbcRuntimeException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6221931985895978746L;

	/**
	 * コンストラクタ
	 */
	protected SimpleJdbcRuntimeException() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param message 例外メッセージ
	 * @param cause 例外オブジェクト
	 */
	protected SimpleJdbcRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ
	 * @param message 例外メッセージ
	 */
	protected SimpleJdbcRuntimeException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param cause 例外オブジェクト
	 */
	protected SimpleJdbcRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * @param entityClass エンティティクラス
	 * @param message 例外メッセージ
	 */
	public SimpleJdbcRuntimeException(Class<?> entityClass, String message) {
		super(formatMessage(entityClass, message));
	}

	/**
	 * コンストラクタ
	 * @param entityClass エンティティクラス
	 * @param message 例外メッセージ
	 * @param throwable 例外オブジェクト
	 */
	public SimpleJdbcRuntimeException(Class<?> entityClass, String message, Throwable throwable) {
		super(formatMessage(entityClass, message), throwable);
	}

	/**
	 * メッセージフォーマット作成メソッド
	 * @param entityClass エンティティクラス
	 * @param message メッセージ文字列
	 * @param throwable 例外
	 * @return フォーマットされたメッセージ
	 */
	private static String formatMessage(Class<?> entityClass, String message) {
		StringBuilder returnMessage = new StringBuilder();
		if(entityClass != null){
			returnMessage.append("<"+entityClass.getName());
			returnMessage.append("> ").append(message);
		}
		else {
			returnMessage.append(message);
		}
		return returnMessage.toString();
	}

}
