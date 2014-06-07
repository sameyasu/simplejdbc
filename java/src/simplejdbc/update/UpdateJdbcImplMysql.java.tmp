package simplejdbc.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.AbstractManageVersionJdbc;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.exception.UpdateOldRowException;
import simplejdbc.util.DbResourceUtil;

/**
 * UPDATE文サポートMySQL実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class UpdateJdbcImplMysql<E> extends AbstractManageVersionJdbc<E> implements UpdateJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(UpdateJdbcImplMysql.class);

	/**
	 * WHERE句のSQL文
	 */
	private StringBuilder whereSql = new StringBuilder();

	/**
	 * WHERE句用のバインド変数配列
	 */
	private List<Object> whereBindValues = new ArrayList<Object>(0);

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @param clazz エンティティクラス
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public UpdateJdbcImplMysql(Connection connection, Class<E> clazz) {
		super(connection, clazz);
	}

	/**
	 * WHERE句 AND条件付加メソッド
	 */
	public void andWhere(String whereQuery, Object... values) {
		//値セット
		for(Object value: values){
			this.whereBindValues.add(value);
		}

		if(this.whereSql.length() == 0){
			this.whereSql.append(" WHERE ( ");
		}
		else {
			this.whereSql.append(" AND ( ");
		}
		this.whereSql.append(whereQuery);
		this.whereSql.append(" ) ");
	}

	/**
	 * NULL値でない場合WHERE句付加メソッド
	 */
	public void andWhereIfNotNull(String whereQuery, Object... values) {

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
		this.andWhere(whereQuery, values);
	}

	/**
	 * バージョン番号無視、全カラム更新メソッド
	 */
	public int updateAllColumns(E entityInstance) throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();

		//UPDATE tableName SET
		sql.append("UPDATE ").append(this.tableName).append(" SET ");

		if(this.whereSql.length() == 0){
			//WHERE句がない場合はプライマリキーでUPDATE
			for(JdbcColumn column : this.primaryKeys) {
				try {
					Object value = column.getValue(entityInstance);
					if(value == null){
						throw new SQLException("プライマリキーの値がNULL エンティティ名:"+this.entity.getName()+" フィールド名:"+column.getClassFieldName());
					}
					// columnName = ?
					this.andWhere(column.getDbColumnName()+" = ?", value);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "バインド変数セット失敗 フィールド名:"+column.getClassFieldName());
				}
			}
		}

		this.bindValues = new ArrayList<Object>(0);	//リスト初期化
		int counter=0;
		for(String fieldName : this.fieldMap.keySet()){
			counter++;
			// COLUMN_NAME = ?
			JdbcColumn column = this.fieldMap.get(fieldName);
			sql.append(column.getDbColumnName()).append(" = ? ");
			//フィールドの値をバインド変数配列に追加
			if(this.isIgnoreVersion() == false && column.isVersionColumn()){
				String newVersion = this.getNewVersionMysql();
				String oldValue;
				try {
					oldValue = (String) column.getValue(entityInstance);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "現在バージョン値取得失敗 フィールド名:"+fieldName, e);
				}
				this.bindValues.add(newVersion);
				this.andWhere(this.versionColumn.getDbColumnName() + " IS NULL OR "+this.versionColumn.getDbColumnName() + " = ?", oldValue);
			}
			else{
				try {
					Object value = column.getValue(entityInstance);
					this.bindValues.add(value);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "バインド変数セット失敗 フィールド名:"+fieldName, e);
				}
			}

			if(counter < this.fieldMap.size()){
				sql.append(", ");
			}
			else {
				sql.append(" ");
			}
		}

		//WHERE句
		sql.append(this.whereSql);

		this.log.debug(sql.toString());

		PreparedStatement pstmt = null;
		try {
			pstmt = this.dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);
			this.bind(pstmt, this.bindValues.size()+1, this.whereBindValues);

			int execRowCount = pstmt.executeUpdate();
			if(this.isIgnoreVersion() == false){
				if(execRowCount == 0){
					throw new UpdateOldRowException(this.entity, "更新対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
				}
				else if(execRowCount == 0){
					throw new UpdateOldRowException(this.entity, "更新対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
				}
			}
			return execRowCount;
		}
		finally {
			DbResourceUtil.close(pstmt);
		}
	}

	/**
	 * 特定カラムのみ更新メソッド
	 */
	public int updateColumns(E entityInstance, String... fieldNames) throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();

		//UPDATE tableName SET
		sql.append("UPDATE ").append(this.tableName).append(" SET ");

		this.bindValues = new ArrayList<Object>(fieldNames.length);	//リスト初期化
		int counter=0;
		for(String targetFieldName: fieldNames){
			counter++;
			if(this.fieldMap.containsKey(targetFieldName) == false){
				throw new SimpleJdbcRuntimeException(this.entity, "エンティティに存在しないフィールドを指定しています。 フィールド名："+targetFieldName);
			}

			JdbcColumn column = this.fieldMap.get(targetFieldName);
			// COLUMN_NAME = ?
			sql.append(column.getDbColumnName()).append(" = ? ");
			try {
				//フィールドの値をバインド変数配列に追加
				this.bindValues.add(column.getValue(entityInstance));
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "バインド変数セット失敗 フィールド名:"+targetFieldName, e);
			}

			if(counter < fieldNames.length){
				sql.append(", ");
			}
			else {
				sql.append(" ");
			}
		}

		//プライマリキー指定
		if(this.whereSql.length() == 0){
			for(JdbcColumn column : this.primaryKeys) {
				try {
					Object value = column.getValue(entityInstance);
					if(value == null){
						throw new SQLException("プライマリキーの値がNULL エンティティ名:"+this.entity.getName()+" フィールド名:"+column.getClassFieldName());
					}
					// COLUMN_NAME = ?
					this.andWhere(column.getDbColumnName()+" = ?", value);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "バインド変数セット失敗 フィールド名:"+column.getClassFieldName());
				}
			}
			//WHERE句指定がない場合のみVERSION指定追加
			if(this.isIgnoreVersion() == false){
				String newVersion = this.getNewVersionMysql();
				String oldValue;
				// , COLUMN_NAME = ?
				sql.append(", ").append(this.versionColumn.getDbColumnName()).append(" = ? ");
				try {
					oldValue = (String) this.versionColumn.getValue(entityInstance);
				} catch (IllegalAccessException e) {
					throw new SimpleJdbcRuntimeException(this.entity, "現在バージョン値取得失敗 フィールド名:"+this.versionColumn.getClassFieldName(), e);
				}
				this.bindValues.add(newVersion);
				this.andWhere(this.versionColumn.getDbColumnName() + " IS NULL OR "+this.versionColumn.getDbColumnName() + " = ?", oldValue);
			}
		}

		//WHERE句
		sql.append(this.whereSql);

		this.log.debug(sql.toString());

		PreparedStatement pstmt = null;

		try {
			pstmt = this.dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);
			this.bind(pstmt, this.bindValues.size()+1, this.whereBindValues);

			int execRowCount = pstmt.executeUpdate();

			if(this.isIgnoreVersion() == false){
				if(execRowCount == 0){
					throw new UpdateOldRowException(this.entity, "更新対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
				}
				else if(execRowCount == 0){
					throw new UpdateOldRowException(this.entity, "更新対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
				}
			}

			return execRowCount;
		}
		finally {
			DbResourceUtil.close(pstmt);
		}

	}

}
