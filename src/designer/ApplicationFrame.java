package designer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import components.*;

@SuppressWarnings("serial")
public class ApplicationFrame extends JFrame implements ActionListener, ComponentListener
{
	private DesignerPanel designerPanel;
	private PropertiesPanel propertiesPanel;
	private JToolBar toolbar;
	private SpringExporter exporter;
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * Basic constructor for <code>ApplicationFrame</code>.
	 */
	public ApplicationFrame()
	{
		super("Swing Designer");
		
		exporter = new SpringExporter();
		
		this.initComponents();
	}
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * Performs all operations necessary in order to run the <code>ApplicationFrame</code> and runs it.
	 */
	public void run()
	{
		this.setVisible(true);
	}
	
	/**
	 * Adds the given <code>DesignerComponent</code> to this <code>ApplicationFrame</code>.
	 * @param newComp The <code>DesignerComponent</code> to add.
	 */
	public void addDesignerComponent(DesignerComponent newComp)
	{
		newComp.getComponent().addComponentListener(this);
		newComp.addPropertyChangeListener(propertiesPanel);	
		propertiesPanel.addPropertyChangeListener(newComp);
		designerPanel.addComponent(newComp);
	}
	
	//-----------------------------------------------------------------------------------	

	private void initComponents()
	{
		this.setLayout(new BorderLayout());
		
		designerPanel = new DesignerPanel();
		designerPanel.setBorder(BorderFactory.createEtchedBorder());
		
		propertiesPanel = new PropertiesPanel();
		propertiesPanel.setBorder(BorderFactory.createEtchedBorder());
		
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, designerPanel, propertiesPanel);
		splitter.setDividerLocation(400);
		splitter.setDividerSize(5);
		splitter.setResizeWeight(1.0);
		this.add(splitter, BorderLayout.CENTER);
		
		JButton button = new JButton("Button");
		button.addActionListener(this);
		
		JButton label = new JButton("Label");
		label.addActionListener(this);
		
		JButton textArea = new JButton("TextArea");
		textArea.addActionListener(this);
		
		JButton textField = new JButton("TextField");
		textField.addActionListener(this);
		
		JToggleButton layoutMode = new JToggleButton("Layout Mode");
		layoutMode.addActionListener(this);
		
		JButton export = new JButton("Export");
		export.addActionListener(this);
		
		toolbar = new JToolBar();		
		toolbar.add(button);
		toolbar.add(label);
		toolbar.add(textArea);
		toolbar.add(textField);
		toolbar.add(layoutMode);
		toolbar.add(export);
		this.add(toolbar, BorderLayout.NORTH);		
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(650, 500);
	}
	
	//-----------------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Export"))
		{
			boolean success = exporter.promptDirectory();
			if (success)
			{
				exporter.export(designerPanel.getDesignerComponents());
			}
		}		
		else if (command.equals("Layout Mode"))
		{
			// TODO: Implement the logic for clicking the "Layout Mode" button
			this.designerPanel.switchLayoutMode();
		}		
		else if (command.equals("Button"))
		{
			ButtonComponent newComp = new ButtonComponent(new JButton("Button"));
			this.addDesignerComponent(newComp);
		}
		else if (command.equals("Label"))
		{
			LabelComponent newComp = new LabelComponent(new JLabel("Label"));
			this.addDesignerComponent(newComp);
		}
		else if (command.equals("TextArea"))
		{
			TextAreaComponent newComp = new TextAreaComponent(new JTextArea());
			this.addDesignerComponent(newComp);
		}
		else if (command.equals("TextField"))
		{
			TextFieldComponent newComp = new TextFieldComponent(new JTextField());
			this.addDesignerComponent(newComp);
		}
	}
	
	@Override
	public void componentHidden(ComponentEvent ce) 
	{
		designerPanel.removeComponent(ce.getComponent().getName());
		propertiesPanel.clear();
	}

	//-----------------------------------------------------------------------------------
	
	// UNUSED
	@Override
	public void componentMoved(ComponentEvent arg0) {}

	// UNUSED
	@Override
	public void componentResized(ComponentEvent arg0) {}

	// UNUSED
	@Override
	public void componentShown(ComponentEvent arg0) {}
}
