package simplejdbc.tools;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import simplejdbc.exception.SimpleJdbcRuntimeException;
import simplejdbc.tools.JavaSourceMaker.DaoClass;
import simplejdbc.tools.JavaSourceMaker.EntityClass;
import simplejdbc.tools.JavaSourceMaker.EntityColumn;
import simplejdbc.util.DbResourceUtil;

/**
 * カスタムSQL文のエンティティ自動生成ツール
 * @author yasu
 *
 */
public class CustomSqlEntityMaker {

	/**
	 * ソース生成メソッド
	 */
	public static final boolean checkConnect(ToolsProperties properties) {

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

		DbResourceUtil.closeIgnoreException(dbConnection);
		return true;
	}

	public static List<FieldInfo> executeSelect(ToolsProperties properties, String sql) {

		List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

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

		PreparedStatement pstmt = null;
		ResultSet result = null;

		try{

			pstmt = dbConnection.prepareStatement(sql);
			result = pstmt.executeQuery();

			result.next();
			ResultSetMetaData metaData = result.getMetaData();

			System.out.println("カラム数:"+metaData.getColumnCount());
			for(int i=1; i<=metaData.getColumnCount(); i++) {
				FieldInfo field = new FieldInfo();
				field.setColumnName(metaData.getColumnLabel(i));
				Class<?> fieldClass = class2WrapClass(metaData.getColumnClassName(i));
				if(fieldClass.isArray()){
					field.setFieldClassName(fieldClass.getSimpleName());
				}
				else {
					field.setFieldClassName(fieldClass.getName());
				}
				field.setFieldName(convertColumnName2FieldName(metaData.getColumnLabel(i)));
				field.setGenerate(true);
				fieldList.add(field);
				System.out.println(field.getColumnName() + " => " + field.getFieldName() + " ("+field.getFieldClassName()+")");
			}

		} catch (ClassNotFoundException e) {
			throw new SimpleJdbcRuntimeException(null, "SQL実行に失敗しました。",e);
		} catch (SQLException e) {
			throw new SimpleJdbcRuntimeException(null, "SQL実行に失敗しました。",e);
		}
		finally {
			try {
				DbResourceUtil.close(pstmt, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		DbResourceUtil.closeIgnoreException(dbConnection);

		return fieldList;
	}


	public static void executeMake(ToolsProperties properties, List<FieldInfo> fieldList, String entityClassName, String daoClassName) {

		String dbName = properties.getDbName();
		String dbHost = properties.getDbHost();
		String dbPort = properties.getDbPort();
		String user = properties.getDbUser();
		String password = properties.getDbPassword();

		String packageName = properties.getEntityPackage();
		boolean makeEntity = properties.isGenEntity();

		boolean makeDao = properties.isGenDao();

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

		try{
			EntityClass entityClass = new EntityClass();
			entityClass.setClassName(entityClassName);
			entityClass.setPackageName(packageName);
			entityClass.setTableName("CUSTOM_SQL");

			//エンティティクラス作成
			if(makeEntity){
				for(FieldInfo field : fieldList) {
					if(field.isGenerate() == false){
						System.out.println("SKIP ("+field.getColumnName()+")");
						continue;
					}
					EntityColumn column = new EntityColumn();
					column.setColumnName(field.getColumnName());
					try {
						if(field.getFieldClassName().equals("byte[]")){
							column.setColumnClass(byte[].class);
						}
						else {
							column.setColumnClass(Class.forName(field.getFieldClassName()));
						}
					} catch (ClassNotFoundException e) {
						throw new SimpleJdbcRuntimeException(null, "フィールドに指定されたクラスが見つかりません。フィールド名:"+field.getFieldName()+" クラス名:"+field.getFieldClassName(),e);
					}
					column.setFieldName(field.getFieldName());
					entityClass.addColumn(column);
					System.out.println(column.getColumnName() + " => " + column.getFieldName() + " ("+column.getColumnClass()+")");
				}

				JavaSourceMaker.makeEntityFile(properties, entityClass);
			}

			//DAOクラス作成
			if(makeDao){
				DaoClass daoClass = makeDao(properties, daoClassName);
				JavaSourceMaker.makeDaoFile(properties, entityClass, daoClass);
			}

		}
		finally {
			DbResourceUtil.closeIgnoreException(dbConnection);
		}

	}


	/**
	 * Daoクラス生成メソッド
	 * @param properties
	 * @param table
	 * @return
	 */
	private static DaoClass makeDao(ToolsProperties properties, String daoClassName) {
		DaoClass dao = new DaoClass();
		dao.setPackageName(properties.getDaoPackage());
		dao.setClassName(daoClassName);
		return dao;
	}

	private static String convertColumnName2FieldName(String columnName) {

		columnName = columnName.replaceAll("[ \\(\\),\\.'\\*]", "_");
		columnName = columnName.replaceAll("_+", "_");

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

	private static Class<?> class2WrapClass(String className) throws ClassNotFoundException {
		if(className.startsWith("java.lang.")){
			return Class.forName(className);
		}
		else {
			if(className.equals("java.sql.Timestamp")){
				//Oracleの場合はjava.sql.TimestampがDATE型となるため
				return Date.class;
			}
			else if(className.equals("java.sql.Date")){
				return Class.forName("java.util.Date");
			}
			else if(className.equals("java.math.BigDecimal")){
				//IntegerやLongの判断が事実上できない
				//大は小を兼ねるということで。
				return BigDecimal.class;
			}
			else if(className.equals("[B")){
				//バイト配列の場合には暫定的に文字列型に変換
				return String.class;
			}

		}
		throw new ClassNotFoundException("対応していないクラスです。className: "+className);
	}


}
