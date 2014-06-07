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
 * SELECT文サポートPostgreSQL実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class InsertJdbcImplPgsql<E> extends AbstractManageVersionJdbc<E> implements InsertJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(InsertJdbcImplPgsql.class);

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @param entityClass エンティティクラス
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public InsertJdbcImplPgsql(Connection connection, Class<E> entityClass) {
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
		for(String fieldName : this.fieldMap.keySet()){
			counter++;

			JdbcColumn jdbcColumn = this.fieldMap.get(fieldName);

			//カラム名指定を追加
			sql.append(jdbcColumn.getDbColumnName());

			if(this.isIgnoreVersion() == false && jdbcColumn.isVersionColumn()){
				String newVersion = this.getNewVersionPgsql();
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

				if(value == null) {
					//DATE型にNULLをバインドするとPgSqlのJDBCでエラーとなるため。
					valuesSql.append("NULL ");
				}
				else {
					//バインドパラメータ追加（シーケンス値の場合もSELECTを発行してからそのまま代入するので）
					valuesSql.append("? ");
					this.bindValues.add(value);
				}
			}

			if(counter < columnMap.size()){
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

			return pstmt.executeUpdate();
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

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

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
				counter++;
				if(counter > 1){
					sql.append(", ");
				}
				else {
					sql.append(" ");
				}
				IncrementValue incrementValue = column.getIncrementValue();
				if(incrementValue.sql().equals("")){
					sql.append("nextval('").append(incrementValue.sequence()).append("')");
				}
				else {
					sql.append(incrementValue.sql());
				}
				// SEQ_XXXXX.NEXTVAL AS db_column_name
				sql.append(" AS ").append(column.getDbColumnName());
			}
		}

		//シーケンス値を必要としなかった場合はSELECTしない
		if(counter == 0){
			return;
		}

		//sql.append(" FROM DUAL");	//仮想テーブルから取得

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
				throw new SQLException("シーケンス値取得失敗 エンティティ:"+entity.getName()+" SQL:"+sql.toString());
			}

		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}
	}

}
