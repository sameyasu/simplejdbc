package simplejdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;
import simplejdbc.annotation.Version;
import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * 抽象JDBCクラス
 * @author yasu
 *
 */
public abstract class AbstractOrMappingJdbc<E> extends AbstractSimpleJdbc {

	/**
	 * ロガー
	 */
	private static final Log log = LogFactory.getLog(AbstractOrMappingJdbc.class);

	/**
	 * エンティティクラス
	 */
	protected Class<E> entity;

	/**
	 * カラム名からカラムオブジェクトを特定するマップ
	 */
	protected Map<String, JdbcColumn> columnMap;

	/**
	 * フィールド名からカラムオブジェクトを特定するマップ
	 */
	protected Map<String, JdbcColumn> fieldMap;

	/**
	 * プライマリキーのリスト
	 */
	protected List<JdbcColumn> primaryKeys;

	/**
	 * バージョン管理カラム
	 */
	protected JdbcColumn versionColumn = null;

	/**
	 * テーブル名
	 */
	protected String tableName;

	/**
	 * コンストラクタ
	 * @param connection データベースコネクション
	 * @param entityClass エンティティクラス
	 * @throws SecurityException セキュリティ例外
	 * @throws NoSuchFieldException エンティティが正しくない場合
	 */
	public AbstractOrMappingJdbc(Connection connection, Class<E> entityClass) {

		this.entity = entityClass;
		this.dbConnection = connection;

		Table table = entityClass.getAnnotation(Table.class);
		if(table == null){
			throw new SimpleJdbcRuntimeException(entity, "Tableアノテーションを付加していないエンティティクラスを指定しています");
		}
		this.tableName = table.name();

		Field[] fields = entityClass.getFields();

		//負荷係数を考慮したサイズ
		int mapCapacity = (int) (fields.length / 0.75);

		this.columnMap = new HashMap<String, JdbcColumn>(mapCapacity);
		this.fieldMap = new HashMap<String, JdbcColumn>(mapCapacity);
		this.primaryKeys = new ArrayList<JdbcColumn>(3);

		for(int i=0; i<fields.length; i++){
			Column column = fields[i].getAnnotation(Column.class);
			if(column != null){
				Field columnField;
				try {
					columnField = entity.getDeclaredField(fields[i].getName());
				} catch (SecurityException e) {
					throw new SimpleJdbcRuntimeException(entity, "エンティティフィールド取得失敗 フィールド名:"+fields[i].getName());
				} catch (NoSuchFieldException e) {
					throw new SimpleJdbcRuntimeException(entity, "エンティティフィールド取得失敗 フィールド名:"+fields[i].getName());
				}
				PrimaryKey key = fields[i].getAnnotation(PrimaryKey.class);
				boolean isPrimary = (key != null);
				JdbcColumn jdbcColumn = new JdbcColumn(column.name(), isPrimary, columnField);
				//プライマリキーリストに追加
				if(isPrimary){
					this.primaryKeys.add(jdbcColumn);
				}
				IncrementValue incrementValue = fields[i].getAnnotation(IncrementValue.class);
				if(incrementValue != null){
					if(incrementValue.sequence() == null || incrementValue.sequence().equals("")){
						throw new SimpleJdbcRuntimeException(entity, "IncrementValueアノテーションのsequence属性が指定されていません。 フィールド名:"+fields[i].getName());
					}
					//インクリメント値属性をセット
					jdbcColumn.setIncrementValue(incrementValue);
				}
				Version version = fields[i].getAnnotation(Version.class);
				if(version != null){
					if(this.versionColumn != null){
						throw new SimpleJdbcRuntimeException(entity, "Versionアノテーションを付加するフィールドは1つしか指定できません。");
					}
					if(columnField.getType().getName().equals("java.lang.String") == false){
						throw new SimpleJdbcRuntimeException(entity, "Versionアノテーションを付加するフィールドはjava.lang.Stringのフィールドでなければいけません。フィールド名:"+fields[i].getName());
					}
					jdbcColumn.setVersionColumn(true);
					this.versionColumn = jdbcColumn;
					if(log.isDebugEnabled()){
						log.debug("楽観的排他制御有効 エンティティ名:"+this.entity.getName()+" 排他制御カラム:"+this.versionColumn.getDbColumnName()+" 排他制御フィールド名:"+this.versionColumn.getClassFieldName());
					}
				}
				//カラムマップに追加
				this.columnMap.put(column.name().toUpperCase(), jdbcColumn);
				this.fieldMap.put(fields[i].getName(), jdbcColumn);
			}
		}

		if(this.columnMap.size() == 0){
			throw new SimpleJdbcRuntimeException(entity, "Columnアノテーションを1つも付加していないエンティティクラスを指定しています。");
		}

		if(log.isDebugEnabled()){
			if(this.versionColumn == null){
				log.debug("楽観的排他制御無効 エンティティ名:"+this.entity.getName());
			}
		}
	}

