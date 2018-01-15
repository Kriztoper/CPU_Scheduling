package cmsc125.mp1.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.image.ColorModel;
import java.util.EventObject;
import java.util.Random;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.text.TableView.TableRow;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.ResourcesTableModel;

@SuppressWarnings("serial")
public class InputTablePanel extends JPanel {

	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	public int numProcess, numResource;

	public InputTablePanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setBackground(new Color(217,218,219)); // gray dirty white
	}

	public void initComponents() {
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
//				if (j == 0) {
//					allocatedObjects[i][j] = "1";
//				} else {
					allocatedObjects[i][j] = "0";
//				}
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
			allocTC.setCellEditor(new SpinnerEditor());
		}
		
		// maximum table
		String[][] maximumObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					maximumObjects[i][j] = "9";
				} else {
					maximumObjects[i][j] = "0";
				}
			}
		}
		maximumTable = new JTable(new ResourcesTableModel(columnResources, maximumObjects));/* {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 ? true : false;
			}
		});*/
		maximumTable.setBackground(Color.WHITE);
//		TableColumn tm = maximumTable.getColumnModel().getColumn(0);
//		tm.setCellRenderer(new ColorColumnRenderer(Color.LIGHT_GRAY, Color.GRAY));
		maximumTable.setRowSelectionAllowed(true);
		maximumTable.setColumnSelectionAllowed(true);
		maximumTable.setCellSelectionEnabled(true);
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < 10; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			maxTC.setCellEditor(new SpinnerEditor());
		}
		
		
		// available table
		String[][] availableObjects = new String[1][10];
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 10; j++) {
//				if (j == 0) {
//					availableObjects[i][j] = "1";
//				} else {
					availableObjects[i][j] = "0";
//				}
			}
		}
		availableTable = new JTable(new ResourcesTableModel(columnResources, availableObjects));
		availableTable.setBackground(Color.WHITE);
		availableTable.setRowSelectionAllowed(true);
		availableTable.setColumnSelectionAllowed(true);
		availableTable.setCellSelectionEnabled(true);
		/*
		 * DefaultTableCellRenderer centerRenderer = new
		 * DefaultTableCellRenderer(); centerRenderer.setHorizontalAlignment(
		 * JLabel.CENTER); resourcesTable.setDefaultRenderer(String.class,
		 * centerRenderer);
		 */

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

	}

	public void addComponents() {
		JLabel allocatedTableLabel = new JLabel("Allocated Table");
		JLabel maximumTableLabel = new JLabel("Maximum Table");
		JLabel availableTableLabel = new JLabel("Available Table");
		JTable colorsTable = new JTable(20, 1);
		
		// resources table
		allocatedTableLabel.setSize(150, 10);
		allocatedTableLabel.setLocation(50, 0);
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
		colorsTablePane.setLocation(0, 10);
		add(colorsTablePane);
		
		JScrollPane allocatedTablePane = new JScrollPane(allocatedTable);
		allocatedTablePane.setSize(300, 342);
		allocatedTablePane.setLocation(50, 10);
		add(allocatedTablePane);

		// maximum allocated table
		maximumTableLabel.setSize(150, 10);
		maximumTableLabel.setLocation(350, 0);
		add(maximumTableLabel);
		JScrollPane maximumTablePane = new JScrollPane(maximumTable);
		maximumTablePane.setSize(300, 342);
		maximumTablePane.setLocation(350, 10);
		add(maximumTablePane);

		//available allocated table
		availableTableLabel.setSize(150, 10);
		availableTableLabel.setLocation(650, 0);
		add(availableTableLabel);
		JScrollPane availableTablePane = new JScrollPane(availableTable);
		availableTablePane.setSize(300, 342);
		availableTablePane.setLocation(650, 10);
		add(availableTablePane);

		// table for arrival time, priority
		JScrollPane timeTablePane = new JScrollPane(timeTable);
		timeTablePane.setSize(105, 342);
		timeTablePane.setLocation(955, 10);
		add(timeTablePane);

	}

	public void randAllocatedTable() {
		TableModel maximumTableModel = maximumTable.getModel();
		int rowCount = allocatedTable.getModel().getRowCount();
		int colCount = allocatedTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
//				if (j == 0) {
//					allocatedTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
//				} else {
					allocatedTable.getModel().setValueAt(Integer.toString(random.nextInt(Integer.parseInt((String) maximumTableModel.getValueAt(i, j)) + 1)), i, j);
//				}
			}
		}
	}

	public void randMaximumTable() {
		int rowCount = maximumTable.getModel().getRowCount();
		int colCount = maximumTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					maximumTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					maximumTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
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
//				if (j == 0) {
//					availableTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
//				} else {
					availableTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
//				}
			}
		}
	}

	public void randATPT() {
		int rowCount = timeTable.getModel().getRowCount();
		int colCount = timeTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
//				if (j == 0) {
//					timeTable.getModel().setValueAt(Integer.toString(random.nextInt(rowCount) + 1), i, j);
//				} else {
					timeTable.getModel().setValueAt(Integer.toString(random.nextInt(rowCount)), i, j);
//				}
			}
		}
	}
	
	public void setResourcesTableColumnSize(int numColumns) {
		// allocated table
		ResourcesTableModel currentAllocatedModel = ((ResourcesTableModel) allocatedTable.getModel());
		String[][] currentAllocatedTableData = currentAllocatedModel.getData();
		String[][] newAllocatedTableData = new String[currentAllocatedTableData.length][numColumns];

		for (int i = 0; i < currentAllocatedTableData.length; i++) {
			for (int j = 0; j < currentAllocatedTableData[i].length; j++) {
				if (currentAllocatedTableData[i][j] != null && j < numColumns) {
					newAllocatedTableData[i][j] = currentAllocatedTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAllocatedTableData.length; i++) {
			for (int j = 0; j < newAllocatedTableData[i].length; j++) {
				if (newAllocatedTableData[i][j] == null) {
//					if (j == 0) {
//						newAllocatedTableData[i][j] = "1";								
//					} else {
						newAllocatedTableData[i][j] = "0";						
//					}
				}
			}
		}

		String[] newAllocatedColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newAllocatedColumns[i] = "R" + (i);
		}

		((ResourcesTableModel) allocatedTable.getModel()).setColumnNames(newAllocatedColumns);

		allocatedTable.setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));
		TableColumnModel allocTCM = allocatedTable.getColumnModel();	
		for (int i = 0; i < numColumns; i++) {
			TableColumn allocTC = allocTCM.getColumn(i);
			allocTC.setCellEditor(new SpinnerEditor());
		}
		

		// maximum table
		ResourcesTableModel currentMaximumModel = ((ResourcesTableModel) maximumTable.getModel());
		String[][] currentMaximumTableData = currentMaximumModel.getData();
		String[][] newMaximumTableData = new String[currentMaximumTableData.length][numColumns];

		for (int i = 0; i < currentMaximumTableData.length; i++) {
			for (int j = 1; j < currentMaximumTableData[i].length; j++) {
				if (currentMaximumTableData[i][j] != null && j < numColumns) {
					newMaximumTableData[i][j] = currentMaximumTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newMaximumTableData.length; i++) {
			for (int j = 0; j < newMaximumTableData[i].length; j++) {
				if (newMaximumTableData[i][j] == null) {
					if (j == 0) {
						newMaximumTableData[i][j] = "9";
					} else {
						newMaximumTableData[i][j] = "0";
					}
				}
			}
		}

		String[] newMaximumColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newMaximumColumns[i] = "R" + (i);
		}

		((ResourcesTableModel) maximumTable.getModel()).setColumnNames(newMaximumColumns);

		maximumTable.setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));/* {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 ? true : false;
			}
		});*/
