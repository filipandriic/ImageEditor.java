package editor;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;

import javax.swing.*;

public class SelectionPopUp extends JFrame{
	public SelectionPopUp() {
		super();
		this.setLayout(new GridLayout(4, 1));
		this.add(selName);
		this.add(name);
		this.add(ok, BorderLayout.CENTER);
		group.add(active);
		group.add(inactive);
		panel.add(active);
		panel.add(inactive);
		this.add(panel);
		
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			
			Selection s = new Selection(name.getText(), active.isSelected());
			
			for (Rectangle r : Menu.currentRectangles) {
				s.addRectangle(r);
			}
			
			Menu.currentRectangles.clear();
			
			Menu.image.repaint();
			//new RectanglePopUp(s);
			Menu.image.selections.add(s);
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JLabel selName = new JLabel("Insert selection name: ");
	static JTextField name = new JTextField();
	JButton ok = new JButton("OK");
	JCheckBox active = new JCheckBox("Active", true);
	JCheckBox inactive = new JCheckBox("Not active", false);
	ButtonGroup group = new ButtonGroup();
	JPanel panel = new JPanel(new GridLayout(1, 2));
}
