package designer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import components.StringFormatter;

@SuppressWarnings("serial")
public class DesignerPanel extends JLayeredPane implements ActionListener, MouseListener
{
	public static int ComponentCount = 0;
	
	private ArrayList<DesignerComponent> components;
	
	public final Color BASE_REF_COLOR = new Color(0, 0, 255, 40);
	public final Color HORIZONTAL_REF_COLOR = new Color(255, 0, 0, 40);
	public final Color VERTICAL_REF_COLOR = new Color(0, 255, 0, 40);
	public final Color BOTH_REF_COLOR = new Color(255, 255, 0, 40);

	private boolean layoutMode;
	
	private JComponent currentSelection;
	private DesignerComponent currentAnchorDC;
	private JComponent currentAnchor;
	private JComponent currentHRef;
	private JComponent currentVRef;	
	
	private JPopupMenu rightPopup;
    private JMenuItem deleteItem;
    private JMenuItem setHRefItem;
    private JMenuItem setVRefItem;

	//-----------------------------------------------------------------------------------
	
	/**
	 * Basic constructor for a <code>DesignerPanel</code>.
	 */
    public DesignerPanel()
    {
        super();
        
        components = new ArrayList<DesignerComponent>();
        
        this.initComponents();
    }
    
    //-----------------------------------------------------------------------------------
    /**
     * Adds the given <code>DesignerComponent</code> to this <code>DesignerPanel</code>.
     * New components are always added at a default position with a default size. The 
     * location, size and name attributes of the component are automatically set in this method.
     */
    public void addComponent(DesignerComponent comp)
    {
        for (DesignerComponent c : components)
        {
            if (c.getName().equals(comp.getName()))
            {
                return;
            }
        }
        
        comp.getComponent().setLocation(new Point(10, 10));
        comp.getComponent().setName("component" + DesignerPanel.ComponentCount);
        comp.getComponent().setSize(new Dimension(80, 25));
        
        comp.setProperty(DesignerComponent.LocationKey, StringFormatter.format(comp.getComponent().getLocation()));
        comp.setProperty(DesignerComponent.NameKey, comp.getComponent().getName());
        comp.setProperty(DesignerComponent.SizeKey, StringFormatter.format(comp.getComponent().getSize()));
        
        comp.getComponent().addMouseListener(this);
        
        this.add(comp.getComponent(), JLayeredPane.DEFAULT_LAYER);
        components.add(comp);
        
        DesignerPanel.ComponentCount += 1;
    }
    
    /**
     * Removes the <code>DesignerComponent</code> containing the given name from this
     * <code>DesignerPanel</code>.
     * 
     * @param name The name of the <code>DesignerComponent</code> to remove.
     * @return
     */
    public DesignerComponent removeComponent(String name)
    {
        for (DesignerComponent c : components)
        {
            if (c.getName().equals(name))
            {
                components.remove(c);
                DesignerPanel.ComponentCount -= 1;
                return c;
            }
        }
        
        return null;
    }
    
    
    /**
	 * Sets the <code>ApplicationFrame</code> into Layout Mode based on the given boolean value.
	 * Also sets all <code>DesignerComponent</code> objects in this <code>ApplicationFrame</code> to
	 * designer mode.
	 * @param isLayoutMode If <code>true</code> the <code>ApplicationFrame</code> will be set to Layout Mode.
	 * If <code>false</code>, it will be set to Designer Mode.
	 */
	public void setLayoutMode(boolean isLayoutMode)
	{
		layoutMode = isLayoutMode;
		this.clearLayoutSelections();
		this.clearGlassOverlay();
		
		for(DesignerComponent dc : this.getDesignerComponents())
		{
			dc.setLayoutMode(layoutMode);
		}
	}
	
	public void switchLayoutMode()
	{
		this.setLayoutMode(!this.layoutMode);
	}
    /**
     * Gets an <code>ArrayList</code> containing all of the <code>DesignerComponent</code>
     * currently contained by this <code>DesignerPanel</code>
     * 
     * @return The <code>ArrayList</code> of components.
     */
    public ArrayList<DesignerComponent> getDesignerComponents() { return components; }
    
    //-----------------------------------------------------------------------------------
    
    private void initComponents()
    {
        this.setLayout(null);
        
        this.initRightPopup();
    }

	private void initRightPopup()
    {         
         this.deleteItem = new JMenuItem("Delete");
         this.deleteItem.addActionListener(this);
         
         this.setHRefItem = new JMenuItem("Set Horizontal Reference");
         this.setHRefItem.setActionCommand("href");         
         this.setHRefItem.addActionListener(this);
         
         this.setVRefItem = new JMenuItem("Set Vertical Reference");
         this.setVRefItem.setActionCommand("vref");         
         this.setVRefItem.addActionListener(this);
         
         this.rightPopup = new JPopupMenu();
         this.rightPopup.add(deleteItem);
         this.rightPopup.add(new JSeparator());
         this.rightPopup.add(setHRefItem);
         this.rightPopup.add(setVRefItem);
    }
	
