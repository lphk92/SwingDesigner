package components;

import java.util.*;
import javax.swing.*;
import designer.DesignerComponent;

public class ButtonComponent extends DesignerComponent
{	
	public static final String TextKey = "Text";
	public static final String FontKey = "Font";
	public static final String ForegroundKey = "Text Color";
	public static final String BackgroundKey = "Background Color";
	public static final String ToolTipTextKey = "ToolTip Text";
	public static final String EnabledKey = "Enabled";
	
	private Properties properties;
	private JButton button;
	
	public ButtonComponent(JButton b)
	{
		super(b);
		button = b;
		
		properties = new Properties();
		
		this.setProperty(LocationKey, StringFormatter.format(button.getLocation()));
		this.setProperty(NameKey, StringFormatter.format(button.getName()));
		this.setProperty(SizeKey, StringFormatter.format(button.getSize()));
		this.setProperty(TextKey, StringFormatter.format(button.getText()));
		this.setProperty(FontKey, StringFormatter.format(button.getFont()));
		this.setProperty(ForegroundKey, StringFormatter.format(button.getForeground()));
		this.setProperty(BackgroundKey, StringFormatter.format(button.getBackground()));
		this.setProperty(EnabledKey, StringFormatter.format(button.isEnabled()));
		this.setProperty(ToolTipTextKey, StringFormatter.format(button.getToolTipText()));
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
			button.setLocation(StringFormatter.deformatPoint(value));
			
		if (propertyName.equals(NameKey))
			button.setName(value);
		
		if (propertyName.equals(SizeKey))
			button.setSize(StringFormatter.deformatDimension(value));
		
		if (propertyName.equals(TextKey))
			button.setText(value);
		
		if (propertyName.equals(EnabledKey))
			button.setEnabled(StringFormatter.deformatBoolean(value));
		
		if (propertyName.equals(BackgroundKey))
			button.setBackground(StringFormatter.deformatColor(value) == null ? button.getBackground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ForegroundKey))
			button.setForeground(StringFormatter.deformatColor(value) == null ? button.getForeground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ToolTipTextKey))
			button.setToolTipText(value);
		
	}
}
