package simplejdbc.tools;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.Table;
import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.sql.SelectSqlJdbc;
import simplejdbc.sql.SelectSqlJdbcFactory;
import simplejdbc.tools.JavaSourceMaker.DaoClass;
import simplejdbc.tools.JavaSourceMaker.EntityClass;
import simplejdbc.tools.JavaSourceMaker.EntityColumn;
import simplejdbc.tools.windows.ExplorerOpener;
import simplejdbc.util.DbResourceUtil;

/**
 * エンティティ自動生成ツール
 * @author yasu
 *
 */
public class UserTablesEntityMaker implements Runnable {

	private static Log log = LogFactory.getLog(UserTablesEntityMaker.class);

	/**
	 * プロパティ
	 */
	private ToolsProperties properties;

	/**
	 * 進捗バーオブジェクト
	 */
	private JProgressBar progressBar;

	/**
	 * テーブル一覧
	 */
	private List<TableInfo> tableInfoList;

	/**
	 * コンストラクタ
	 * @param properties プロパティ
	 * @param progressBar 進捗バーオブジェクト
	 */
	public UserTablesEntityMaker(ToolsProperties properties, List<TableInfo> tableInfoList, JProgressBar progressBar) {
		super();
		this.properties = properties;
		this.progressBar = progressBar;
		this.tableInfoList = tableInfoList;
	}

