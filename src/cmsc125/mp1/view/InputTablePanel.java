package cmsc125.mp1.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.util.Random;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.ResourcesTableModel;

@SuppressWarnings("serial")
public class InputTablePanel extends JPanel {
	
	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	private JTable diskTable;
	public int numProcess, numResource;

	public InputTablePanel() {
		setLayout(null);
		setBackground(new Color(217,218,219)); // gray dirty white
		initComponents();
		addComponents();
	}

	public void initComponents() {
		// init numProcess to 20 and numResource to 10 for the initial rows and col values in table
		numProcess = 20;
		numResource = 10;

		// drop-down list to choose number of processes
		String[] oneToTwenty = new String[20];
		for (int i = 1; i <= oneToTwenty.length; i++) {
			oneToTwenty[i - 1] = String.format("%s", i);
		}

		// drop-down list to choose number of resources
		String[] oneToTen = new String[10];
		for (int i = 1; i <= oneToTen.length; i++) {
			oneToTen[i - 1] = String.format("%s", i);
		}
		
		// Resources table
		String[] columnResources = { "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9" };

		// allocated table
		String[][] allocatedObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					allocatedObjects[i][j] = "9";
				} else {
					allocatedObjects[i][j] = "0";
				}
			}
		}
		allocatedTable = new JTable(new ResourcesTableModel(columnResources, allocatedObjects));
		allocatedTable.setBackground(Color.WHITE);
		allocatedTable.setRowSelectionAllowed(true);
		allocatedTable.setColumnSelectionAllowed(true);
		allocatedTable.setCellSelectionEnabled(true);
		TableColumnModel allocTCM = allocatedTable.getColumnModel();	
		for (int i = 0; i < 10; i++) {
			TableColumn allocTC = allocTCM.getColumn(i);
			allocTC.setCellEditor(new SpinnerEditor(0, 9));
		}
		
		// maximum table
		String[][] maximumObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					maximumObjects[i][j] = "10";
				} else {
					maximumObjects[i][j] = "0";
				}
			}
		}
		maximumTable = new JTable(new ResourcesTableModel(columnResources, maximumObjects));
		maximumTable.setBackground(Color.WHITE);
		maximumTable.setRowSelectionAllowed(true);
		maximumTable.setColumnSelectionAllowed(true);
		maximumTable.setCellSelectionEnabled(true);
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < 10; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			if (i == 0) {
				maxTC.setCellEditor(new SpinnerEditor(1, 10));
			} else {
				maxTC.setCellEditor(new SpinnerEditor(0, 9));
			}		}
		
		
		// available table
		String[][] availableObjects = new String[1][10];
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 10; j++) {
				availableObjects[i][j] = "0";
			}
		}
		availableTable = new JTable(new ResourcesTableModel(columnResources, availableObjects));
		availableTable.setBackground(Color.WHITE);
		availableTable.setRowSelectionAllowed(true);
		availableTable.setColumnSelectionAllowed(true);
		availableTable.setCellSelectionEnabled(true);

		// Table for arrival time, priority
		String[] columnTime = { "AT", "Priority" };
		String[][] objectsTime = new String[20][2];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 2; j++) {
				objectsTime[i][j] = "0";
			}
		}
		timeTable = new JTable(new ResourcesTableModel(columnTime, objectsTime));
		timeTable.setBackground(Color.WHITE);
		timeTable.setRowSelectionAllowed(true);
		timeTable.setColumnSelectionAllowed(true);
		timeTable.setCellSelectionEnabled(true);
		
		// disk table
		String[][] diskObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				diskObjects[i][j] = "0";
			}
		}
		String[] columnCylinders = { "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9" };
		diskTable = new JTable(new ResourcesTableModel(columnCylinders, diskObjects));
		diskTable.setBackground(Color.WHITE);
		diskTable.setRowSelectionAllowed(true);
		diskTable.setColumnSelectionAllowed(true);
		diskTable.setCellSelectionEnabled(true);
	}

	public void addComponents() {
		JLabel allocatedTableLabel = new JLabel("Allocated Table");
		JLabel maximumTableLabel = new JLabel("Maximum Table");
		JLabel availableTableLabel = new JLabel("Available Table");
		JLabel diskTableLabel = new JLabel("Disk Cylinder Positions");
		JTable colorsTable = new JTable(20, 1);
		
		// resources table
		allocatedTableLabel.setSize(150, 10);
		allocatedTableLabel.setLocation(50, 5);
		add(allocatedTableLabel);
		
		String[] processHeader = { "P#" };
		String[][] colors = new String[20][1];
		for (int i = 0; i < colors.length; i++) {
			colors[i][0] = "P" + i;
		}
		colorsTable = new JTable(new ResourcesTableModel(processHeader, colors) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 ? true : false;
			}
		});
		ColorRowRenderer colorRowRenderer = new ColorRowRenderer();
		colorsTable.getColumn(colorsTable.getColumnName(0)).setCellRenderer(colorRowRenderer);
		
		JScrollPane colorsTablePane = new JScrollPane(colorsTable);
		colorsTablePane.setSize(50, 342);
		colorsTablePane.setLocation(0, 20);
		add(colorsTablePane);
		
		JScrollPane allocatedTablePane = new JScrollPane(allocatedTable);
		allocatedTablePane.setSize(300, 342);
		allocatedTablePane.setLocation(50, 20);
		add(allocatedTablePane);

		// maximum allocated table
		maximumTableLabel.setSize(150, 15);
		maximumTableLabel.setLocation(350, 5);
		add(maximumTableLabel);
		JScrollPane maximumTablePane = new JScrollPane(maximumTable);
		maximumTablePane.setSize(300, 342);
		maximumTablePane.setLocation(350, 20);
		add(maximumTablePane);
		
		//cylinders table
		diskTableLabel.setSize(150, 15);
		diskTableLabel.setLocation(650, 5);
		add(diskTableLabel);
		JScrollPane diskTablePane = new JScrollPane(diskTable);
		diskTablePane.setSize(300, 342);
		diskTablePane.setLocation(650, 20);
		add(diskTablePane);
		
		//available allocated table
		availableTableLabel.setSize(150, 15);
		availableTableLabel.setLocation(50, 365);
		add(availableTableLabel);
		JScrollPane availableTablePane = new JScrollPane(availableTable);
		availableTablePane.setSize(300, 38);
		availableTablePane.setLocation(50, 380);
		add(availableTablePane);

		// table for arrival time, priority
		JScrollPane timeTablePane = new JScrollPane(timeTable);
		timeTablePane.setSize(105, 342);
		timeTablePane.setLocation(955, 20);
		add(timeTablePane);
	}

	public void randAllocatedTable() {
		TableModel maximumTableModel = maximumTable.getModel();
		TableModel allocatedTableModel = allocatedTable.getModel();
		int rowCount = allocatedTableModel.getRowCount();
		int colCount = allocatedTableModel.getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				allocatedTableModel.setValueAt(Integer.toString(random.nextInt(Integer.parseInt((String) maximumTableModel.getValueAt(i, j)) + 1)), i, j);
			}
		}
	}

	public void randMaximumTable() {
		TableModel maximumTableModel = maximumTable.getModel();
		int rowCount = maximumTableModel.getRowCount();
		int colCount = maximumTableModel.getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					maximumTableModel.setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					maximumTableModel.setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}

	public void randAvailableTable() {
		int rowCount = availableTable.getModel().getRowCount();
		int colCount = availableTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				int maxResource = Integer.parseInt(((ResourcesTableModel) maximumTable.getModel()).getData()[i][j]);
				int availableResource = Integer.parseInt(((ResourcesTableModel) allocatedTable.getModel()).getData()[i][j]);
				availableTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + maxResource-availableResource), i, j);
			}
		}
	}
	
	public void randDiskTable() {
		TableModel diskTableModel = diskTable.getModel();
		int rowCount = diskTableModel.getRowCount();
		Random random = new Random();
		for (int i = 0, j; i < rowCount; i++) {
			int colCount = Integer.parseInt((String) maximumTable.getModel().getValueAt(i, 0));
			for ( j=0 ; j < colCount; j++) {
				try {
					diskTableModel.setValueAt(Integer.toString(random.nextInt(100)), i, j);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(e);
				}
			}
			for ( ; j < diskTableModel.getColumnCount(); j++) {
				diskTableModel.setValueAt("-", i, j);
			}
				
		}
	}

	public void randATPT() {
		int rowCount = timeTable.getModel().getRowCount();
		int colCount = timeTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				timeTable.getModel().setValueAt(Integer.toString(random.nextInt(rowCount)), i, j);
			}
		}
	}
	
	public void setResourcesTableColumnSize(int numCols) {
		// allocated table
		ResourcesTableModel currentAllocatedModel = ((ResourcesTableModel) allocatedTable.getModel());
		String[][] currentAllocatedTableData = currentAllocatedModel.getData();
		String[][] newAllocatedTableData = new String[currentAllocatedTableData.length][numCols];

		for (int i = 0; i < currentAllocatedTableData.length; i++) {
			for (int j = 1; j < currentAllocatedTableData[i].length; j++) {
				if (currentAllocatedTableData[i][j] != null && j < numCols) {
					newAllocatedTableData[i][j] = currentAllocatedTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAllocatedTableData.length; i++) {
			for (int j = 0; j < newAllocatedTableData[i].length; j++) {
				if (newAllocatedTableData[i][j] == null) {
					if (j == 0) {
						newAllocatedTableData[i][j] = "9";
					} else {
						newAllocatedTableData[i][j] = "0";						
					}
				}
			}
		}

		String[] newAllocatedColumns = new String[numCols];
		for (int i = 0; i < numCols; i++) {
			newAllocatedColumns[i] = "R" + (i);
		}

		((ResourcesTableModel) allocatedTable.getModel()).setColumnNames(newAllocatedColumns);

		allocatedTable.setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));
		TableColumnModel allocTCM = allocatedTable.getColumnModel();	
		for (int i = 0; i < numCols; i++) {
			TableColumn allocTC = allocTCM.getColumn(i);
			allocTC.setCellEditor(new SpinnerEditor(0, 9));
		}
		

		// maximum table
		ResourcesTableModel currentMaximumModel = ((ResourcesTableModel) maximumTable.getModel());
		String[][] currentMaximumTableData = currentMaximumModel.getData();
		String[][] newMaximumTableData = new String[currentMaximumTableData.length][numCols];

		for (int i = 0; i < currentMaximumTableData.length; i++) {
			for (int j = 0; j < currentMaximumTableData[i].length; j++) {
				if (currentMaximumTableData[i][j] != null && j < numCols) {
					newMaximumTableData[i][j] = currentMaximumTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newMaximumTableData.length; i++) {
			for (int j = 0; j < newMaximumTableData[i].length; j++) {
				if (newMaximumTableData[i][j] == null) {
					if (j == 0) {
						newMaximumTableData[i][j] = "10";
					} else {
						newMaximumTableData[i][j] = "0";
					}
				}
			}
		}

		String[] newMaximumColumns = new String[numCols];
		for (int i = 0; i < numCols; i++) {
			newMaximumColumns[i] = "R" + (i);
		}

		((ResourcesTableModel) maximumTable.getModel()).setColumnNames(newMaximumColumns);

		maximumTable.setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < numCols; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			if (i == 0) {
				maxTC.setCellEditor(new SpinnerEditor(1, 10));
			} else {
				maxTC.setCellEditor(new SpinnerEditor(0, 9));
			}
		}
		
		
		// available table
		ResourcesTableModel currentAvailableModel = ((ResourcesTableModel) availableTable.getModel());
		String[][] currentAvailableTableData = currentAvailableModel.getData();
		String[][] newAvailableTableData = new String[currentAvailableTableData.length][numCols];

		for (int i = 0; i < currentAvailableTableData.length; i++) {
			for (int j = 0; j < currentAvailableTableData[i].length; j++) {
				if (currentAvailableTableData[i][j] != null && j < numCols) {
					newAvailableTableData[i][j] = currentAvailableTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAvailableTableData.length; i++) {
			for (int j = 0; j < newAvailableTableData[i].length; j++) {
				if (newAvailableTableData[i][j] == null) {
					newAvailableTableData[i][j] = "0";
				}
			}
		}

		String[] newAvailableColumns = new String[numCols];
		for (int i = 0; i < numCols; i++) {
			newAvailableColumns[i] = "R" + (i);
		}

		((ResourcesTableModel) availableTable.getModel()).setColumnNames(newAvailableColumns);

		availableTable.setModel(new ResourcesTableModel(currentAvailableModel.getColumnNames(), newAvailableTableData));
	}

	public void setResourcesTableRowSize(int numRows) {
		// allocated table
		ResourcesTableModel currentAllocatedModel = ((ResourcesTableModel) allocatedTable.getModel());
		String[][] currentAllocatedTableData = currentAllocatedModel.getData();
		String[][] newAllocatedTableData = new String[numRows][currentAllocatedTableData[0].length];

		for (int i = 0; i < currentAllocatedTableData.length; i++) {
			for (int j = 0; j < currentAllocatedTableData[i].length; j++) {
				if (currentAllocatedTableData[i][j] != null && i < numRows) {
					newAllocatedTableData[i][j] = currentAllocatedTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAllocatedTableData.length; i++) {
			for (int j = 0; j < newAllocatedTableData[i].length; j++) {
				if (newAllocatedTableData[i][j] == null) {
					newAllocatedTableData[i][j] = "0";
				}
			}
		}

		allocatedTable.setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));
		TableColumnModel allocTCM = allocatedTable.getColumnModel();	
		for (int i = 0; i < newAllocatedTableData[0].length; i++) {
			TableColumn allocTC = allocTCM.getColumn(i);
			allocTC.setCellEditor(new SpinnerEditor(0, 9));
		}
		
		
		// maximum table
		ResourcesTableModel currentMaximumModel = ((ResourcesTableModel) maximumTable.getModel());
		String[][] currentMaximumTableData = currentMaximumModel.getData();
		String[][] newMaximumTableData = new String[numRows][currentMaximumTableData[0].length];

		for (int i = 0; i < currentMaximumTableData.length; i++) {
			for (int j = 1; j < currentMaximumTableData[i].length; j++) {
				if (currentMaximumTableData[i][j] != null && i < numRows) {
					newMaximumTableData[i][j] = currentMaximumTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newMaximumTableData.length; i++) {
			for (int j = 0; j < newMaximumTableData[i].length; j++) {
				if (newMaximumTableData[i][j] == null) {
					if (j == 0) {
						newMaximumTableData[i][j] = "10";
					} else {
						newMaximumTableData[i][j] = "0";
					}
				}
			}
		}

		maximumTable.setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < newMaximumTableData[0].length; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			if (i == 0) {
				maxTC.setCellEditor(new SpinnerEditor(1, 10));
			} else {
				maxTC.setCellEditor(new SpinnerEditor(0, 9));
			}
		}
		

		// System.exit(1);
		resizeTimeTable(numRows);
		
		// disk table
		ResourcesTableModel currentDiskModel = ((ResourcesTableModel) diskTable.getModel());
		String[][] currentDiskTableData = currentDiskModel.getData();
		String[][] newDiskTableData = new String[numRows][currentDiskTableData[0].length];

		for (int i = 0; i < currentDiskTableData.length; i++) {
			for (int j = 0; j < currentDiskTableData[i].length; j++) {
				if (currentDiskTableData[i][j] != null && i < numRows) {
					newDiskTableData[i][j] = currentDiskTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newDiskTableData.length; i++) {
			for (int j = 0; j < newDiskTableData[i].length; j++) {
				if (newDiskTableData[i][j] == null) {
					newDiskTableData[i][j] = "0";
				}
			}
		}

		diskTable.setModel(new ResourcesTableModel(currentDiskModel.getColumnNames(), newDiskTableData));
	}
	
	public void resizeTimeTable(int numRows) {
		ResourcesTableModel timeTableModel = ((ResourcesTableModel) timeTable.getModel());
		String[][] objects = timeTableModel.getData();
		String[][] newStrings = new String[numRows][2];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < 2; j++) {
				if (i < objects.length && objects[i][j] != null) {
					newStrings[i][j] = objects[i][j];
				} else {
					newStrings[i][j] = "0";
				}
			}
		}

		timeTable.setModel(new ResourcesTableModel(timeTableModel.getColumnNames(), newStrings));
	}

	public JTable getResourcesTable() {
		return allocatedTable;
	}
	public JTable getTimeTable() {
		return timeTable;
	}
	public JTable getAllocatedTable() {
		return allocatedTable;
	}
	public JTable getMaximumTable() {
		return maximumTable;
	}
	public JTable getAvailableTable() {
		return availableTable;
	}
	
	public JTable getDiskTable() {
		return diskTable;
	}

	/**
	* Applied background and foreground color to single column of a JTable
	* in order to distinguish it apart from other columns.
	*/
	class ColorColumnRenderer extends DefaultTableCellRenderer
	{
	   Color bkgndColor, fgndColor;
	     
	   public ColorColumnRenderer(Color bkgnd, Color foregnd) {
	      super();
	      bkgndColor = bkgnd;
	      fgndColor = foregnd;
	   }
	     
	   public Component getTableCellRendererComponent
	        (JTable table, Object value, boolean isSelected,
	         boolean hasFocus, int row, int column)
	   {
	      Component cell = super.getTableCellRendererComponent
	         (table, value, isSelected, hasFocus, row, column);
	  
	      cell.setBackground( bkgndColor );
	      cell.setForeground( fgndColor );
	      
	      return cell;
	   }
	}
	
	class ColorRowRenderer extends JLabel implements TableCellRenderer
	{
		public ColorRowRenderer() {
			setOpaque(true);
		}
		
		public Component getTableCellRendererComponent
		(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column)
		{
			Object columnValue = table.getValueAt(row, table.getColumnModel().getColumnIndex("P#"));
			
			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(table.getBackground());
				setForeground(table.getForeground());
			}
			
			for (int i = 0; i < 20; i++) {
				if (columnValue.equals("P" + i)) {
					setBackground(ColorConstants.getColor(i));
					setText("P" + i);
					
					if (i == 19) {
						setForeground(Color.WHITE);
					}
				}
			}
			
			return this;
		}
	}
	
	public static class SpinnerEditor extends DefaultCellEditor
    {
        JSpinner spinner;
        JSpinner.DefaultEditor editor;
        JTextField textField;
        boolean valueSet;

        // Initializes the spinner.
        public SpinnerEditor(int minRange, int maxRange) {
            super(new JTextField());
            SpinnerModel sm = new SpinnerNumberModel(minRange, minRange, maxRange, 1);
            spinner = new JSpinner(sm);
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            textField.addFocusListener( new FocusListener() {
                public void focusGained( FocusEvent fe ) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if ( valueSet ) {
                                textField.setCaretPosition(1);
                            }
                        }
                    });
                }
                public void focusLost( FocusEvent fe ) {
                }
            });
            textField.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent ae ) {
                    stopCellEditing();
                }
            });
        }

        // Prepares the spinner component and returns it.
        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column
        ) {
            if ( !valueSet ) {
                spinner.setValue(Integer.parseInt((String) value));
            }
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    textField.requestFocus();
                }
            });
            return spinner;
        }

        public boolean isCellEditable( EventObject eo ) {
            if ( eo instanceof KeyEvent ) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                valueSet = true;
            } else {
                valueSet = false;
            }
            return true;
        }

        // Returns the spinners current value.
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        public boolean stopCellEditing() {
            try {
                editor.commitEdit();
                spinner.commitEdit();
            } catch ( java.text.ParseException e ) {
            	e.printStackTrace();
            }
            return super.stopCellEditing();
        }
    }
}