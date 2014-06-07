package simplejdbc.select;

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
 * SELECT文サポートOracle実装クラス
 * @author yasu
 * @param <E> エンティティクラス
 */
public class SelectJdbcImplPgsql<E> extends AbstractOrMappingJdbc<E> implements SelectJdbc<E> {

	/**
	 * ロガー
	 */
	private Log log = LogFactory.getLog(SelectJdbcImplPgsql.class);

	/**
	 * ORDER BY句
	 */
	private List<String> orderBys;

	/**
	 * SELECT FOR UPDATE構文
	 */
	private SelectForUpdate selectForUpdate = null;

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
	public SelectJdbcImplPgsql(Connection connection, Class<E> clazz) {
		super(connection, clazz);
	}

	/**
	 * WHERE句 AND条件付加メソッド
	 * @param whereQuery WHERE句のSQL
	 * @param values バインドする値
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
	 * @param whereQuery WHERE句のSQL
	 * @param values バインドする値
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
	 * 結果リスト取得メソッド
	 * @return 結果エンティティのリスト
	 * @throws SQL例外
	 */
	public List<E> selectList() throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		int counter=0;
		for(String columnName : this.columnMap.keySet()){
			counter++;
			sql.append(columnName);
			if(counter < columnMap.size()){
				sql.append(", ");
			}
			else {
				sql.append(" ");
			}
		}

		sql.append("FROM ").append(this.tableName);

		//WHERE句
		if(this.whereSql.length() > 0){
			sql.append(this.whereSql);
		}

		//ORDERBY句
		if(this.orderBys != null){
			sql.append(" ORDER BY ");
			int orderNum=0;
			for(String orderBy : this.orderBys){
				orderNum++;
				sql.append(orderBy);
				if(orderNum < this.orderBys.size()){
					sql.append(", ");
				}
				else {
					sql.append(" ");
				}
			}
		}

		//FOR UPDATE句
		sql.append(this.getForUpdateSqlString());

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;