	/**
	 * インスタンス生成メソッド
	 * @return 空のインスタンス
	 * @throws SQLException SQL例外発生
	 */
	protected E newInstance() throws SQLException {
		E instance = null;
		try {
			Constructor<E> constructor = entity.getConstructor();
			instance = constructor.newInstance();
		} catch (Exception e){
			throw new SimpleJdbcRuntimeException(this.entity,"エンティティインスタンス化失敗",e);
		}
		return instance;
	}

	/**
	 * ResultSetからエンティティにつめなおすメソッド
	 * @return エンティティ
	 * @throws SQLException SQL例外発生
	 */
	protected E setResultToEntityByFieldName(ResultSet result) throws SQLException {
		E instance = newInstance();
		setResultToEntityByFieldName(result, instance);
		return instance;
	}

	/**
	 * ResultSetからエンティティにつめなおすメソッド
	 * @param result 結果セット
	 * @param instance エンティティのインスタンス
	 * @throws SQLException SQL例外発生
	 */
	protected void setResultToEntityByFieldName(ResultSet result, E instance) throws SQLException {
		for(String fieldName : fieldMap.keySet()){
			JdbcColumn jdbcColumn = fieldMap.get(fieldName);
			setResultToEntity(result, jdbcColumn, instance);
		}
	}

	/**
	 * ResultSetからエンティティにつめなおすメソッド
	 * <pre>
	 * このメソッドでは結果セットのメタデータに存在するカラム名からエンティティのフィールドを特定します。
	 * そのため、SQL文にエンティティのカラム名と対応していない結果が含まれていた場合には警告がロギングされて、継続します。
	 * </pre>
	 * @param result 結果セット
	 * @param resultMeta 結果セットのメタデータ
	 * @param instance エンティティのインスタンス
	 * @throws SQLException SQL例外発生
	 */
	protected void setResultToEntityByColumnName(ResultSet result, ResultSetMetaData resultMeta, E instance) throws SQLException {
		//結果セットにあるカラムから検索
		for(int i=1; i<=resultMeta.getColumnCount(); i++){
			//カラム名を取得
			String columnName = resultMeta.getColumnName(i).toUpperCase();
			if(this.columnMap.containsKey(columnName)){
				JdbcColumn jdbcColumn = this.columnMap.get(columnName);
				setResultToEntity(result, jdbcColumn, instance);
			}
			else{
				log.warn("結果セットにエンティティに詰める必要のないカラムが含まれています。エンティティ名:"+entity.getName()+" テーブル名:"+tableName+" カラム名:"+columnName);
			}
		}
	}

