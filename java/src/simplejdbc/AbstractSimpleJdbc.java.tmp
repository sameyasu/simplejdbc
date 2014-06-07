package simplejdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JDBCフレームワーク抽象クラス
 * @author yasu
 *
 */
public abstract class AbstractSimpleJdbc {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(AbstractSimpleJdbc.class);

	/**
	 * DBコネクションオブジェクト
	 */
	protected Connection dbConnection;

	/**
	 * バインド変数配列
	 */
	protected List<Object> bindValues = new ArrayList<Object>(0);

	/**
	 * 値バインドメソッド
	 * @param preparedStatement プリペアーされたステートメント
	 * @param startIndex 開始インデックス
	 * @param bindVar バインドするオブジェクトのリスト
	 * @throws SQLException SQL例外
	 */
	protected void bind(PreparedStatement preparedStatement, int startIndex, List<Object> bindVar) throws SQLException {
		//値BIND
		int paramIndex = startIndex;

		StringBuilder debugLog = new StringBuilder(0);

		for(Object bindValue: bindVar) {

			if(bindValue == null){
				if(log.isDebugEnabled()){
					debugLog.append(" [").append(paramIndex).append("] ").append("NULL ");
				}
				preparedStatement.setString(paramIndex++, null);
			}
			else {
				Class<?> bindClass = bindValue.getClass();

				if(log.isDebugEnabled()){
					debugLog.append(" [").append(paramIndex).append("] (").append(bindClass.getName()).append(") ").append(bindValue);
				}

				if(bindClass.getName().equals("java.lang.String")){
					preparedStatement.setString(paramIndex++, (String) bindValue);
				}
				else if(bindClass.getName().equals("java.sql.Timestamp")){
					preparedStatement.setTimestamp(paramIndex++, (Timestamp) bindValue);
				}
				else if(bindClass.getName().equals("java.util.Date")){
					Date date = (Date) bindValue;
					Timestamp timestamp = new java.sql.Timestamp(date.getTime());
					timestamp.setNanos(0);	//Date型の場合にはナノ秒指定をすると検索できないので
					preparedStatement.setTimestamp(paramIndex++, timestamp);
				}
				else if(bindClass.getName().equals("java.sql.Date")){
					preparedStatement.setDate(paramIndex++, (java.sql.Date) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Integer")){
					preparedStatement.setInt(paramIndex++, (Integer) bindValue);
				}
				else if(bindClass.getName().equals("java.math.BigInteger")){
					preparedStatement.setBigDecimal(paramIndex++, new BigDecimal((BigInteger) bindValue));
				}
				else if(bindClass.getName().equals("java.math.BigDecimal")){
					preparedStatement.setBigDecimal(paramIndex++, (BigDecimal) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Double")){
					preparedStatement.setDouble(paramIndex++, (Double) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Float")){
					preparedStatement.setFloat(paramIndex++, (Float) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Short")){
					preparedStatement.setShort(paramIndex++, (Short) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Character")){
					preparedStatement.setInt(paramIndex++, (int) ((Character)bindValue).charValue());
				}
				else if(bindClass.getName().equals("java.lang.Byte")){
					preparedStatement.setByte(paramIndex++, (Byte) bindValue);
				}
				else if(bindClass.getName().equals("java.lang.Long")){
					preparedStatement.setLong(paramIndex++, (Long) bindValue);
				}
				else {
					preparedStatement.setObject(paramIndex++, bindValue);
				}
			}
		}

		if(log.isDebugEnabled()) {
			if(debugLog.length() != 0){
				log.debug("バインド値 "+debugLog.toString());
			}
		}
	}

	/**
	 * バインド値をクリアするメソッド
	 */
	protected void clearBindValues() {
		this.bindValues.clear();
	}

}
