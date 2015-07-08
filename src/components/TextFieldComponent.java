package components;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import designer.DesignerComponent;

public class TextFieldComponent extends DesignerComponent implements KeyListener
{	
	public static final String TextKey = "Text";
	public static final String FontKey = "Font";
	public static final String ForegroundKey = "Text Color";
	public static final String BackgroundKey = "Background Color";
	public static final String ToolTipTextKey = "ToolTip Text";
	public static final String EnabledKey = "Enabled";
	
	private Properties properties;
	private JTextField field;
	
	public TextFieldComponent(JTextField b)
	{
		super(b);
		field = b;
		field.addKeyListener(this);
		
		properties = new Properties();
		
		this.setProperty(LocationKey, StringFormatter.format(field.getLocation()));
		this.setProperty(NameKey, StringFormatter.format(field.getName()));
		this.setProperty(SizeKey, StringFormatter.format(field.getSize()));
		this.setProperty(TextKey, StringFormatter.format(field.getText()));
		this.setProperty(FontKey, StringFormatter.format(field.getFont()));
		this.setProperty(ForegroundKey, StringFormatter.format(field.getForeground()));
		this.setProperty(BackgroundKey, StringFormatter.format(field.getBackground()));
		this.setProperty(EnabledKey, StringFormatter.format(field.isEnabled()));
		this.setProperty(ToolTipTextKey, StringFormatter.format(field.getToolTipText()));
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
			field.setLocation(StringFormatter.deformatPoint(value));
			
		if (propertyName.equals(NameKey))
			field.setName(value);
		
		if (propertyName.equals(SizeKey))
			field.setSize(StringFormatter.deformatDimension(value));
		
		if (propertyName.equals(TextKey))
			field.setText(value);
		
		if (propertyName.equals(EnabledKey))
			field.setEnabled(StringFormatter.deformatBoolean(value));
		
		if (propertyName.equals(BackgroundKey))
			field.setBackground(StringFormatter.deformatColor(value) == null ? field.getBackground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ForegroundKey))
			field.setForeground(StringFormatter.deformatColor(value) == null ? field.getForeground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ToolTipTextKey))
			field.setToolTipText(value);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		this.setProperty(TextKey, field.getText());
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	
}
