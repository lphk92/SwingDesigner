package designer;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class PropertiesCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private JTextField editingComponent;
	private String[] rowData;
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * A basic constructor for a <code>PropertiesCellEditor</code> object.
	 */
	public PropertiesCellEditor()
	{
		editingComponent = new JTextField();
		editingComponent.setBackground(new Color(221, 238, 238));
		editingComponent.setBorder(BorderFactory.createEmptyBorder());
		
		rowData = new String[2];
	}
	//-----------------------------------------------------------------------------------
	/**
	 * Returns the value of the editing component. Uses the <code>getText</code> method of the
	 * <code>JTextField</code> that is used in editing the cell.
	 */
	@Override
	public Object getCellEditorValue() 
	{
		rowData[1] = editingComponent.getText();
		return editingComponent.getText();
	}

	/**
	 * Returns the <code>Component</code> used to edit the given cell. Returns <code>null</code> if the
	 * column equals <code>0</code>, because the left column is not meant to be edited. Otherwise,
	 * returns a <code>JTextField</code> object that is used for editing.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	{
		rowData[0] = table.getModel().getValueAt(row, 0).toString();
		
		if (column == 0 || !isSelected)
		{
			return null;
		}
		
		editingComponent.setText(value.toString());
		return editingComponent;
	}

	/**
	 * Returns an array that contains information about the current row being edited.
	 * The first element of the array is the name of the property being edited. The second elements 
	 * is the edited value of that property.
	 * @return
	 */
	public String[] getRowData()
	{
		return rowData;
	}
}
