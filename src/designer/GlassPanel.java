package designer;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GlassPanel extends JPanel
{		
	/**
	 * A basic constructor for a <code>GlassPanel</code> object.
	 */
	public GlassPanel()
	{
		super();
		
		this.setLayout(null);		
		this.setOpaque(false);
	}
	
	//-----------------------------------------------------------------------------------
	/**
	 * Clears this <code>GlassPanel</code>. This is done by removing all of the <code>GlassComponent</code>
	 * objects contained on the panel and then calling <code>repaint</code>.
	 */
	public void clear()
	{
		this.removeAll();
		this.repaint();
	}
	
	/**
	 * Adds the given <code>GlassComponent</code> to this <code>GlassPanel</code>.
	 * After adding, this method calls <code>repaint</code> to ensure that the necessary graphical
	 * updates are made after adding the new component.
	 * 
	 * @param gc The <code>GlassComponent</code> object to add.
	 */
	public void addGlassComponent(GlassComponent gc)
	{
		this.add(gc);
		this.repaint();
		//System.out.println("\nGlassComponentCount = " + this.getComponentCount());
	}
	
	protected void paintComponent(Graphics g)
	{
		g.setColor(Color.green);
		g.fillRect(0, 0, 200, 200);
	}
}
