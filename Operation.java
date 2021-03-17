package editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.*;

public class Operation extends JFrame {
	public native byte setPixel(int pixel, int num, int operation);
	
	public Operation() {
		super();
		this.setLayout(new GridLayout(0, 1));
		this.add(addition);
		this.add(subtraction);
		this.add(inverseSubtraction);
		this.add(multiplication);
		this.add(division);
		this.add(inverseDivision);
		this.add(power);
		this.add(log);
		this.add(abs);
		this.add(min);
		this.add(max);
		this.add(blackWhite);
		this.add(gray);
		
		group.add(addition);
		group.add(subtraction);
		group.add(inverseSubtraction);
		group.add(multiplication);
		group.add(division);
		group.add(inverseDivision);
		group.add(power);
		group.add(log);
		group.add(abs);
		group.add(min);
		group.add(max);
		group.add(blackWhite);
		group.add(gray);
		
		
		
		this.setBounds(400, 50, 350, 800);
		this.setVisible(true);
		panel.add(constant);
		panel.add(num);
		this.add(panel);
		addOp.addActionListener((ev) -> {
			dispose();
			int i = 0;
			for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();
	            if (button.isSelected()) {
	                Menu.operations.add(i + 1);  
	            }
	            i++;  
	        }
			Menu.constants.add(Integer.parseInt(num.getText() ));
		});
		
			
		this.add(addOp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	JCheckBox addition = new JCheckBox("Addition", true);
	JCheckBox subtraction = new JCheckBox("Subtraction", false);
	JCheckBox inverseSubtraction = new JCheckBox("Inverse Subtraction", false);
	JCheckBox multiplication = new JCheckBox("Multiplication", false);
	JCheckBox division = new JCheckBox("Division", false);
	JCheckBox inverseDivision = new JCheckBox("Inverse Division", false);
	JCheckBox power = new JCheckBox("Power", false);
	JCheckBox log = new JCheckBox("Log", false);
	JCheckBox abs = new JCheckBox("Abs", false);
	JCheckBox min = new JCheckBox("Min", false);
	JCheckBox max = new JCheckBox("Max", false);
	JCheckBox blackWhite = new JCheckBox("Black and white", false);
	JCheckBox gray = new JCheckBox("Grayscale", false);
	ButtonGroup group = new ButtonGroup();
	
	JPanel panel = new JPanel(new GridLayout(0, 2));
	JLabel constant = new JLabel("Constant:");
	JTextField num = new JTextField();
	JButton addOp = new JButton("Add Operation");
	JButton finish = new JButton("Process operations");
}
