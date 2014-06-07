package simplejdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import simplejdbc.util.DbResourceUtil;

/**
 * バージョン管理JDBCの抽象クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public abstract class AbstractManageVersionJdbc<E> extends AbstractOrMappingJdbc<E> {

	/**
	 * 楽観的排他制御無視フラグ。
	 * <pre>
	 * デフォルトではfalse (楽観的排他制御有り)
	 * </pre>
	 */
	protected boolean ignoreVersion = false;

	/**
	 * コンストラクタ
	 * @param connection データベース接続オブジェクト
	 * @param entityClass エンティティクラス
	 */
	public AbstractManageVersionJdbc(Connection connection, Class<E> entityClass) {
		super(connection, entityClass);
	}

	/**
	 * 楽観的排他制御無視フラグセットメソッド
	 * @param ignoreVersionFlag true:楽観的排他制御無視 false:楽観的排他制御を使用する
	 */
	public void setIgnoreVersion(boolean ignoreVersionFlag) {
		this.ignoreVersion = ignoreVersionFlag;
	}

	/**
	 * 楽観的排他制御状態取得メソッド
	 * <pre>
	 * 排他制御カラムが存在しない場合には自動的に無効となります。
	 * </pre>
	 * @return true:楽観的排他制御無視 false:楽観的排他制御を使用する
	 */
	public boolean isIgnoreVersion() {
		//排他制御カラムが存在しなければ問答無用で排他制御無効
		if(this.versionColumn == null){
			return true;
		}
		else {
			//排他制御カラムが有れば楽観的排他制御の有効無効を選択可
			return this.ignoreVersion;
		}
	}

	/**
	 * 新バージョン文字列を取得するメソッド
	 * @return 新バージョン文字列
	 * @throws SQLException SQL例外
	 */
	protected String getNewVersionOracle() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt = this.dbConnection.prepareStatement("SELECT TO_CHAR(SYSTIMESTAMP,'YYYYMMDDHH24MISSFF3') AS VERSION FROM DUAL");
			result = pstmt.executeQuery();
			result.next();
			return result.getString("VERSION");
		}
		finally{
			DbResourceUtil.close(pstmt, result);
		}
	}

	/**
	 * 新バージョン文字列を取得するメソッド
	 * @return 新バージョン文字列
	 * @throws SQLException SQL例外
	 */
	protected String getNewVersionPgsql() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt = this.dbConnection.prepareStatement("SELECT TO_CHAR(CURRENT_TIMESTAMP,'YYYYMMDDHH24MISSMS') AS VERSION");
			result = pstmt.executeQuery();
			result.next();
			return result.getString("VERSION");
		}
		finally{
			DbResourceUtil.close(pstmt, result);
		}
	}

	/**
	 * 新バージョン文字列を取得するメソッド
	 * @return 新バージョン文字列
	 * @throws SQLException SQL例外
	 */
	protected String getNewVersionMysql() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt = this.dbConnection.prepareStatement("SELECT DATE_FORMAT(CURRENT_TIMESTAMP, '%Y%m%d%H%i%s%f') AS VERSION");
			result = pstmt.executeQuery();
			result.next();
			return result.getString("VERSION");
		}
		finally{
			DbResourceUtil.close(pstmt, result);
		}
	}
}
