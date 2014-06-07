package simplejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * mysql用抽象テストケースクラス
 * @author yasu
 *
 */
public abstract class AbstractMysqlTestCase extends TestCase {

	/**
	 * データベースコネクション
	 */
	protected Connection dbConnection;

	/**
	 * データベースコネクション2
	 */
	protected Connection dbConnection2 = null;

	/**
	 * テスト実行前処理メソッド
	 */
	@Override
	protected void setUp() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("test");
		Class.forName("com.mysql.jdbc.Driver");
		this.dbConnection = DriverManager.getConnection(
				bundle.getString("mysql.jdbc.url"));
		this.dbConnection.setAutoCommit(false);
	}

	/**
	 * テスト実行後処理メソッド
	 */
	@Override
	protected void tearDown() throws Exception {
		//コネクションクローズ
		if(this.dbConnection != null && this.dbConnection.isClosed() == false){
			this.dbConnection.rollback();
			this.dbConnection.close();
		}
		//コネクションクローズ（2つ目)
		if(this.dbConnection2 != null && this.dbConnection2.isClosed() == false){
			this.dbConnection2.rollback();
			this.dbConnection2.close();
		}
	}

	/**
	 * データベースコネクションの2つ目をインスタンス化するメソッド
	 * <pre
	 * データベースコネクションを2つ使用してテストする場合のみ明示的に実行すること。
	 * </pre>
	 */
	protected void setUpDoubleDbConnection() throws Exception {
		//複数回実行されないために
		if(this.dbConnection2 == null){
			ResourceBundle bundle = ResourceBundle.getBundle("test");
			Class.forName("com.mysql.jdbc.Driver");
			this.dbConnection2 = DriverManager.getConnection(
					bundle.getString("mysql.jdbc.url"));
			this.dbConnection2.setAutoCommit(false);
		}
	}

}
