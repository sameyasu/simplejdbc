package simplejdbc.sql;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.AbstractOrMappingJdbc;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.util.DbResourceUtil;

/**
 * SQL文指定SELECT文サポートOracle実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class SelectSqlJdbcImpl<E> extends AbstractOrMappingJdbc<E> implements SelectSqlJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(SelectSqlJdbcImpl.class);

	/**
	 * SQL文
	 */
	private StringBuilder sqlQuery = new StringBuilder();

	/**
	 * コンストラクタ
	 * @param connection DBコネクション
	 * @param clazz エンティティクラス
	 * @throws SecurityException フィールド読込み失敗
	 * @throws NoSuchFieldException フィールド読込み失敗
	 */
	public SelectSqlJdbcImpl(Connection connection, Class<E> clazz) {
		super(connection, clazz);
	}

	/**
	 * SQL文追加メソッド
	 * @param addSqlQuery 追加SQL文
	 * @param values バインド値
	 */
	public void addSql(String addSqlQuery, Object... values) {
		//値セット
		for(Object value: values){
			this.bindValues.add(value);
		}

		this.sqlQuery.append(" ").append(addSqlQuery).append(" ");
	}

	/**
	 * NULL値でない場合WHERE句付加メソッド
	 * @param whereQuery WHERE句
	 * @param values バインド値
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
	 * SEELECT結果をエンティティの配列で返却するメソッド
	 * @return 結果エンティティの配列
	 */
	@SuppressWarnings("unchecked")
	public E[] selectArray() throws SQLException {
		List<E> entityList = this.selectList();
		E[] arrayInstance = (E[]) Array.newInstance(this.entity, new int[] {entityList.size()} );
		E[] array = entityList.toArray(arrayInstance);
		return array;
	}

	/**
	 * 結果リスト取得メソッド
	 * @return 結果エンティティのリスト
	 */
	public List<E> selectList() throws SQLException {

		if(this.sqlQuery.length() == 0){
			throw new SimpleJdbcRuntimeException(this.entity, "SQL文が指定されていません。");
		}

		log.debug(sqlQuery.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;


		List<E> list = new ArrayList<E>();

		try {
			pstmt = dbConnection.prepareStatement(sqlQuery.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			result = pstmt.executeQuery();

			while(result.next()){
				//新しいインスタンスにフィールド値を詰めなおす
				E instance = setResultToEntityByFieldName(result);
				list.add(instance);
			}
		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}

		return list;
	}

	/**
	 * 1行のみ取得メソッド
	 * @return 結果エンティティ
	 */
	public E selectSingle() throws SQLException {

		if(this.sqlQuery.length() == 0){
			throw new SimpleJdbcRuntimeException(this.entity, "SQL文が指定されていません。");
		}

		log.debug(sqlQuery.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sqlQuery.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			result = pstmt.executeQuery();

			if(result.next()) {
				//新しいインスタンスにフィールド値を詰めなおす
				E instance = setResultToEntityByFieldName(result);
				return instance;
			}
			else {
				return null;
			}
		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}

	}

}