//		TableColumn tm = maximumTable.getColumnModel().getColumn(0);
//		tm.setCellRenderer(new ColorColumnRenderer(Color.LIGHT_GRAY, Color.GRAY));
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < numColumns; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			maxTC.setCellEditor(new SpinnerEditor());
		}
		
		
		// available table
		ResourcesTableModel currentAvailableModel = ((ResourcesTableModel) availableTable.getModel());
		String[][] currentAvailableTableData = currentAvailableModel.getData();
		String[][] newAvailableTableData = new String[currentAvailableTableData.length][numColumns];

		for (int i = 0; i < currentAvailableTableData.length; i++) {
			for (int j = 0; j < currentAvailableTableData[i].length; j++) {
				if (currentAvailableTableData[i][j] != null && j < numColumns) {
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

		String[] newAvailableColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
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
//					if (j == 0) {
//						newAllocatedTableData[i][j] = "1";
//					} else {
						newAllocatedTableData[i][j] = "0";
//					}
				}
			}
		}

		allocatedTable.setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));
		TableColumnModel allocTCM = allocatedTable.getColumnModel();	
		for (int i = 0; i < newAllocatedTableData[0].length; i++) {
			TableColumn allocTC = allocTCM.getColumn(i);
			allocTC.setCellEditor(new SpinnerEditor());
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
						newMaximumTableData[i][j] = "9";
					} else {
						newMaximumTableData[i][j] = "0";
					}
				}
			}
		}

		maximumTable.setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));/* {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 ? true : false;
			}
		});*/
