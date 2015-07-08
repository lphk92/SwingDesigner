package designer;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class SpringExporter 
{
	private File loc;
	private ArrayList<DesignerComponent> components;
	
	public SpringExporter()
	{
		loc = null;
		components = new ArrayList<DesignerComponent>();
	}
	
	public boolean promptDirectory()
	{
		try
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = chooser.showOpenDialog(null);
			
			if (result == JFileChooser.APPROVE_OPTION)
			{
				loc = chooser.getSelectedFile();
				
				if (!loc.exists())
				{
					loc.createNewFile();
				}
				
				return true;
			}
		}
		catch (Exception ex) { ex.printStackTrace(); }
		
		return false;
	}
	
	public boolean export(ArrayList<DesignerComponent> comps)
	{
		components = comps;
		
		if (loc == null)
		{
			return false;
		}		
		else
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("import java.util.*;\n");
			builder.append("import javax.swing.*;\n\n");
			
			builder.append("public class " + loc.getName() + " extends JPanel\n{\n");
			
			for (DesignerComponent comp : components)
			{
				builder.append("\tprivate " + comp.getComponent().getClass().getName() + " " + comp.getName() + ";\n");
			}
			
			builder.append("\n\tpublic void initComponents()\n\t{\n");
			
			builder.append("\t\tSpringLayout sLayout = new SpringLayout();\n");
			builder.append("\t\tthis.setLayout(sLayout);\n");
			
			for (DesignerComponent comp : components)
			{
				builder.append("\n\t\t" + comp.getName() + " = new " + comp.getComponent().getClass().getName() + "();\n");
	
				// TODO: Find a good way to set properties
				/* Properties props = comp.getProperties();
				Enumeration<Object> keys = props.keys();
				while (keys.hasMoreElements())
				{
					String key = keys.nextElement().toString();
					String prop = props.getProperty(key);
					
					builder.append("\t\t" + comp.getName() + ".set" + key + "(\"" + prop + "\");\n");
				}
				*/
				if (comp.getHorizontalReference() != null)
				{
					int dist = comp.getComponent().getX() - comp.getHorizontalReference().getComponent().getX();
					
					builder.append("\t\tsLayout.putConstraint(SpringLayout.WEST, " + comp.getName() + ", ");
					builder.append(dist + ", SpringLayout.WEST, " + comp.getHorizontalReference().getName() + ");\n");
				}
				else
				{
					builder.append("\t\tsLayout.putConstraint(SpringLayout.WEST, " + comp.getName() + ", ");
					builder.append(comp.getComponent().getX() + ", SpringLayout.WEST, this);\n");
				}
				
				if (comp.getVerticalReference() != null)
				{
					int dist = comp.getComponent().getY() - comp.getVerticalReference().getComponent().getY();
					
					builder.append("\t\tsLayout.putConstraint(SpringLayout.NORTH, " + comp.getName() + ", ");
					builder.append(dist + ", SpringLayout.NORTH, " + comp.getVerticalReference().getName() + ");\n");
				}
				else
				{
					builder.append("\t\tsLayout.putConstraint(SpringLayout.NORTH, " + comp.getName() + ", ");
					builder.append(comp.getComponent().getY() + ", SpringLayout.NORTH, this);\n");
				}
			}
			
			builder.append("\n\t}\n");
			builder.append("\n}");
			
			try
			{
				PrintWriter writer = new PrintWriter(new FileWriter(this.loc));
				writer.println(builder.toString());
				writer.close();
				
				JOptionPane.showMessageDialog(null, "Export Successful");
			}
			catch (Exception ex) { ex.printStackTrace(); return false; }
			
			return true;
		}		
	}
}