	/**
	 * 結果セットから対応するエンティティフィールドにセットするメソッド
	 * @param result 結果セット
	 * @param jdbcColumn JDBCカラム定義
	 * @param entityInstance エンティティオブジェクト
	 * @throws SQLException 結果取得失敗
	 */
	protected void setResultToEntity(ResultSet result, JdbcColumn jdbcColumn, E entityInstance) throws SQLException {

		Field field = jdbcColumn.getClassField();
		Class<?> type = jdbcColumn.getFieldType();
		try {
			if(type.getName().equals("java.lang.String")) {
				field.set(entityInstance, result.getString(jdbcColumn.getDbColumnName()));
			}
			else if(type.isPrimitive()) {
				if(type.getName().equals("int")) {
					field.setInt(entityInstance, result.getInt(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("short")) {
					field.setShort(entityInstance, result.getShort(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("boolean")) {
					field.setBoolean(entityInstance, result.getBoolean(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("double")) {
					field.setDouble(entityInstance, result.getDouble(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("float")) {
					field.setFloat(entityInstance, result.getFloat(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("long")) {
					field.setLong(entityInstance, result.getLong(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("byte")) {
					field.setByte(entityInstance, result.getByte(jdbcColumn.getDbColumnName()));
				}
				else if(type.getName().equals("char")) {
					field.setChar(entityInstance, (char) result.getInt(jdbcColumn.getDbColumnName()));
				}
			}
			else if(type.getName().equals("java.lang.Integer")) {
				field.set(entityInstance, result.getInt(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Short")) {
				field.set(entityInstance, result.getShort(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Boolean")) {
				field.set(entityInstance, result.getBoolean(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Double")) {
				field.set(entityInstance, result.getDouble(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Float")) {
				field.set(entityInstance, result.getFloat(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Long")) {
				field.set(entityInstance, result.getLong(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Byte")) {
				field.set(entityInstance, result.getByte(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.lang.Character")) {
				field.set(entityInstance, Character.valueOf((char) result.getInt(jdbcColumn.getDbColumnName())));
			}
			else if(type.getName().equals("java.sql.Timestamp")) {
				field.set(entityInstance, result.getTimestamp(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.sql.Date")) {
				field.set(entityInstance, result.getDate(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.util.Date")) {
				field.set(entityInstance, result.getTimestamp(jdbcColumn.getDbColumnName()));
			}
			else if(type.getName().equals("java.math.BigInteger")) {
				BigDecimal bigDecimal = result.getBigDecimal(jdbcColumn.getDbColumnName());
				field.set(entityInstance, bigDecimal != null ? bigDecimal.toBigInteger() : null);
			}
			else if(type.getName().equals("java.math.BigDecimal")) {
				field.set(entityInstance, result.getBigDecimal(jdbcColumn.getDbColumnName()));
			}
			else {
				if(log.isDebugEnabled()){
					log.debug(jdbcColumn.getDbColumnName() + " => " + type.getName());
				}
				field.set(entityInstance, result.getObject(jdbcColumn.getDbColumnName()));
			}
		} catch (IllegalArgumentException e) {
			throw new SimpleJdbcRuntimeException(this.entity, "エンティティフィールドへのSELECT結果セット失敗 フィールド名:"+jdbcColumn.getClassFieldName(),e);
		} catch (IllegalAccessException e) {
			throw new SimpleJdbcRuntimeException(this.entity, "エンティティフィールドへのSELECT結果セット失敗 フィールド名:"+jdbcColumn.getClassFieldName(),e);
		}
	}

	/**
	 * JDBCのカラムクラス
	 * @author yasu
	 *
	 */
	public static class JdbcColumn {

		/**
		 * DBカラム名
		 */
		private String dbColumnName;

		/**
		 * プライマリーキー
		 */
		private boolean primaryKey;

		/**
		 * バージョン管理用カラム
		 */
		private boolean versionColumn;

		/**
		 * エンティティフィールドオブジェクト
		 */
		private Field classField;

		/**
		 * インクリメントが必要な値属性
		 */
		private IncrementValue incrementValue = null;

		/**
		 * コンストラクタ
		 * @param dbColumnName DBカラム名
		 * @param primaryKey プライマリキー
		 * @param classField フィールドオブジェクト
		 */
		private JdbcColumn(String dbColumnName, boolean primaryKey, Field classField) {
			this.dbColumnName = dbColumnName;
			this.classField = classField;
			this.primaryKey = primaryKey;
			this.versionColumn = false;
		}

		/**
		 * DBカラム名取得メソッド
		 * @return DBカラム名
		 */
		public String getDbColumnName() {
			return dbColumnName;
		}

		/**
		 * エンティティクラスのフィールド取得メソッド
		 * @return フィールドオブジェクト
		 */
		public Field getClassField() {
			return classField;
		}

		/**
		 * フィールド名取得メソッド
		 * @return フィールド名
		 */
		public String getClassFieldName() {
			return this.classField.getName();
		}

		/**
		 * クラスフィールド名取得メソッド
		 * @return フィールドのタイプ(クラス)
		 */
		public Class<?> getFieldType() {
			return this.classField.getType();
		}

		/**
		 * フィールドの値取得メソッド
		 * @param instance エンティティのインスタンス
		 * @return フィールドの値
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 */
		public Object getValue(Object instance) throws IllegalArgumentException, IllegalAccessException {
			return this.classField.get(instance);
		}

		/**
		 * プライマリキーかどうか判断するメソッド
		 * @return true:プライマリキー false:プライマリキー
		 */
		public boolean isPrimaryKey() {
			return primaryKey;
		}

		/**
		 * インクリメントする値かどうか判断するメソッド
		 * @return true:インクリメントが必要 false:インクリメントは必要ない
		 */
		public boolean isIncrementValue() {
			return (this.incrementValue != null);
		}

		/**
		 * インクリメント値属性オブジェクトを取得するメソッド
		 * @return インクリメント値属性オブジェクト
		 */
		public IncrementValue getIncrementValue() {
			return incrementValue;
		}

		/**
		 * インクリメント値属性オブジェクトセットメソッド
		 * @param incrementValue インクリメント値属性オブジェクト
		 */
		private void setIncrementValue(IncrementValue incrementValue) {
			this.incrementValue = incrementValue;
		}

		/**
		 * バージョン管理カラムかどうか判断するメソッド
		 * @return true:バージョン管理カラム false:バージョン管理カラムではない
		 */
		public boolean isVersionColumn() {
			return versionColumn;
		}

		/**
		 * バージョン管理カラムセットメソッド
		 * @param versionColumn バージョン管理カラムかどうかのフラグ
		 */
		private void setVersionColumn(boolean versionColumn) {
			this.versionColumn = versionColumn;
		}

	}
}
