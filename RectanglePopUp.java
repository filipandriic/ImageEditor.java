package editor;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;

import javax.swing.*;

public class RectanglePopUp extends JFrame{
	public RectanglePopUp(Selection s) {
		super();
		this.s = s;
		this.setLayout(new GridLayout(2, 1));
		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(x);
		panel.add(xW);
		panel.add(y);
		panel.add(yW);
		panel.add(width);
		panel.add(widthW);
		panel.add(height);
		panel.add(heightW);
		this.add(panel);
		this.add(ok, BorderLayout.CENTER);
		
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			int x = Integer.parseInt(xW.getText());
			int y = Integer.parseInt(yW.getText());
			int w = Integer.parseInt(widthW.getText());
			int h = Integer.parseInt(heightW.getText());
			
			if (x + w < Menu.image.width || y + h < Menu.image.height) {
				Rectangle r = new Rectangle(x, y, w, h);
				s.addRectangle(r);
			}
			else {
				System.out.println("Neuspesno napravljen pravouganik.");
			}
			
			if (s.active) {
				Menu.image.repaint();
			}
			new RectanglePopUp(s);
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	Selection s;
	JLabel x = new JLabel("X coordinate: ");
	JLabel y = new JLabel("Y coordinate: ");
	JLabel width = new JLabel("Width: ");
	JLabel height = new JLabel("Height: ");
	JTextField xW = new JTextField();
	JTextField yW = new JTextField();
	JTextField widthW = new JTextField();
	JTextField heightW = new JTextField();
	JButton ok = new JButton("OK");
}
