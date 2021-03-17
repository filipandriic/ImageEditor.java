package editor;

import java.util.ArrayList;

public class Selection {
	public Selection(String name, boolean active) {
		this.name = name;
		this.active = active;
	}
	
	public void addRectangle(Rectangle r) {
		rectangles.add(r);
	}
	
	boolean active;
	String name;
	ArrayList<Rectangle> rectangles = new ArrayList<>();
}
