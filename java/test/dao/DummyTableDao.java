package dao;

import java.sql.Connection;
import simplejdbc.dao.AbstractStandardDao;
import entity.DummyTable;

/**
 * DUMMY_TABLEテーブル標準DAOクラス
 * @author simpleJdbc (AutoGen)
 */
public class DummyTableDao extends AbstractStandardDao<DummyTable> {

	/**
	* コンストラクタ
	* @param connection データベース接続オブジェクト
	*/
	public DummyTableDao(Connection connection) {
		super(connection);
	}
}