//		TableColumn tm = maximumTable.getColumnModel().getColumn(0);
//		tm.setCellRenderer(new ColorColumnRenderer(Color.LIGHT_GRAY, Color.GRAY));
		TableColumnModel maxTCM = maximumTable.getColumnModel();	
		for (int i = 0; i < newMaximumTableData[0].length; i++) {
			TableColumn maxTC = maxTCM.getColumn(i);
			maxTC.setCellEditor(new SpinnerEditor());
		}
		
		
		// available table
		/*ResourcesTableModel currentAvailableModel = ((ResourcesTableModel) availableTable.getModel());
		String[][] currentAvailableTableData = currentAvailableModel.getData();
		String[][] newAvailableTableData = new String[numRows][currentAvailableTableData[0].length];

		for (int i = 0; i < currentAvailableTableData.length; i++) {
			for (int j = 0; j < currentAvailableTableData[i].length; j++) {
				if (currentAvailableTableData[i][j] != null && i < numRows) {
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

		availableTable.setModel(new ResourcesTableModel(currentAvailableModel.getColumnNames(), newAvailableTableData));*/

		/*
		 * currentModel = ((ResourcesTableModel) allocatedTable.getModel());
		 * currentTableData = currentModel.getData(); for (int i = 0; i <
		 * currentTableData.length; i++) { for (int j = 0; j <
		 * currentTableData[i].length; j++) {
		 * System.out.print(currentTableData[i][j] + " "); }
		 * System.out.println(); }
		 * 
		 * currentModel = ((ResourcesTableModel) maximumTable.getModel());
		 * currentTableData = currentModel.getData(); for (int i = 0; i <
		 * currentTableData.length; i++) { for (int j = 0; j <
		 * currentTableData[i].length; j++) {
		 * System.out.print(currentTableData[i][j] + " "); }
		 * System.out.println(); }
		 * 
		 * currentModel = ((ResourcesTableModel) availableTable.getModel());
		 * currentTableData = currentModel.getData(); for (int i = 0; i <
		 * currentTableData.length; i++) { for (int j = 0; j <
		 * currentTableData[i].length; j++) {
		 * System.out.print(currentTableData[i][j] + " "); }
		 * System.out.println(); }
		 */

		// System.exit(1);
		resizeTimeTable(numRows);
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
        public SpinnerEditor() {
            super(new JTextField());
            SpinnerModel sm = new SpinnerNumberModel(0, 0, 9, 1);
            spinner = new JSpinner(sm);
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            textField.addFocusListener( new FocusListener() {
                public void focusGained( FocusEvent fe ) {
//                    System.err.println("Got focus");
                    //textField.setSelectionStart(0);
                    //textField.setSelectionEnd(1);
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
//            	System.out.println("value = "value+", value's class = "+value.getClass());
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
//            System.err.println("isCellEditable");
            if ( eo instanceof KeyEvent ) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                //textField.select(1,1);
                //textField.setCaretPosition(1);
                //textField.moveCaretPosition(1);
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
//            System.err.println("Stopping edit");
            try {
                editor.commitEdit();
                spinner.commitEdit();
            } catch ( java.text.ParseException e ) {
                JOptionPane.showMessageDialog(null,
                    "Invalid value, discarding.");
            }
            return super.stopCellEditing();
        }
    }
}