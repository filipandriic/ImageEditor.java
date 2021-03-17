package editor;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;

import javax.swing.*;

public class SelectionDeletePopUp extends JFrame{
	public SelectionDeletePopUp() {
		super();
		this.setLayout(new GridLayout(3, 1));
		this.add(selName);
		this.add(name);
		this.add(ok, BorderLayout.CENTER);
		
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			
			Selection toDelete = null;
		f:	for (Selection s : Menu.image.selections) {
				if (s.name.equals(name.getText())) {
					toDelete = s;
					break f;
				}
			}
			Menu.image.selections.remove(toDelete);
			
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JLabel selName = new JLabel("Insert selection name: ");
	JTextField name = new JTextField();
	JButton ok = new JButton("OK");

}
