package designer;

import java.awt.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class PropertiesPanel extends JPanel implements PropertyChangeListener, CellEditorListener
{
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private JTable table;
	private DefaultTableModel model;
	private PropertiesCellEditor editor;
	private Object currentSelection;
	private Properties properties;
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * A basic constructor for a <code>PropertiesPanel</code> object.
	 */
	public PropertiesPanel()
	{
		super();
		
		this.initComponents();
	}
	
	/**
	 * Loads the <code>Properties</code> object associated with this panel into the panel's 
	 * table so that it can be viewed and edited. This object is obtained through a
	 * <code>PropertyChangeEvent</code> that this panel listens to. Properties are listed in 
	 * alphabetical order.
	 */
	public void loadProperties()
	{		
		this.clear();
		
		if (properties != null)
		{
			//TODO: Fix this
			ArrayList<String> propertyNameList = new ArrayList<String>(properties.stringPropertyNames());
			Collections.sort(propertyNameList);
			Iterator<String> keys = propertyNameList.iterator();
			while (keys.hasNext())
			{
				String key = keys.next();
				String value = properties.getProperty(key);
				model.addRow(new String[] { key, value });
			}
		}
	}

	/**
	 * Clears the table that is displayed by this <code>PropertiesPanel</code>
	 */
	public void clear()
	{
		table.getSelectionModel().clearSelection();
		editor.cancelCellEditing();
		
		int rowCount = model.getRowCount();
		for (int a = 0 ; a < rowCount ; a++)
		{
			model.removeRow(0);
		}
	}
	
	/**
	 * Returns the object which is currently selected by the Designer. This is the object
	 * whose properties are being displayed by this <code>PropertiesPanel</code>.
	 * @return
	 */
	public Object getCurrentSelection()
	{
		return currentSelection;
	}

	/**
	 * Updates the given property in the <code>Properties</code> object and reloads it 
	 * into the table so that it can be shown. 
	 * <p>
	 * This method fires a <code>PropertyChangeEvent</code> containing the new property information
	 * with the intent that the <code>Object</code> whose properties are being displayed will be 
	 * listening to this <code>PropertiesPanel</code>. That way, there is a two way communication
	 * between the panel and the <code>Object</code> whose properties it displays.
	 * 
	 * @param propName The name of the property to be updated.
	 * @param newValue The new value of the property.
	 */
	public void updateProperty(String propName, String newValue)
	{
		this.properties.setProperty(propName, newValue);
		this.loadProperties();
		this.pcs.firePropertyChange(propName, null, newValue);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent pce) 
	{		
		if (pce.getPropertyName().equals(DesignerComponent.SELECTED_FIELD))
		{
			properties = (Properties)pce.getNewValue();		
			this.loadProperties();
			currentSelection = pce.getSource();
		}
		else if (currentSelection == pce.getSource())
		{
			this.loadProperties();
		}
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener pcl)
    {
    	this.pcs.addPropertyChangeListener(pcl);
    }
    
	@Override
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
    	this.pcs.removePropertyChangeListener(pcl);
    }
    
	/**
	 * Removes all <code>PropertyChangeListener</code> objects from the listener list.
	 */
    public void removeAllPropertyChangeListeners()
    {
    	PropertyChangeListener[] listeners = pcs.getPropertyChangeListeners();
    	for(PropertyChangeListener l : listeners)
    	{
    		this.pcs.removePropertyChangeListener(l);
    	}
    }

    //-----------------------------------------------------------------------------------
    
    private void initComponents()
	{		
		this.setLayout(new BorderLayout());
		
		model = new DefaultTableModel();
		model.addColumn("Property Name");
		model.addColumn("Value");
		
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		editor = new PropertiesCellEditor();
		editor.addCellEditorListener(this);
		
		table.getColumnModel().getColumn(0).setCellEditor(editor);
		table.getColumnModel().getColumn(1).setCellEditor(editor);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(75);
		
		this.add(table.getTableHeader(), BorderLayout.NORTH);
		this.add(table, BorderLayout.CENTER);
	}
    
    //-----------------------------------------------------------------------------------	

	@Override
	public void editingStopped(ChangeEvent e) 
	{
		String[] rowData = editor.getRowData();
		this.updateProperty(rowData[0], rowData[1]);
	}
	
	@Override
	public void editingCanceled(ChangeEvent e){}
}
