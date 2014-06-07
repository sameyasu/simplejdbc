package simplejdbc.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.AbstractManageVersionJdbc;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.exception.UpdateOldRowException;
import simplejdbc.util.DbResourceUtil;

/**
 * UPDATE文サポートOracle実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class DeleteJdbcImpl<E> extends AbstractManageVersionJdbc<E> implements DeleteJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(DeleteJdbcImpl.class);

	/**
	 * WHERE句のSQL文
	 */
	private StringBuilder whereSql = new StringBuilder();

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @param clazz エンティティクラス
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public DeleteJdbcImpl(Connection connection, Class<E> clazz) {
		super(connection, clazz);
	}

	/**
	 * WHERE句 AND条件付加メソッド
	 */
	public void andWhere(String whereQuery, Object... values) {
		//値セット
		for(Object value: values){
			this.bindValues.add(value);
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
	 * 行削除メソッド
	 * <pre>
	 * 楽観的排他制御は自動的に無視されます。
	 * </pre>
	 */
	public int delete() throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();

		//DELETE FROM tableName
		sql.append("DELETE FROM ").append(this.tableName);

		//WHERE句
		if(this.whereSql.length() > 0){
			sql.append(this.whereSql);
		}

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
	 * プライマリキーから行削除メソッド
	 */
	public int deleteByPrimaryKey(E instance) throws SQLException {

		//プライマリキーが無い場合
		if(this.primaryKeys.size() == 0) {
			throw new SimpleJdbcRuntimeException(this.entity, "プライマリキーが存在しないエンティティです。");
		}

		//SQL生成
		StringBuilder sql = new StringBuilder();

		//DELETE FROM tableName
		sql.append("DELETE FROM ").append(this.tableName);

		//WHERE句
		for(JdbcColumn column : this.primaryKeys) {
			Object value = null;
			try {
				value = column.getValue(instance);
			} catch (IllegalArgumentException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "バインド変数取得失敗 フィールド名:"+column.getClassFieldName(),e);
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "バインド変数取得失敗 フィールド名:"+column.getClassFieldName(),e);
			}
			if(value == null){
				throw new SimpleJdbcRuntimeException(this.entity, "プライマリキーの値がNULL フィールド名:"+column.getClassFieldName());
			}
			// COLUMN_NAME = ?
			this.andWhere(column.getDbColumnName()+" = ?", value);
		}
		//排他制御をおこなう場合はVERSION指定追加
		if(this.isIgnoreVersion() == false){
			Object value = null;
			try {
				value = this.versionColumn.getValue(instance);
			} catch (IllegalArgumentException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "バインド変数取得失敗 フィールド名:"+this.versionColumn.getClassFieldName(),e);
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "バインド変数取得失敗 フィールド名:"+this.versionColumn.getClassFieldName(),e);
			}
			this.andWhere(this.versionColumn.getDbColumnName() + " IS NULL OR "+this.versionColumn.getDbColumnName() + " = ?", value);
		}

		sql.append(this.whereSql);

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		try {
			pstmt = dbConnection.prepareStatement(sql.toString());
			//値バインド
			this.bind(pstmt, 1, this.bindValues);
			int delRows = pstmt.executeUpdate();

			//楽観的排他制御が有効となっている場合は削除対象が存在しない場合は例外をスローする
			if(this.isIgnoreVersion() == false){
				if(delRows == 0){
					throw new UpdateOldRowException(this.entity, "削除対象が存在しませんでした。古いバージョンを指定してる可能性があります。");
				}
			}

			//削除件数返却
			return delRows;
		}
		finally {
			DbResourceUtil.close(pstmt);
		}

	}

}
