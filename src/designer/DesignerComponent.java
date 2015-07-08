package designer;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;

import components.StringFormatter;

public abstract class DesignerComponent implements MouseListener, MouseMotionListener, PropertyChangeListener
{
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String SELECTED_FIELD = "SELECTED";
	
	public static final String LocationKey = "Location";
	public static final String NameKey = "Name";
	public static final String SizeKey = "Size";
	
    protected JComponent component;
    
    private boolean moving;
    private boolean resizing;
    
    private int lastMouseX;
    private int lastMouseY;
    
    private JPopupMenu locationPopup;
    private JMenuItem locationItem;
    
    private DesignerComponent hRef;
    private DesignerComponent vRef;
    
    private boolean layout;
    
    //-----------------------------------------------------------------------------------
    
    /**
     * Creates a <code>DesignerComponent</code> using the given <code>JComponent</code> as the
     * component to display when designing.
     * 
     * @param comp The <code>JComponent</code> to display.
     */
    public DesignerComponent(JComponent comp)
    {
        moving = false;
        resizing = false;
        layout = false;
        
        this.initComponents(comp);
    }
    
    //-----------------------------------------------------------------------------------
    
    /**
     * Gets the <code>boolean</code> indicating if this <code>DesignerComponent</code> is
     * in Layout Mode.
     * 
     * @return Returns <code>true</code> if this <code>DesignerComponent</code> is set to
     * Layout Mode, <code>false</code> if it is set to Designer Mode.
     */
    public boolean isLayoutMode()
    {
    	return layout;
    }
    
    /**
     * Sets the mode of this <code>DesignerComponent</code>.
     * 
     * @param layoutMode If <code>true</code> this <code>DesignerComponent</code> will be set to
     * Layout Mode. If <code>false</code> it will be set to Designer Mode.
     */
    public void setLayoutMode(boolean layoutMode)
    {
    	layout = layoutMode;
    	component.setEnabled(!layout);
    	component.setFocusable(!layout);
    	moving = false;
    	resizing = false;
    }
    
    /**
     * Sets the horizontal reference for this <code>DesignerComponent</code>. This reference
     * will be used when creating the constraints for the <code>SpringLayout</code> that has 
     * been designed.
     * 
     * @param ref The <code>DesignerComponent</code> acting as the horizontal reference.
     */
    public void setHorizontalReference(DesignerComponent ref) {	hRef = ref; }
    
    /**
     * Sets the vertical reference for this <code>DesignerComponent</code>. This reference
     * will be used when creating the constraints for the <code>SpringLayout</code> that has 
     * been designed.
     * 
     * @param ref The <code>DesignerComponent</code> acting as the vertical reference.
     */
    public void setVerticalReference(DesignerComponent ref) { vRef = ref; }
    
    /**
     * Gets the horizontal reference for this <code>DesignerComponent</code>
     * 
     * @return The horizontal reference.
     */
    public DesignerComponent getHorizontalReference() {	return hRef; }
    
    /**
     * Gets the vertical reference for this <code>DesignerComponent</code>
     * 
     * @return The vertical reference.
     */
    public DesignerComponent getVerticalReference() { return vRef; }
    
    /**
     * Gets the <code>JComponent</code> associated with this <code>DesignerComponent</code>.
     * This <code>JComponent</code> represents what will be displayed in Designer Mode, as well
     * as what <codes>Class</code> will be used when generating the <code>SpringLayout</code>.
     * 
     * @return The <code>JComponent</code> used by this <code>DesignerComponent</code>.
     */
    public JComponent getComponent() { return component; }
    
    /**
     * A quick shortcut method which implements the <code>getName</code> method of the
     * <code>JComponent</code> associated with this <code>DesignerComponent</code>. 
     * Used for convenience.
     * 
     * @return The name of the <code>JComponent</code>.
     */
    public String getName() { return component.getName(); }

    /**
     * Adds the given <code>PropertyChangeListener</code> to the listener list.
     * A <code>PropertyChangeEvent</code> will be fired under one of two conditions.
     * 
     * The first is if a property in the <code>Properties</code> associated with this 
     * <code>DesignerComponent</code> is changed. In this case the <code>PropertyChangeEvent</code> 
     * will contain the name of the changed property, as well as its old and new values. The intent is that
     * a <code>PropertiesPanel</code> object will be listening for this event and update its displayed
     * properties accordingly.
     * <p>
     * The second is if the <code>JComponent</code> associated with this <code>DesignerComponent</code>
     * becomes selected while the in Design Mode. In this case, the <code>PropertyChangeEvent</code>
     * will contain the value of <code>SELECTED_FIELD</code> as the <code>propertyName</code> and the
     * <code>Properties</code> object associated with this <code>DesignerComopnent</code> as the 
     * <code>newValue</code>. The intent is that a <code>PropertiesPanel</code> object will be listening
     * for this event and load the <code>Properties</code> when the <code>JComponent</code>
     * becomes selected.
     * 
     * @param pcl The <code>PropertyChangeListener</code> to add. This listener should be a 
     * <code>PropertiesPanel</code> object.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) 
    {
    	this.pcs.addPropertyChangeListener(pcl);
    }
    
    /**
     * Removes the given <code>PropertyChangeListener</code> from this listener list.
     * @param pcl
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl)
    {
    	this.pcs.removePropertyChangeListener(pcl);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent pce)
    {
    	if (this == ((PropertiesPanel)pce.getSource()).getCurrentSelection())
    	{
    		this.handlePropertyChange(pce.getPropertyName(), pce.getNewValue().toString());
    	}
    }
    
    /**
     * Sets the given property to a new value using the <code>Properties</code> object associated
     * with this <code>DesignerComponent</code>. Using this method will fire a
     * <code>PropertyChangeEvent</code> containing information about the changed property.
     * 
     * @param propertyName The name of the property to change. This value should be accessed using
     * one of the <code>static</code> fields associated with a specific <code>DesignerComopnent</code>.
     * @param value The new value of the property.
     */
    public void setProperty(String propertyName, String value)
    {
    	Properties props = this.getProperties();
    	String oldValue = props.getProperty(propertyName);
    	props.setProperty(propertyName, value);
    	
    	this.pcs.firePropertyChange(propertyName, oldValue, value);
    }
    
