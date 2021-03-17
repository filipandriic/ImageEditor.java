package editor;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import java.util.ArrayList;

public class Image extends JFrame {
	
	public Image() {
		super("Image");
		this.setBounds(400, 100, 200, 200);
		width = this.getWidth();
		height = this.getHeight();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(false);
	}
	
	public void addLayer(Layer l) {
		this.add(l);	
		this.setSize(width, height);
	}
	
	public void deleteLayer(String s) {
		Layer l;
		int x = -1;
		int whelp = 0;
		int hhelp = 0;
		for (int i = 0; i < layers.size(); i++) {
			if (layers.get(i).name.equals(s)) {
				x = i;
			} else {
				if (whelp < layers.get(i).width) {
					whelp = layers.get(i).width;
				}
				if (hhelp < layers.get(i).height) {
					hhelp = layers.get(i).height;
				}
			}
		}
		if (x != -1) {
			l = layers.get(x);
			layers.remove(x);
			width = whelp;
			height = hhelp;
			
			this.remove(l);
			this.setSize(width, height);
		}
	}
	
	static ArrayList<Layer> layers = new ArrayList<>();
	static ArrayList<Selection> selections = new ArrayList<>();
	static int width;
	static int height;
}
