package simplejdbc.select;

/**
 * SELECT FOR UPDATE構文のための行ロッククラス
 * @author yasu
 *
 */
public class SelectForUpdate {

	/**
	 * 待機秒数
	 */
	private int waitSeconds = WAITSECONDS_NOWAIT;

	/**
	 * 無限待機秒数
	 */
	protected static final int WAITSECONDS_WAIT_UNLIMITED = Integer.MAX_VALUE;

	/**
	 * 待機なし（秒数）
	 */
	protected static final int WAITSECONDS_NOWAIT = -1;

	/**
	 * コンストラクタ
	 */
	protected SelectForUpdate() {
		//do nothing.
	}

	/**
	 * 待機秒数オプション追加メソッド
	 * <pre>
	 * このオプションはOracleのみで使用可能です。
	 * NOWAITオプションを指定する場合には<code>WAITSECONDS_NOWAIT</code>フィールドを指定してください。
	 * 永久待機のオプションを指定する場合には<code>WAITSECONDS_WAIT_UNLIMITED</code>フィールドを指定して下さい。
	 * </pre>
	 * @param waitSeconds 待機秒数
	 */
	protected void setWaitSeconds(int waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	/**
	 * 待機秒数取得メソッド
	 * @return 待機秒数
	 */
	protected int getWaitSeconds() {
		return this.waitSeconds;
	}

	/**
	 * 待機秒数オプション指定SELECT FOR UPDATE構文オブジェクト取得メソッド
	 * <pre>
	 * このオプションはOracleのみで使用可能です。
	 * NOWAITオプションを指定する場合には<code>WAITSECONDS_NOWAIT</code>フィールドを指定してください。
	 * 永久待機のオプションを指定する場合には<code>WAITSECONDS_WAIT_UNLIMITED</code>フィールドを指定して下さい。
	 * </pre>
	 * @param waitSeconds 待機秒数
	 * @return SELECT FOR UPDATE構文オブジェクト
	 */
	public static final SelectForUpdate wait(int waitSeconds) {
		SelectForUpdate forUpdate = new SelectForUpdate();
		forUpdate.setWaitSeconds(waitSeconds);
		return forUpdate;
	}

	/**
	 * 待機なしオプション指定SELECT FOR UPDATE構文オブジェクト取得メソッド
	 * @return SELECT FOR UPDATE構文オブジェクト
	 */
	public static final SelectForUpdate noWait() {
		return SelectForUpdate.wait(WAITSECONDS_NOWAIT);
	}

	/**
	 * 永久待機オプション指定SELECT FOR UPDATE構文オブジェクト取得メソッド
	 * @return SELECT FOR UPDATE構文オブジェクト
	 */
	public static final SelectForUpdate waitUnlimited() {
		return SelectForUpdate.wait(WAITSECONDS_WAIT_UNLIMITED);
	}

}
