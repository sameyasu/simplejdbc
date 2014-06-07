package simplejdbc.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.AbstractManageVersionJdbc;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.util.DbResourceUtil;

/**
 * SELECT文サポートMySQL実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class InsertJdbcImplMysql<E> extends AbstractManageVersionJdbc<E> implements InsertJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(InsertJdbcImplMysql.class);

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @param entityClass エンティティクラス
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public InsertJdbcImplMysql(Connection connection, Class<E> entityClass) {
		super(connection, entityClass);
	}

	/**
	 * 全カラム更新メソッド
	 * @param entityInstance エンティティのインスタンス
	 * @return 更新件数
	 * @throws IllegalAccessException
	 */
	public int insert(E entityInstance) throws SQLException {

		//エンティティにシーケンス値をセットする
		selectEntitySequences(entityInstance);

		//SQL生成
		StringBuilder sql = new StringBuilder();

		//INSERT INTO tableName (
		sql.append("INSERT INTO ").append(this.tableName).append(" ( ");

		//VALUES句
		StringBuilder valuesSql = new StringBuilder();

		this.bindValues = new ArrayList<Object>(this.fieldMap.size());	//リスト初期化
		int counter=0;
		int maxSize = columnMap.size();

		boolean hasAutoIncrement = false;

		for(String fieldName : this.fieldMap.keySet()){
			counter++;

			JdbcColumn jdbcColumn = this.fieldMap.get(fieldName);

			if(this.isIgnoreVersion() == false && jdbcColumn.isVersionColumn()){
				//カラム名指定を追加
				sql.append(jdbcColumn.getDbColumnName());

				String newVersion = this.getNewVersionMysql();
				try {
					jdbcColumn.getClassField().set(entityInstance, newVersion);
				} catch (IllegalArgumentException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "バージョンカラムフィールド値セット失敗 フィールド名:"+fieldName, e);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "バージョンカラムフィールド値セット失敗 フィールド名:"+fieldName, e);
				}
				//バインドパラメータ追加（シーケンス値の場合もSELECTを発行してからそのまま代入するので）
				valuesSql.append("? ");
				this.bindValues.add(newVersion);
			}
			//インクリメントする値以外を挿入
			else {
				Object value = null;
				try {
					//フィールドの値をバインド変数配列に追加
					value = jdbcColumn.getValue(entityInstance);
				} catch (IllegalArgumentException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
				}

				if(value == null && jdbcColumn.isIncrementValue() && jdbcColumn.getIncrementValue().sequence().equals("AUTO_INCREMENT")) {
					//自動インクリメントされるので値をセットしない。
					hasAutoIncrement = true;
					continue;
				}
				else {
					//カラム名指定を追加
					sql.append(jdbcColumn.getDbColumnName());
					//バインドパラメータ追加（シーケンス値の場合もSELECTを発行してからそのまま代入するので）
					valuesSql.append("? ");
					this.bindValues.add(value);
				}

			}

			if(counter < maxSize){
				valuesSql.append(", ");
				sql.append(", ");
			}
			else {
				valuesSql.append(" ");
				sql.append(" ");
			}

		}

		sql.append(" ) ").append(" VALUES ( ");

		sql.append(valuesSql.toString()).append(" ) ");

		log.debug(sql.toString());

		PreparedStatement pstmt = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			int rows = pstmt.executeUpdate();

			if(rows > 0 && hasAutoIncrement == true) {
				//エンティティにシーケンス値をセットする
				selectEntityLastInsertID(entityInstance);
			}

			return rows;
		}
		finally {
			DbResourceUtil.close(pstmt);
		}
	}

	/**
	 * エンティティにシーケンス値を設定するメソッド
	 * @param entityInstance エンティティオブジェクト
	 * @throws SQLException SQL例外発生時
	 */
	protected void selectEntitySequences(E entityInstance) throws SQLException {

		String columnName = null;
		IncrementValue incrementValue = null;

		for(String fieldName : this.fieldMap.keySet()){
			JdbcColumn column = this.fieldMap.get(fieldName);
			Object value = null;
			try {
				//フィールドの値をバインド変数配列に追加
				value = column.getValue(entityInstance);
			} catch (IllegalArgumentException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
			}
			//値がセットされていないカラムのみ
			if(column.isIncrementValue() && value == null){
				columnName = column.getDbColumnName();
				incrementValue = column.getIncrementValue();
				//属性がいずれかNULLの場合にはシーケンス値取得しない
				if(incrementValue.sequence().length() == 0 || incrementValue.sql().length() == 0 || incrementValue.table().length() == 0) {
					incrementValue = null;
				}
				break;
			}
		}

		//シーケンス値を必要としなかった場合はSELECTしない
		if(incrementValue == null){
			return;
		}

		//SQL生成
		StringBuilder sql = new StringBuilder(50);
		sql.append("INSERT INTO ").append(incrementValue.table()).append(" ( ").append(incrementValue.sequence()).append(" ) VALUES ( NULL )");
		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());
			int rows = pstmt.executeUpdate();
			if(rows == 1){
				DbResourceUtil.close(pstmt);

				StringBuilder selectSql = new StringBuilder(50);
				selectSql.append("SELECT ").append(incrementValue.sql()).append(" AS ").append(columnName);
				log.debug(selectSql.toString());

				pstmt = dbConnection.prepareStatement(selectSql.toString());
				result = pstmt.executeQuery();

				if(result.next()){
					//インスタンスにフィールド値を詰める
					setResultToEntityByColumnName(result, result.getMetaData(), entityInstance);
				}
				else {
					throw new SQLException("シーケンス値取得失敗 エンティティ:"+entity.getName()+" SQL:"+selectSql.toString());
				}
			}
			else {
				throw new SQLException("シーケンス値作成失敗 エンティティ:"+entity.getName()+" SQL:"+sql.toString());
			}
		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}
	}

	/**
	 * エンティティにシーケンス値を設定するメソッド
	 * @param entityInstance エンティティオブジェクト
	 * @throws SQLException SQL例外発生時
	 */
	protected void selectEntityLastInsertID(E entityInstance) throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LAST_INSERT_ID()");

		int counter=0;
		for(String fieldName : this.fieldMap.keySet()){
			JdbcColumn column = this.fieldMap.get(fieldName);
			Object value = null;
			try {
				//フィールドの値をバインド変数配列に追加
				value = column.getValue(entityInstance);
			} catch (IllegalArgumentException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "フィールド値取得失敗 フィールド名:"+fieldName, e);
			}
			//値がセットされていないカラムのみ
			if(column.isIncrementValue() && value == null){
				IncrementValue incrementValue = column.getIncrementValue();
				if(incrementValue.sql().equals("")){
					counter++;
					sql.append(" AS ").append(column.getDbColumnName());
				}
				break;
			}
		}

		//シーケンス値を必要としなかった場合はSELECTしない
		if(counter == 0){
			return;
		}

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());
			result = pstmt.executeQuery();
			if(result.next()){
				//インスタンスにフィールド値を詰めなおす
				setResultToEntityByColumnName(result, result.getMetaData(), entityInstance);
			}
			else {
				throw new SQLException("AUTO_INCREMENT値取得失敗 エンティティ:"+entity.getName()+" SQL:"+sql.toString());
			}

		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}
	}
}
