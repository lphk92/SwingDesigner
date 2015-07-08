package components;

import java.util.*;

import javax.swing.*;

import designer.DesignerComponent;

public class LabelComponent extends DesignerComponent
{	
	public static final String TextKey = "Text";
	public static final String FontKey = "Font";
	public static final String ForegroundKey = "Text Color";
	public static final String BackgroundKey = "Background Color";
	public static final String ToolTipTextKey = "ToolTip Text";
	public static final String EnabledKey = "Enabled";
	
	private Properties properties;
	private JLabel label;
	
	public LabelComponent(JLabel b)
	{
		super(b);
		label = b;
		
		properties = new Properties();
		
		this.setProperty(LocationKey, StringFormatter.format(label.getLocation()));
		this.setProperty(NameKey, StringFormatter.format(label.getName()));
		this.setProperty(SizeKey, StringFormatter.format(label.getSize()));
		this.setProperty(TextKey, StringFormatter.format(label.getText()));
		this.setProperty(FontKey, StringFormatter.format(label.getFont()));
		this.setProperty(ForegroundKey, StringFormatter.format(label.getForeground()));
		this.setProperty(BackgroundKey, StringFormatter.format(label.getBackground()));
		this.setProperty(EnabledKey, StringFormatter.format(label.isEnabled()));
		this.setProperty(ToolTipTextKey, StringFormatter.format(label.getToolTipText()));
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
			label.setLocation(StringFormatter.deformatPoint(value));
			
		if (propertyName.equals(NameKey))
			label.setName(value);
		
		if (propertyName.equals(SizeKey))
			label.setSize(StringFormatter.deformatDimension(value));
		
		if (propertyName.equals(TextKey))
			label.setText(value);
		
		if (propertyName.equals(EnabledKey))
			label.setEnabled(StringFormatter.deformatBoolean(value));
		
		if (propertyName.equals(BackgroundKey))
			label.setBackground(StringFormatter.deformatColor(value) == null ? label.getBackground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ForegroundKey))
			label.setForeground(StringFormatter.deformatColor(value) == null ? label.getForeground() : StringFormatter.deformatColor(value));
	
		if (propertyName.equals(ToolTipTextKey))
			label.setToolTipText(value);
		
	}
}
