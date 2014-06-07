package simplejdbc.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import simplejdbc.exception.SimpleJdbcRuntimeException;

/**
 * Javaのソースを自動生成するクラス
 * @author yasu
 *
 */
public class JavaSourceMaker {

	/**
	 * SIGLETON
	 */
	private static final JavaSourceMaker myObject = new JavaSourceMaker();

	/**
	 * 作成者
	 */
	private String author ;

	/**
	 * 楽観的排他制御カラムのアノテーション付加フラグ
	 */
	private boolean autoVersionAddFlag ;

	/**
	 * コンストラクタ
	 */
	private JavaSourceMaker() {
		ResourceBundle bundle = ResourceBundle.getBundle("tools");
		this.author = bundle.getString("auther.prefix") + " " + bundle.getString("auther.version");
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.author = this.author + " (" + format.format(new Date()) + ")";
		this.autoVersionAddFlag = bundle.getString("column.version.auto").equals("true");
	}


	/**
	 * エンティティクラスのJavaソース自動生成メソッド
	 * @param properties
	 * @param entityClass
	 */
	public static final void makeEntityFile(ToolsProperties properties, EntityClass entityClass) {

		File entityPackageDirectory = new File(properties.getSourceFilePrefix()+"/"+entityClass.getPackageName().replaceAll("\\.", "/"));

		System.out.println("Entityパッケージディレクトリ => "+entityPackageDirectory.getPath());

		//エンティティパッケージディレクトリがなければ作成
		if(entityPackageDirectory.exists() == false){
			entityPackageDirectory.mkdirs();
		}

		try {
			String javaSource = makeEntityJavaSource(entityClass);
			File out = new File(entityPackageDirectory.getPath()+"/"+entityClass.getClassName()+".java");
			System.out.println("<Entity> "+out.getPath());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), properties.getSourceEncoding()));

			writer.write(javaSource);
			writer.flush();
			writer.close();
		} catch (UnsupportedEncodingException e) {
			throw new SimpleJdbcRuntimeException(null, "エンティティクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		} catch (FileNotFoundException e) {
			throw new SimpleJdbcRuntimeException(null, "エンティティクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		} catch (IOException e) {
			throw new SimpleJdbcRuntimeException(null, "エンティティクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		}

	}

	/**
	 * エンティティクラスのJavaソース自動生成メソッド
	 * @param properties
	 * @param entityClass
	 */
	public static final void makeDaoFile(ToolsProperties properties, EntityClass entityClass) {
		makeDaoFile(properties, entityClass, makeDaoInstance(properties, entityClass));
	}

	/**
	 * エンティティクラスのJavaソース自動生成メソッド
	 * @param properties
	 * @param entityClass
	 */
	public static final void makeDaoFile(ToolsProperties properties, EntityClass entityClass, DaoClass daoClass) {

		File daoPackageDirectory = new File(properties.getSourceFilePrefix()+"/"+daoClass.getPackageName().replaceAll("\\.", "/"));

		System.out.println("Daoパッケージディレクトリ => "+daoPackageDirectory.getPath());

		//Daoパッケージディレクトリがなければ作成
		if(daoPackageDirectory.exists() == false){
			daoPackageDirectory.mkdirs();
		}

		try {
			String javaSource = makeDaoJavaSource(entityClass, daoClass);
			File out = new File(daoPackageDirectory.getPath()+"/"+daoClass.getClassName()+".java");
			System.out.println("<Dao> "+out.getPath());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), properties.getSourceEncoding()));

			writer.write(javaSource);
			writer.flush();
			writer.close();
		} catch (UnsupportedEncodingException e) {
			throw new SimpleJdbcRuntimeException(null, "Daoクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		} catch (FileNotFoundException e) {
			throw new SimpleJdbcRuntimeException(null, "Daoクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		} catch (IOException e) {
			throw new SimpleJdbcRuntimeException(null, "Daoクラスファイル生成に失敗しました。テーブル名:"+entityClass.getTableName(),e);
		}

	}

	/**
	 * Daoクラスのインスタンス生成メソッド
	 * @param properties
	 * @param entityClass
	 * @return
	 */
	public static final DaoClass makeDaoInstance(ToolsProperties properties, EntityClass entityClass) {
		DaoClass dao = new DaoClass();
		dao.setPackageName(properties.getDaoPackage());
		dao.setClassName(entityClass.getClassName()+properties.getDaoSuffix());
		return dao;
	}

	private static String makeEntityJavaSource(EntityClass entityClass) {

		String crlf = "\r\n";

		Map<String, String> importPackageMap = new HashMap<String, String>(0);
		StringBuilder importSource = new StringBuilder();
		StringBuilder fieldSource = new StringBuilder();

		boolean hasPrimaryKey = false;
		boolean hasVersion = false;

		for(EntityColumn column : entityClass.getColumns()) {
			if(column.getColumnClass().getPackage() != null && column.getColumnClass().getPackage().getName().equals("java.lang") == false) {
				//import文の重複防止
				if(importPackageMap.containsKey(column.getColumnClass().getName()) == false) {
					importSource.append("import ").append(column.getColumnClass().getName()).append(";").append(crlf);
					importPackageMap.put(column.getColumnClass().getName(), column.getColumnClass().getName());
				}
			}
			if(column.isPkey()){
				fieldSource.append("\t@PrimaryKey").append(crlf);
				hasPrimaryKey = true;
			}
			if(myObject.autoVersionAddFlag == true && column.getColumnName().equalsIgnoreCase("version")) {
				if(column.getColumnClass().getName().equals("java.lang.String")){
					fieldSource.append("\t@Version").append(crlf);
					hasVersion = true;
				}
			}
			fieldSource.append("\t@Column(name=\"" + column.getColumnName() + "\")").append(crlf);
			fieldSource.append("\tpublic "+ column.getColumnClass().getSimpleName() + " "+ column.getFieldName() +";").append(crlf);
			fieldSource.append(crlf);
		}

		StringBuilder source = new StringBuilder();

		source.append("package " + entityClass.getPackageName() + ";").append(crlf);
		source.append(crlf);
		source.append("import simplejdbc.annotation.Column;").append(crlf);
		source.append("import simplejdbc.annotation.Table;").append(crlf);
		if(hasVersion){
			source.append("import simplejdbc.annotation.Version;").append(crlf);
		}
		if(hasPrimaryKey){
			source.append("import simplejdbc.annotation.PrimaryKey;").append(crlf);
		}
		source.append(importSource);
		source.append(crlf);
		source.append("/**").append(crlf);
		source.append(" * "+entityClass.getTableName()+"テーブルのエンティティクラス").append(crlf);
		source.append(" * @author "+myObject.author).append(crlf);
		source.append(" */").append(crlf);
		source.append("@Table(name=\"" + entityClass.getTableName() + "\")").append(crlf);
		source.append("public class " + entityClass.getClassName() + " {").append(crlf);
		source.append(crlf);
		source.append(fieldSource);
		source.append("}").append(crlf);

		return source.toString();
	}

	/**
	 *
	 * @param entity
	 * @param dao
	 * @return
	 */
	private static final String makeDaoJavaSource(EntityClass entity, DaoClass dao) {

		String crlf = "\r\n";

		StringBuilder source = new StringBuilder();

		source.append("package "+dao.getPackageName()+";").append(crlf);
		source.append("").append(crlf);
		source.append("import java.sql.Connection;").append(crlf);
		source.append("import java.sql.SQLException;").append(crlf);
		source.append("import java.util.List;").append(crlf);
		source.append("").append(crlf);
		source.append("import simplejdbc.dao.AbstractStandardDao;").append(crlf);
		source.append("import simplejdbc.select.SelectJdbc;").append(crlf);
		source.append("import simplejdbc.select.SelectJdbcFactory;").append(crlf);
		source.append("import "+entity.getPackageName()+"."+entity.getClassName()+";").append(crlf);
		source.append("").append(crlf);
		source.append("/**").append(crlf);
		source.append(" * "+entity.getTableName()+"テーブル標準DAOクラス").append(crlf);
		source.append(" * @author "+myObject.author).append(crlf);
		source.append(" */").append(crlf);
		source.append("public class "+dao.getClassName()+" extends AbstractStandardDao<"+entity.getClassName()+"> {").append(crlf);
		source.append("").append(crlf);
		source.append("\t/**").append(crlf);
		source.append("\t * コンストラクタ").append(crlf);
		source.append("\t * @param connection データベース接続オブジェクト").append(crlf);
		source.append("\t */").append(crlf);
		source.append("\tpublic "+dao.getClassName()+"(Connection connection) {").append(crlf);
		source.append("\t\tsuper(connection);").append(crlf);
		source.append("\t}").append(crlf);
		source.append("").append(crlf);
		source.append("\t/**").append(crlf);
		source.append("\t * 全件取得メソッド").append(crlf);
		source.append("\t * @param orderBy ORDER BY句").append(crlf);
		source.append("\t * @return 取得レコードのリスト").append(crlf);
		source.append("\t * @throws SQLException SQL例外").append(crlf);
		source.append("\t */").append(crlf);
		source.append("\tpublic List<"+entity.getClassName()+"> selectAll(String... orderBy) throws SQLException {").append(crlf);
		source.append("\t\tSelectJdbc<"+entity.getClassName()+"> selectJdbc = SelectJdbcFactory.create(dbConnection, "+entity.getClassName()+".class);").append(crlf);
		source.append("\t\tselectJdbc.addOrderBy(orderBy);").append(crlf);
		source.append("\t\treturn selectJdbc.selectList();").append(crlf);
		source.append("\t}").append(crlf);
		source.append("}").append(crlf);

		return source.toString();
	}


	public static class EntityClass {

		private String packageName;

		private String className;

		private String tableName;

		private List<EntityColumn> columns = new ArrayList<EntityColumn>(0);

		public List<EntityColumn> getColumns() {
			return columns;
		}

		public void addColumn(EntityColumn column) {
			this.columns.add(column);
		}

		public void setColumns(List<EntityColumn> columns) {
			this.columns = columns;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

	}

	public static class DaoClass {

		private String packageName;

		private String className;

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

	}


	public static class EntityColumn {

		private String columnName;

		private Class<?> columnClass;

		private String fieldName;

		private String dataType;

		private boolean pkey;

		public boolean isPkey() {
			return pkey;
		}

		public void setPkey(boolean pkey) {
			this.pkey = pkey;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public Class<?> getColumnClass() {
			return columnClass;
		}

		public void setColumnClass(Class<?> columnClass) {
			this.columnClass = columnClass;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

	}

}
