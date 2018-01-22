package cmsc125.mp1.model;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ResourcesTableModel extends AbstractTableModel {

	private String[] columnNames;
	private String[][] data;

	public ResourcesTableModel(String[] columnNames, String[][] data) {
		this.setColumnNames(columnNames);
		this.setData(data);
	}

	public ResourcesTableModel() {

	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return getColumnNames().length;
	}

	@Override
	public int getRowCount() {
		return getData().length;
	}

	@Override
	public String getValueAt(int row, int column) {
		return getData()[row][column];
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value + "";
		fireTableCellUpdated(row, col);
	}
}