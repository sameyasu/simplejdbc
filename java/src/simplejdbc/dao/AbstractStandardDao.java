package simplejdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import simplejdbc.delete.DeleteJdbc;
import simplejdbc.delete.DeleteJdbcFactory;
import simplejdbc.insert.InsertJdbc;
import simplejdbc.insert.InsertJdbcFactory;
import simplejdbc.select.SelectJdbc;
import simplejdbc.select.SelectJdbcFactory;
import simplejdbc.update.UpdateJdbc;
import simplejdbc.update.UpdateJdbcFactory;

/**
 * 標準データアクセスオブジェクトの抽象クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public abstract class AbstractStandardDao<E> {

	/**
	 * データベース接続オブジェクト
	 */
	protected Connection dbConnection;

	/**
	 * コンストラクタ
	 * @param connection データベース接続オブジェクト
	 */
	public AbstractStandardDao(Connection connection) {
		this.dbConnection = connection;
	}

	/**
	 * プライマリキーから検索メソッド
	 * @return true:該当レコードあり false:該当レコードなし
	 * @throws SQLException SQL例外
	 */
	@SuppressWarnings("unchecked")
	public boolean selectByPrimaryKey(E entity) throws SQLException {
		SelectJdbc<E> selectJdbc = SelectJdbcFactory.create(dbConnection, (Class<E>) entity.getClass());
		return selectJdbc.selectByPrimaryKey(entity);
	}
	
	/**
	 * 挿入メソッド
	 * @param entity エンティティオブジェクト
	 * @throws SQLException SQL例外
	 */
	@SuppressWarnings("unchecked")
	public int insert(E entity) throws SQLException {
		InsertJdbc<E> insertJdbc = InsertJdbcFactory.create(dbConnection, (Class<E>) entity.getClass());
		return insertJdbc.insert(entity);
	}

	/**
	 * 全カラム更新メソッド
	 * @param entity エンティティオブジェクト
	 * @throws SQLException SQL例外
	 */
	@SuppressWarnings("unchecked")
	public int updateAllColumns(E entity) throws SQLException {
		UpdateJdbc<E> updateJdbc = UpdateJdbcFactory.create(dbConnection, (Class<E>) entity.getClass());
		return updateJdbc.updateAllColumns(entity);
	}

	/**
	 * 特定カラム更新メソッド
	 * @param entity エンティティオブジェクト
	 * @throws SQLException SQL例外
	 */
	@SuppressWarnings("unchecked")
	public int updateColumns(E entity, String... fieldNames) throws SQLException {
		UpdateJdbc<E> updateJdbc = UpdateJdbcFactory.create(dbConnection, (Class<E>) entity.getClass());
		return updateJdbc.updateColumns(entity, fieldNames);
	}

	/**
	 * プライマリキーから削除メソッド
	 * @param entity エンティティオブジェクト
	 * @throws SQLException SQL例外
	 */
	@SuppressWarnings("unchecked")
	public int deleteByPrimaryKey(E entity) throws SQLException {
		//少し強引なやり方です。
		DeleteJdbc<E> deleteJdbc = DeleteJdbcFactory.create(dbConnection, (Class<E>) entity.getClass());
		return deleteJdbc.deleteByPrimaryKey(entity);
	}
	
}