	/**
	 * ソース生成メソッド
	 */
	public static final List<TableInfo> getTableList(ToolsProperties properties) {

		String dbName = properties.getDbName();
		String dbHost = properties.getDbHost();
		String dbPort = properties.getDbPort();
		String user = properties.getDbUser();
		String password = properties.getDbPassword();

		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.exit(1);
        }

		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?user="+user+"&password="+password);
		} catch (SQLException e) {
			throw new SimpleJdbcRuntimeException(null, "データベース接続に失敗しました。",e);
		}

		System.out.println("Database Connect OK.");

		PreparedStatement preStmt = null;
		ResultSet result = null;

		try{
			preStmt = dbConnection.prepareStatement(
					"show tables"
					);

			result = preStmt.executeQuery();

			List<TableInfo> tableInfoList = new ArrayList<TableInfo>();

			while(result.next()){
				TableInfo info = new TableInfo();
				info.setTableName(result.getString(1));
				info.setEntityName(convertTableName2ClassName(info.getTableName()));
				info.setDaoName(convertTableName2ClassName(info.getTableName())+properties.getDaoSuffix());
				info.setGenerate(true);
				tableInfoList.add(info);
			}

			return tableInfoList;

		} catch (SQLException e) {
			throw new SimpleJdbcRuntimeException(null, "データベース接続に失敗しました。",e);
		}
		finally{
			try{
				DbResourceUtil.close(preStmt, result);
			} catch (SQLException e) {
				throw new SimpleJdbcRuntimeException(null, "リソースクローズに失敗しました。",e);
			}
			DbResourceUtil.closeIgnoreException(dbConnection);
		}

	}


	/**
	 * ソース生成メソッド
	 */
	public static final void execute(ToolsProperties properties, List<TableInfo> tableInfoList, JProgressBar progressBar) {

		String packageName = properties.getEntityPackage();
		boolean makeEntity = properties.isGenEntity();
		boolean makeDao = properties.isGenDao();

		String dbName = properties.getDbName();
		String dbHost = properties.getDbHost();
		String dbPort = properties.getDbPort();
		String user = properties.getDbUser();
		String password = properties.getDbPassword();

		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.exit(1);
        }

		Connection dbConnection;
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?user="+user+"&password="+password);
		} catch (SQLException e) {
			throw new SimpleJdbcRuntimeException(null, "データベース接続に失敗しました。",e);
		}

		String owner = user.toUpperCase();

		System.out.println("Database Connect OK.");

		try{
			progressBar.setMaximum(tableInfoList.size());
			int counter=1;
			for(TableInfo table : tableInfoList){

				System.out.println("----- TABLE ("+table.getTableName()+") -----");
				if(table.isGenerate() == false){
					//生成しないならスキップ
					System.out.println("SKIP!");
					progressBar.setValue(counter++);
					continue;
				}

				table.setOwner(owner);
				EntityClass entityClass;
				try {
					entityClass = makeEntity(dbConnection, table, packageName);
				} catch (IOException e1) {
					throw new SimpleJdbcRuntimeException(null, "エンティティクラスソース生成に失敗しました。テーブル名:"+table.getTableName());
				}

				//エンティティクラス作成
				if(makeEntity){
					JavaSourceMaker.makeEntityFile(properties, entityClass);
				}

				//DAOクラス作成
				if(makeDao){
					DaoClass daoClass = makeDao(properties, table);
					JavaSourceMaker.makeDaoFile(properties, entityClass, daoClass);
				}

				progressBar.setValue(counter++);
			}
		} catch (SQLException e) {
			throw new SimpleJdbcRuntimeException(null, "テーブルエンティティ自動生成に失敗しました。",e);
		}
		finally{
			DbResourceUtil.closeIgnoreException(dbConnection);
		}
	}

	/**
	 * Daoクラス生成メソッド
	 * @param properties
	 * @param table
	 * @return
	 */
	private static DaoClass makeDao(ToolsProperties properties, TableInfo table) {
		DaoClass dao = new DaoClass();
		dao.setPackageName(properties.getDaoPackage());
		dao.setClassName(table.getDaoName());
		return dao;
	}

	/**
	 * エンティティ生成メソッド
	 * @param connection
	 * @param packageName
	 * @param viewName
	 * @throws SQLException
	 * @throws IOException
	 */
	public static final EntityClass makeEntity(Connection connection, TableInfo table, String packageName) throws SQLException, IOException {

		EntityClass entityClass = new EntityClass();

		SelectSqlJdbc<UserTableColumn> selectJdbc = SelectSqlJdbcFactory.create(connection, UserTableColumn.class);

		entityClass.setPackageName(packageName);
		entityClass.setTableName(table.getTableName());
		entityClass.setClassName(table.getEntityName());

		System.out.println(entityClass.getTableName() + " => " + entityClass.getPackageName() + "." + entityClass.getClassName());

		selectJdbc.addSql(	"desc " + table.getTableName());

		List<UserTableColumn> columns = selectJdbc.selectList();

		for(UserTableColumn tableColumn : columns){
			EntityColumn column = new EntityColumn();
			column.setColumnName(tableColumn.columnName);
			column.setColumnClass(dataType2WrapperClass(tableColumn));
			column.setFieldName(convertColumnName2FieldName(column.getColumnName()));
			column.setDataType(tableColumn.dataType);
			column.setPkey(tableColumn.pkey != null && (tableColumn.pkey.equals("PRI")));

			entityClass.addColumn(column);
			System.out.printf("%s (%s) %s%n", column.getColumnName(), column.getDataType(), (column.isPkey() ? "○" : ""));
		}

		return entityClass;

	}

	private static String convertColumnName2FieldName(String columnName) {

		StringBuilder fieldName = new StringBuilder();
		StringTokenizer token = new StringTokenizer(columnName, "_");

		while(token.hasMoreElements()){
			String t = token.nextToken();
			if(fieldName.length() > 0){
				char start = t.charAt(0);
				fieldName.append(Character.toUpperCase(start)).append(t.substring(1).toLowerCase());
			}
			else {
				fieldName.append(t.toLowerCase());
			}
		}
		return fieldName.toString();
	}

	private static String convertTableName2ClassName(String tableName) {

		tableName = tableName.replaceAll("\\$", "");
		StringBuilder fieldName = new StringBuilder();
		StringTokenizer token = new StringTokenizer(tableName, "_");

		while(token.hasMoreElements()){
			String t = token.nextToken();
			char start = t.charAt(0);
			fieldName.append(Character.toUpperCase(start)).append(t.substring(1).toLowerCase());
		}


		return fieldName.toString();
	}

	private static Class<?> dataType2WrapperClass(UserTableColumn tableColumn) {
		String dataType = tableColumn.dataType;
		if(dataType.startsWith("varchar") || dataType.startsWith("text") || dataType.startsWith("enum") || dataType.startsWith("char") || dataType.startsWith("blob") ){
			return String.class;
		}
		else if(dataType.startsWith("tinyint") || dataType.startsWith("integer")){
			return Integer.class;
		}
		else if(dataType.startsWith("smallint") || dataType.startsWith("mediumint")){
			return Integer.class;
		}
		else if(dataType.startsWith("int") || dataType.startsWith("year")){
			return Integer.class;
		}
		else if(dataType.startsWith("bigint")){
			return BigInteger.class;
		}
		else if(dataType.equals("float") || dataType.equals("double")){
			return Double.class;
		}
		else if(dataType.equals("date")){
			return Date.class;
		}
		else if(dataType.equals("datetime")){
			return Date.class;
		}
		else if(dataType.equals("time")){
			return Time.class;
		}
		else if(dataType.equals("timestamp")){
			return Timestamp.class;
		}
		else {
			log.warn("not supported type: "+dataType);
			//RAW型 BLOB型など
			return byte[].class;
		}
	}

	@Table(name="USER_TAB_COLUMNS")
	public static class UserTableColumn {

		@Column(name="Field")
		public String columnName;

		@Column(name="Type")
		public String dataType;

		@Column(name="Key")
		public String pkey;
	}

	/**
	 * スレッド実行メソッド
	 */
	public void run() {
		execute(this.properties, this.tableInfoList, this.progressBar);
		ExplorerOpener.open(this.properties.getSourceFilePrefix());
	}

}
