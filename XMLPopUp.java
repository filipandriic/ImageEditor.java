package editor;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;

import javax.swing.*;

public class XMLPopUp extends JFrame{
	public XMLPopUp() {
		super();
		
		this.setLayout(new GridLayout(3, 1));
		this.add(label);
		this.add(text);
		this.add(ok);
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			if (Menu.project) {
				if (Menu.toExport) {
					XMLFormatter.exportProject(text.getText());
					Menu.toExport = false;
				} else {
					XMLFormatter.importProject(text.getText());
				}
			} else {
				if (Menu.toExport) {
					XMLFormatter.exportOperations(text.getText());
					Menu.toExport = false;
				} else {
					XMLFormatter.importOperations(text.getText());
				}
			}
		
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JTextField text = new JTextField();
	JLabel label = new JLabel("Name of the file:");
	JButton ok = new JButton("OK");
}
