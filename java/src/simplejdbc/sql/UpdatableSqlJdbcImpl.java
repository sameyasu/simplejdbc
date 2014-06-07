package simplejdbc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.AbstractSimpleJdbc;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.util.DbResourceUtil;

/**
 * SQL文指定更新文サポートOracle実装クラス
 * @author yasu
 */
public class UpdatableSqlJdbcImpl extends AbstractSimpleJdbc implements UpdatableSqlJdbc {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(UpdatableSqlJdbcImpl.class);

	/**
	/**
	 * SQL文
	 */
	private StringBuilder sqlQuery = new StringBuilder();

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public UpdatableSqlJdbcImpl(Connection connection) {
		this.dbConnection = connection;
	}

	/**
	 * SQL文追加メソッド
	 */
	public void addSql(String sqleQuery, Object... values) {
		//値セット
		for(Object value: values){
			this.bindValues.add(value);
		}

		this.sqlQuery.append(" ").append(sqleQuery).append(" ");
	}

	/**
	 * NULL値でない場合WHERE句付加メソッド
	 */
	public void addSqlIfNotNull(String whereQuery, Object... values) {

		//値セット
		boolean isNull = false;
		for(Object value: values){
			if(value == null){
				isNull = true;
				break;
			}
		}

		//NULL値が含まれればWHERE句は付加しない
		if(isNull){
			return ;
		}

		//WHERE句セット
		this.addSql(whereQuery, values);
	}

	/**
	 * 実行メソッド
	 * @return 実行件数
	 */
	public int executeUpdate() throws SQLException {

		if(this.sqlQuery.length() == 0){
			throw new SimpleJdbcRuntimeException(null, "SQL文が指定されていません。");
		}

		log.debug(sqlQuery.toString());

		PreparedStatement pstmt = null;

		try {
			pstmt = dbConnection.prepareStatement(sqlQuery.toString());
			//値バインド
			this.bind(pstmt, 1, this.bindValues);
			return pstmt.executeUpdate();
		}
		finally {
			DbResourceUtil.close(pstmt);
		}
	}

}
