package simplejdbc.tools;

/**
 * USER_TABLESエンティティ作成ツールのプロパティ
 * @author yasu
 *
 */
public class ToolsProperties {

	private String dbHost;

	private String dbPort;

	private String dbUser;

	private String dbPassword;

	private String dbName;

	private boolean genEntity;

	private boolean genDao;

	private String entityPackage;

	private String daoPackage;

	private String sourceFilePrefix;

	private String sourceEncoding;

	private String daoSuffix;

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public boolean isGenEntity() {
		return genEntity;
	}

	public void setGenEntity(boolean genEntity) {
		this.genEntity = genEntity;
	}

	public boolean isGenDao() {
		return genDao;
	}

	public void setGenDao(boolean genDao) {
		this.genDao = genDao;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getSourceFilePrefix() {
		return sourceFilePrefix;
	}

	public void setSourceFilePrefix(String sourceFilePrefix) {
		this.sourceFilePrefix = sourceFilePrefix;
	}

	public String getSourceEncoding() {
		return sourceEncoding;
	}

	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}

	public String getDaoSuffix() {
		return daoSuffix;
	}

	public void setDaoSuffix(String daoSuffix) {
		this.daoSuffix = daoSuffix;
	}

}
