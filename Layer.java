package editor;

import java.awt.image.BufferedImage;

import javax.swing.*;


public abstract class Layer extends JPanel {
	public abstract void importLayer();
	public abstract void exportPicture();
	
	protected String name;
	protected BufferedImage layer = null;
	protected BufferedImage converted = null;
	protected int width;
	protected int height;
}