	private void clearLayoutSelections()
	{
		currentAnchor = null;
		currentHRef = null;
		currentVRef = null;
	}
	
	private DesignerComponent getDesignerComponent(Component comp)
	{
		for(DesignerComponent dc : this.getDesignerComponents())
		{
			if (dc.getComponent() == comp)
			{
				return dc;
			}
		}
		
		return null;
	}
	
	private void addGlassOverlay(JComponent component, Color color)
	{
		GlassComponent overlay = new GlassComponent(component, color);
		
		this.add(overlay, JLayeredPane.DEFAULT_LAYER);
		this.moveToFront(overlay);
	}
	
	private void clearGlassOverlay()
	{
		for (Component component : this.getComponents())
		{
			if (component instanceof GlassComponent)
			{
				this.remove(component);
				this.repaint();
			}
		}
	}
	
	private void moveAllToLayer(int layer)
	{
		for (Component component : this.getComponents())
		{
			this.setLayer(component, layer);
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	@Override 
	public void actionPerformed(ActionEvent ae)
	{
		// TODO: Complete the actionPerformed method for the DesignerPanel
		String command = ae.getActionCommand();
	}
	
	@Override
	public void mouseClicked(MouseEvent me) 
	{
		JComponent comp = (JComponent)me.getSource();
		
		if (layoutMode && me.getButton() == MouseEvent.BUTTON1)
		{
			this.clearGlassOverlay();
				
			if (currentAnchor == null && (me.isControlDown() || me.isAltDown()))
			{
				JOptionPane.showMessageDialog(null, "Please select a base component before selecting references.");
			}
			
			else if (me.isControlDown() && currentVRef != comp)
			{
				DesignerComponent dc = this.getDesignerComponent(comp);
				currentAnchorDC.setVerticalReference(dc);				
				currentVRef = (dc == null ? null : comp);
			}
			else if (me.isAltDown() && currentHRef != comp)
			{
				DesignerComponent dc = this.getDesignerComponent(comp);
				currentAnchorDC.setHorizontalReference(dc);				
				currentHRef = (dc == null ? null : comp);
			}
			
			else if (comp != currentAnchor)
			{
				if (currentAnchor != null)
				{
					this.clearLayoutSelections();
				}
				
				for(DesignerComponent dc : this.getDesignerComponents())
				{
					if (dc.getComponent() == comp)
					{
						currentAnchorDC = dc;
						currentHRef = dc.getHorizontalReference() == null ? null : dc.getHorizontalReference().getComponent();
						currentVRef = dc.getVerticalReference() == null ? null : dc.getVerticalReference().getComponent();
						break;
					}
				}
				
				currentAnchor = comp;
			}
			
			if (currentAnchor != null)
			{
				this.addGlassOverlay(currentAnchor, this.BASE_REF_COLOR);
			}
			
			if (currentHRef != null && currentHRef == currentVRef)
			{
				this.addGlassOverlay(currentHRef, this.BOTH_REF_COLOR);
			}
			else
			{
				if (currentHRef != null)
				{
					this.addGlassOverlay(currentHRef, this.HORIZONTAL_REF_COLOR);
				}
				
				if (currentVRef != null)
				{
					this.addGlassOverlay(currentVRef, this.VERTICAL_REF_COLOR);
				}
			}		
		}
		
		else if (!layoutMode && me.getButton() == MouseEvent.BUTTON3)
		{
			for(DesignerComponent dc : this.getDesignerComponents())
			{
				if (dc.getComponent() == comp)
				{
					this.setHRefItem.setEnabled(false);
		    		this.setVRefItem.setEnabled(false);
		    		this.rightPopup.show(comp, me.getX(), me.getY());
					break;
				}
			}
		}
		else if(layoutMode && me.getButton() == MouseEvent.BUTTON3)
		{
			for(DesignerComponent dc : this.getDesignerComponents())
			{
				if (dc.getComponent() == comp)
				{
					this.currentSelection = comp;
					this.setHRefItem.setEnabled(true);
		    		this.setVRefItem.setEnabled(true);
		    		this.rightPopup.show(comp, me.getX(), me.getY());
					break;
				}
			}
		}
	}
	
	// UNUSED
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	// UNUSED
	@Override
	public void mouseExited(MouseEvent arg0) {}

	// UNUSED
	@Override
	public void mousePressed(MouseEvent arg0) {}

	// UNUSED
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
