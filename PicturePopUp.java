package editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class PicturePopUp extends JFrame{
	public PicturePopUp() {
		super();
		this.setLayout(new GridLayout(3, 1));
		this.add(picName);
		this.add(name);
		this.add(ok, BorderLayout.CENTER);
		this.setBounds(400, 100, 200, 200);
		this.setVisible(true);
		
		ok.addActionListener((ev) -> {
			dispose();
			if (Menu.toDelete == false) {
				Menu.image.setVisible(true);
				Menu.enableButtons();
				if (name.getText().contains(".bmp")) {
					BMPLayer l = new BMPLayer();
					Menu.image.addLayer(l);
				} else {
					PAMLayer l = new PAMLayer();
					Menu.image.addLayer(l);
				}
			} else {
				if (name.getText().contains(".bmp") || name.getText().contains(".pam")) {
					Menu.image.deleteLayer(name.getText());
				}
				Menu.toDelete = false;
			}
			Menu.image.repaint();
			
			
		});
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JLabel picName = new JLabel("Insert picture name: ");
	static JTextField name = new JTextField();
	JButton ok = new JButton("OK");
}
