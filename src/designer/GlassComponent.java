package designer;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GlassComponent extends JPanel
{	
	public GlassComponent(Component component, Color color)
	{
		super();		

		this.setBackground(color);
		this.setLocation(component.getLocation());
		this.setSize(component.getSize());
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
