package simplejdbc.tools;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * テーブル一覧のテーブルモデルクラス
 * @author yasu
 *
 */
public class TableListTableModel extends AbstractTableModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5540002701267050291L;
	/**
	 * テーブル一覧
	 */
	private List<TableInfo> tableList;

	/**
	 * コンストラクタ
	 */
	public TableListTableModel(List<TableInfo> tableInfoList) {
		super();
		this.tableList = tableInfoList;
	}

	/**
	 * カラム数
	 */
	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return this.tableList.size();
	}

	@Override
    public boolean isCellEditable(int row, int col) {
		if (col == 0) {
		    return false;
		} else {
		    return true;
		}
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
		TableInfo info = this.tableList.get(rowIndex);
		switch(columnIndex){
		case 0:
			return info.getTableName();
		case 1:
			return info.getEntityName();
		case 2:
			return info.getDaoName();
		case 3:
			return info.isGenerate();
		default:
			return null;
		}
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 3:
			return Boolean.class;
		default:
			return String.class;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		TableInfo info = this.tableList.get(rowIndex);
		switch(columnIndex){
		case 0:
			info.setTableName((String) value);
			break;
		case 1:
			info.setEntityName((String)value);
			break;
		case 2:
			info.setDaoName((String)value);
			break;
		case 3:
			info.setGenerate((Boolean)value);
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