    @Override
    public String toString()
    {
    	return component.getName();
    }
    
    //-----------------------------------------------------------------------------------
    
    private void initComponents(JComponent comp)
    {
    	component = comp;
        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        component.setName("");
        
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        
        this.initPopups();
    }
    
    private void initPopups()
    {
    	locationItem = new JMenuItem();
        locationItem.setEnabled(false);
        locationItem.setFont(new Font("Arial", Font.PLAIN, 8));
        
        locationPopup = new JPopupMenu();
        locationPopup.setFocusable(false);
        locationPopup.add(locationItem);
    }
    
    //-----------------------------------------------------------------------------------
    
    public void mouseClicked(MouseEvent me)
    {    	
    	if (!layout)
    	{
	    	if (me.getButton() == MouseEvent.BUTTON1 && !locationItem.getText().equals(""))
	    	{
	    		locationPopup.show(component, 0, -1 * locationPopup.getHeight());    		
	    	}
    	}
    }
    
    public void mousePressed(MouseEvent me)
    {
    	if (!layout)
    	{
	        ((JLayeredPane)component.getParent()).setLayer(component, JLayeredPane.DRAG_LAYER);
	        
	        this.pcs.firePropertyChange(DesignerComponent.SELECTED_FIELD, null, this.getProperties());
	        
	        if (component.getCursor().getType() == Cursor.DEFAULT_CURSOR)
	        {
	        	moving = true;
	        	
	        	lastMouseX = me.getX();
	            lastMouseY = me.getY();
	            
	        	component.setCursor(new Cursor(Cursor.MOVE_CURSOR));
	        }
	        else
	        {
	        	resizing = true;
	        	
	        	lastMouseX = me.getX();
	            lastMouseY = me.getY();
	        }
    	}
    }
    
    public void mouseReleased(MouseEvent me)
    {
    	if (!layout)
    	{
	        moving = false;
	        resizing = false;
	        
	        ((JLayeredPane)component.getParent()).setLayer(component, JLayeredPane.DEFAULT_LAYER);
	        ((JLayeredPane)component.getParent()).moveToFront(component);
	        
	        component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	}
    }
    
    public void mouseEntered(MouseEvent me)
    {}
    
    public void mouseExited(MouseEvent me)
    {}
    
    public void mouseDragged(MouseEvent me)
    {
        if (moving)
        {            
            Point currLoc = component.getLocation();
            component.setLocation(new Point(currLoc.x + me.getX() - lastMouseX, currLoc.y + me.getY() - lastMouseY));
            locationItem.setText(component.getLocation().x+ ", " + component.getLocation().y);
            locationPopup.show(component, 0, -1 * locationPopup.getHeight());    
            
            this.setProperty(LocationKey, StringFormatter.format(component.getLocation()));
        }
        else if (resizing)
        {        	
        	int dx = me.getX() - lastMouseX;
        	int dy = me.getY() - lastMouseY;
        	
        	int resizeType = component.getCursor().getType();
        	
        	if (resizeType == Cursor.E_RESIZE_CURSOR)
        	{
        		component.setSize(component.getWidth() + dx, component.getHeight());
        	}
        	else if (resizeType == Cursor.S_RESIZE_CURSOR)
        	{
        		component.setSize(component.getWidth(), component.getHeight() + dy);
        	}
        	else if (resizeType == Cursor.SE_RESIZE_CURSOR)
        	{
        		component.setSize(component.getWidth() + dx, component.getHeight() + dy);
        	}
        	
        	locationItem.setText(component.getWidth() + " x " + component.getHeight());
            locationPopup.show(component, 0, -1 * locationPopup.getHeight());  
        	
        	lastMouseX = me.getX();
        	lastMouseY = me.getY();
        	
        	this.setProperty(SizeKey, StringFormatter.format(component.getSize()));
        }
    }
    
    public void mouseMoved(MouseEvent me)
    {
    	if (me.getX() == component.getWidth() - 1 && me.getY() == component.getHeight() - 1)
    	{
    		component.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
    	}
    	else if (me.getX() == component.getWidth() - 1)
    	{
    		component.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
    	}
    	else if (me.getY() == component.getHeight() - 1)
    	{
    		component.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
    	}
    	
    	else
    	{
    		component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	}
    }    

    //-----------------------------------------------------------------------------------
    
    /**
     * Gets the <code>Properties</code> object associated with this <code>DesignerComponent</code>.
     * Difference <code>JComponent</code> objects will have different properties associated with them,
     * so this method should return varying properties based on what kind of <code>JComponent</code>
     * is represented by the <code>DesignerComponent</code>.
     */
    public abstract Properties getProperties();
    
    /**
     * Handles the necessary steps of changing a specific property. This method is called by the
     * <code>propertyChange</code> method which is called whenever a <code>PropertyChangeEvent</code> 
     * is fired.
     * 
     * @param propertyName The property name which is contained in the <code>PropertyChangeEvent</code>
     * that was fired.
     * @param value The <code>newValue</code> contained within the <code>PropertyChangEvent</code>.
     */
    protected abstract void handlePropertyChange(String propertyName, String value);
    
}