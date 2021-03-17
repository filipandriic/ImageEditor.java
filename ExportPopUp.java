package editor;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;

import javax.swing.*;

public class ExportPopUp extends JFrame{
	public ExportPopUp() {
		super();
		this.setLayout(new GridLayout(5, 1));
		this.add(label);
		this.add(name);
		this.add(choose);
		group.add(bmp);
		group.add(pam);
		
		panel.add(bmp);
		panel.add(pam);
		this.add(panel);
		this.add(ok, BorderLayout.CENTER);
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			
			if (bmp.isSelected()) {
				new BMPLayer(true);
			} else {
				new PAMLayer(true);
			}
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JLabel label = new JLabel("Name: ");
	static JTextField name = new JTextField();
	JLabel choose = new JLabel("Choose format: ");
	JButton ok = new JButton("OK");
	JCheckBox bmp = new JCheckBox(".bmp", true);
	JCheckBox pam = new JCheckBox(".pam", false);
	ButtonGroup group = new ButtonGroup();
	JPanel panel = new JPanel(new GridLayout(1, 2));
}