		List<E> list = new ArrayList<E>();

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());

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
	 * SEELECT結果をエンティティの配列で返却するメソッド
	 * @return 結果エンティティの配列
	 * @throws SQL例外
	 */
	@SuppressWarnings("unchecked")
	public E[] selectArray() throws SQLException {
		List<E> entityList = this.selectList();
		E[] arrayInstance = (E[]) Array.newInstance(this.entity, new int[] {entityList.size()} );
		E[] array = entityList.toArray(arrayInstance);
		return array;
	}

	/**
	 * 1行のみ取得メソッド
	 * @return 結果エンティティ
	 * @throws SQL例外
	 */
	public E selectSingle() throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		int counter=0;
		for(String columnName : this.columnMap.keySet()){
			counter++;
			sql.append(columnName);
			if(counter < columnMap.size()){
				sql.append(", ");
			}
			else {
				sql.append(" ");
			}
		}

		sql.append("FROM ").append(this.tableName);

		//WHERE句
		if(this.whereSql.length() > 0){
			sql.append(this.whereSql);
		}

		//ORDERBY句
		if(this.orderBys != null){
			sql.append(" ORDER BY ");
			int orderNum=0;
			for(String orderBy : this.orderBys){
				orderNum++;
				sql.append(orderBy);
				if(orderNum < this.orderBys.size()){
					sql.append(", ");
				}
				else {
					sql.append(" ");
				}
			}
		}

		//FOR UPDATE句
		sql.append(this.getForUpdateSqlString());

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			result = pstmt.executeQuery();
			if(result.next()){
				//新しいインスタンスにフィールド値を詰めなおす
				E instance = setResultToEntityByFieldName(result);
				return instance;
			}
			else {
				log.debug("該当レコードなし(NULL)");
				return null;
			}

		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}

	}

	/**
	 * プライマリキーから取得メソッド
	 * @param instance インスタンス
	 * @return true:結果が取得できた場合 false:結果が取得できなかった場合
	 * @throws SQL例外
	 */
	public boolean selectByPrimaryKey(E instance) throws SQLException {

		//バインド値クリア
		this.clearBindValues();

		//プライマリキーが無い場合
		if(this.primaryKeys.size() == 0) {
			throw new SimpleJdbcRuntimeException(this.entity, "プライマリキーが存在しないエンティティです。");
		}

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		int counter=0;
		for(String columnName : this.columnMap.keySet()){
			counter++;
			sql.append(columnName);
			if(counter < columnMap.size()){
				sql.append(", ");
			}
			else {
				sql.append(" ");
			}
		}

		sql.append("FROM ").append(this.tableName);

		//WHERE句
		boolean first = true;
		for(JdbcColumn column: this.primaryKeys){
			if(first) {
				sql.append(" WHERE ");
				first = false;
			}
			else {
				sql.append(" AND ");
			}
			sql.append(column.getDbColumnName()).append(" = ? ");
			try {
				this.bindValues.add(column.getValue(instance));
			} catch (IllegalArgumentException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "エンティティフィールド値取得に失敗 フィールド名:"+column.getClassFieldName(),e);
			} catch (IllegalAccessException e) {
				throw new SimpleJdbcRuntimeException(this.entity, "エンティティフィールド値取得に失敗 フィールド名:"+column.getClassFieldName(),e);
			}
		}

		//FOR UPDATE句
		sql.append(this.getForUpdateSqlString());

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			result = pstmt.executeQuery();
			if(result.next()){
				//新しいインスタンスにフィールド値を詰めなおす
				setResultToEntityByFieldName(result, instance);
				return true;
			}
			else {
				//該当レコード取得できなったのでfalse
				return false;
			}

		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}

	}

	/**
	 * 件数取得メソッド
	 * @return 件数
	 * @throws SQL例外
	 */
	public int selectCount() throws SQLException {

		//SQL生成
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ").append(this.tableName);

		//WHERE句
		if(this.whereSql.length() > 0){
			sql.append(this.whereSql);
		}

		log.debug(sql.toString());

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			pstmt = dbConnection.prepareStatement(sql.toString());

			//値バインド
			this.bind(pstmt, 1, this.bindValues);

			result = pstmt.executeQuery();
			result.next();

			return result.getInt(1);
		}
		finally {
			DbResourceUtil.close(pstmt, result);
		}

	}

	/**
	 * ORDER BY句追加メソッド
	 * @param orderbys ORDER BY句
	 */
	public void addOrderBy(String... orderbys) {

		if(this.orderBys == null){
			this.orderBys = new ArrayList<String>(5);
		}

		for(String orderBy : orderbys){
			this.orderBys.add(orderBy);
		}
	}

	/**
	 * SELECT FOR UPDATE構文の設定メソッド
	 * @param forUpdate SELECT FOR UPDATE構文オブジェクト
	 * @see simplejdbc.select.SelectForUpdate SELECT FOR UPDATE構文クラス
	 */
	public void setSelectForUpdate(SelectForUpdate forUpdate){
		this.selectForUpdate = forUpdate;
	}

	/**
	 * SELECT FOR UPDATE構文取得メソッド
	 * @return SELECT FOR UPDATE構文取得
	 */
	protected String getForUpdateSqlString() {
		if(this.selectForUpdate != null){
			if(this.selectForUpdate.getWaitSeconds() == SelectForUpdate.WAITSECONDS_NOWAIT) {
				return " FOR UPDATE NOWAIT";
			}
			else if(this.selectForUpdate.getWaitSeconds() == SelectForUpdate.WAITSECONDS_WAIT_UNLIMITED){
				return " FOR UPDATE";
			}
			else if(this.selectForUpdate.getWaitSeconds() < -1){
				return "";
			}
			else {
				//Pgsql用
				return " FOR UPDATE";
			}
		}
		else {
			return "";
		}
	}

}
