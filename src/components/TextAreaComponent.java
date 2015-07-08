package components;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import designer.DesignerComponent;

public class TextAreaComponent extends DesignerComponent implements KeyListener
{	
	public static final String TextKey = "Text";
	public static final String FontKey = "Font";
	public static final String ForegroundKey = "Text Color";
	public static final String BackgroundKey = "Background Color";
	public static final String ToolTipTextKey = "ToolTip Text";
	public static final String EnabledKey = "Enabled";
	
	private Properties properties;
	private JTextArea area;
	
	public TextAreaComponent(JTextArea b)
	{
		super(b);
		area = b;
		area.addKeyListener(this);
		
		properties = new Properties();
		
		this.setProperty(LocationKey, StringFormatter.format(area.getLocation()));
		this.setProperty(NameKey, StringFormatter.format(area.getName()));
		this.setProperty(SizeKey, StringFormatter.format(area.getSize()));
		this.setProperty(TextKey, StringFormatter.format(area.getText()));
		this.setProperty(FontKey, StringFormatter.format(area.getFont()));
		this.setProperty(ForegroundKey, StringFormatter.format(area.getForeground()));
		this.setProperty(BackgroundKey, StringFormatter.format(area.getBackground()));
		this.setProperty(EnabledKey, StringFormatter.format(area.isEnabled()));
		this.setProperty(ToolTipTextKey, StringFormatter.format(area.getToolTipText()));
	}
	
	@Override
	public Properties getProperties()
	{		
		return properties;
	}
	
	@Override
	public void handlePropertyChange(String propertyName, String value)
	{
		if (propertyName.equals(LocationKey))
			area.setLocation(StringFormatter.deformatPoint(value));
			
		if (propertyName.equals(NameKey))
			area.setName(value);
		
		if (propertyName.equals(SizeKey))
			area.setSize(StringFormatter.deformatDimension(value));
		
		if (propertyName.equals(TextKey))
			area.setText(value);
		
		if (propertyName.equals(EnabledKey))
			area.setEnabled(StringFormatter.deformatBoolean(value));
		
		if (propertyName.equals(BackgroundKey))
			area.setBackground(StringFormatter.deformatColor(value) == null ? area.getBackground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ForegroundKey))
			area.setForeground(StringFormatter.deformatColor(value) == null ? area.getForeground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ToolTipTextKey))
			area.setToolTipText(value);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		this.setProperty(TextKey, area.getText());
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
