package editor;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Menu extends JFrame {
	public Menu() {
		super("Menu");
		this.setSize(300, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1));
		
		SetMenu();
		image = new Image();
	
		this.setVisible(true);
	}
	
	
	
	private void SetMenu() {
		JLabel name = new JLabel("MENU", SwingConstants.CENTER);
		this.add(name);
		
		loadImage = new JButton("Load Image");
		loadImage.addActionListener((ev) -> {
			imageLoaded = true;
			new PicturePopUp();
		});
		this.add(loadImage);
		
		deleteImage = new JButton("Delete Image");
		deleteImage.setEnabled(false);
		deleteImage.addActionListener((ev) -> {
			toDelete = true;
			new PicturePopUp();
		});
		this.add(deleteImage);
		
		processImage = new JButton("Process Image");
		processImage.setEnabled(false);
		processImage.addActionListener((ev) -> {
			XMLFormatter.exportProject("X:\\JAVA\\poop2\\java");
			String file = new String("C:\\Users\\Filip\\Desktop\\opet\\poop\\Debug\\poop.exe " + 
		"X:\\JAVA\\poop2\\java.proj " +
		"X:\\JAVA\\poop2\\cpp.proj");
			Runtime runtime = Runtime.getRuntime();
			System.out.println("Starting process...");
			Process process;
			try {
				process = runtime.exec(file);
				System.out.println("Waiting for process to terminate...");
				process.waitFor();
				System.out.println("Process terminated.");
				System.out.println(process.exitValue());
			} catch (IOException e) {
				
			} catch (InterruptedException e) {
				
			}
			XMLFormatter.importProject("cpp.proj");
			Menu.operations.clear();
			Menu.constants.clear();
			image.repaint();
		});
		this.add(processImage);
		
		exportImage = new JButton("Export Image");
		exportImage.setEnabled(false);
		exportImage.addActionListener(ev -> {
			new ExportPopUp();
		});
		this.add(exportImage);
		
		addSelection = new JButton("Add Selection");
		addSelection.setEnabled(false);
		addSelection.addActionListener((ev) -> {
			new SelectionPopUp();
		});
		this.add(addSelection);
		
		showSelections = new JButton("Show active selections");
		showSelections.setEnabled(false);
		showSelections.addActionListener(ev -> {
			for (Selection s : image.selections) {
				if (s.active) {
					for (Rectangle r : s.rectangles) {
						currentRectangles.add(r);
					}
				}
			}
			image.repaint();
		});
		this.add(showSelections);
		
		dontshowSelections = new JButton("Dont't show active selections");
		dontshowSelections.setEnabled(false);
		dontshowSelections.addActionListener(ev -> {
			currentRectangles.clear();
			image.repaint();
		});
		this.add(dontshowSelections);
		
		deleteSelection = new JButton("Delete Selection");
		deleteSelection.setEnabled(false);
		deleteSelection.addActionListener((ev) -> {
			new SelectionDeletePopUp();
		});
		this.add(deleteSelection);
		
		compositeFunction = new JButton("Composite Function");
		compositeFunction.addActionListener(ev -> {
			new Operation();
		});
		this.add(compositeFunction);
		
		exportProject = new JButton("Export Project");
		exportProject.setEnabled(false);
		exportProject.addActionListener((ev) -> {
			toExport = true;
			project = true;
			new XMLPopUp();
		});
		this.add(exportProject);

		importProject = new JButton("Import Project");
		importProject.addActionListener((ev) -> {
			toExport = false;
			project = true;
			new XMLPopUp();
		});
		this.add(importProject);	
		
		importCompositeOperation = new JButton("Import composite operation");
		importCompositeOperation.addActionListener(ev -> {
			toExport = false;
			project = false;
			new XMLPopUp();
		});
		this.add(importCompositeOperation);
		
		exportCompositeOperation = new JButton("Export composite operation");
		exportCompositeOperation.addActionListener(ev -> {
			toExport = true;
			project = false;
			new XMLPopUp();
		});
		this.add(exportCompositeOperation);
	}
	
	static void enableButtons() {
		deleteImage.setEnabled(true);
		processImage.setEnabled(true);
		exportImage.setEnabled(true);
		addSelection.setEnabled(true);
		deleteSelection.setEnabled(true);
		compositeFunction.setEnabled(true);
		exportProject.setEnabled(true);
		showSelections.setEnabled(true);
		dontshowSelections.setEnabled(true);
	}
	
	private boolean imageLoaded = false;
	static private JButton loadImage;
	static private JButton deleteImage;
	static private JButton processImage;
	static private JButton exportImage;
	static private JButton addSelection;
	static private JButton deleteSelection;
	static private JButton compositeFunction;
	static private JButton exportProject;
	static private JButton importProject;
	static private JButton showSelections;
	static private JButton dontshowSelections;
	static private JButton exportCompositeOperation;
	static private JButton importCompositeOperation;
	static Image image;
	static boolean toDelete = false;
	static boolean toExport = false;
	static boolean project = false;
	static ArrayList<Integer> operations = new ArrayList<>();
	static ArrayList<Integer> constants = new ArrayList<>();
	static ArrayList<Rectangle> currentRectangles = new ArrayList<>();
}