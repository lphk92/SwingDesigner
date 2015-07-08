package components;

import java.awt.*;
import java.util.*;

public class StringFormatter 
{
	public static String format(Object obj)
	{
		//System.out.println(obj.getClass());
		if (obj == null)
		{
			return "";
		}		
		else if (obj instanceof Point)
		{
			Point p = (Point)obj;
			return p.x + ", " + p.y;
		}
		else if (obj instanceof Dimension)
		{
			Dimension d = (Dimension)obj;
			return (int)d.getWidth() + " x " + (int)d.getHeight();
		}
		else if (obj instanceof Color)
		{
			Color col = (Color)obj;
			return col.getRed() + ", " + col.getGreen() + ", " + col.getBlue();
		}
		else if (obj instanceof Font)
		{
			Font f = (Font)obj;
			return f.getFamily() + ", " + f.getSize() + "pt";
		}
		else
		{
			return obj.toString();
		}
	}
	
	public static Color deformatColor(String str)
	{
		try
		{
			StringTokenizer token = new StringTokenizer(str, ", ");
			int r = Integer.parseInt(token.nextToken());
			int g = Integer.parseInt(token.nextToken());
			int b = Integer.parseInt(token.nextToken());
			return new Color(r, g, b);
		}
		catch (Exception ex)
		{ System.out.println("\n[Error Found in deformatColor]\n"); ex.printStackTrace(); }
		
		return null;
	}
	
	public static boolean deformatBoolean(String str)
	{
		if (str.equals(Boolean.toString(true)))
				return true;
		
		return false;
	}
	
	public static Point deformatPoint(String str)
	{
		try
		{
			StringTokenizer token = new StringTokenizer(str, ", ");
			int x = Integer.parseInt(token.nextToken());
			int y = Integer.parseInt(token.nextToken());
			
			return new Point(x, y);
		}
		catch (Exception ex)
		{ System.out.println("\n[Error Found in deformatPoint]\n"); ex.printStackTrace(); }
		
		return null;
	}
	
	public static Dimension deformatDimension(String str)
	{
		try
		{
			StringTokenizer token = new StringTokenizer(str, "x ");
			int x = Integer.parseInt(token.nextToken());
			int y = Integer.parseInt(token.nextToken());
			
			return new Dimension(x, y);
		}
		catch (Exception ex)
		{ System.out.println("\n[Error Found in deformatDimension]\n"); ex.printStackTrace(); }
		
		return null;
	}
}
