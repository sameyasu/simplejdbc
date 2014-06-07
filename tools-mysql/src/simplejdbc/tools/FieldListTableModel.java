package simplejdbc.tools;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * テーブル一覧のテーブルモデルクラス
 * @author yasu
 *
 */
public class FieldListTableModel extends AbstractTableModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9005806539427288598L;

	/**
	 * テーブル一覧
	 */
	private List<FieldInfo> fieldList;

	/**
	 * コンストラクタ
	 */
	public FieldListTableModel() {
		super();
		this.fieldList = new ArrayList<FieldInfo>(0);
	}

	/**
	 * カラム数
	 */
	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return this.fieldList.size();
	}

	@Override
    public boolean isCellEditable(int row, int col) {
		if (col == 2 || col == 0) {
		    return false;
		} else {
		    return true;
		}
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
		FieldInfo info = this.fieldList.get(rowIndex);
		switch(columnIndex){
		case 0:
			return info.getColumnName();
		case 1:
			return info.getFieldName();
		case 2:
			return info.getFieldClassName();
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
		FieldInfo info = this.fieldList.get(rowIndex);
		switch(columnIndex){
		case 0:
			info.setColumnName((String) value);
			break;
		case 1:
			info.setFieldName((String)value);
			break;
		case 2:
			info.setFieldClassName((String)value);
			break;
		case 3:
			info.setGenerate((Boolean)value);
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public void clear() {
		this.fieldList = new ArrayList<FieldInfo>();
		fireTableDataChanged();
	}

	public void setFieldList(List<FieldInfo> fieldList) {
		this.fieldList = fieldList;
		fireTableDataChanged();
	}

	public List<FieldInfo> getFieldList() {
		return this.fieldList;
	}

}
