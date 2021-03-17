package editor;

import javax.swing.*;

import java.awt.event.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BMPLayer extends Layer {
	BMPLayer() {
		super();
		x = y = x2 = y2 = 0; 
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
		importLayer();
	}
	
	BMPLayer(boolean export) {
		super();
		exportPicture();
	}
	
	@Override
	public void importLayer() {
		try {
            layer = ImageIO.read(new File(PicturePopUp.name.getText()));
            
            this.name = PicturePopUp.name.getText();
            this.width = layer.getWidth();
            this.height = layer.getHeight();
           
            converted = new BufferedImage(layer.getWidth(), layer.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            converted.getGraphics().drawImage(layer, 0, 0, null);
            //layer = converted;
            
            Image.layers.add(this);
        } catch (IOException e) {
        	System.out.println("Sranje");
        }
		
	
		
		if (converted.getWidth() > Menu.image.width) {
			Menu.image.width = converted.getWidth();
		}
		if (converted.getHeight() > Menu.image.height) {
			Menu.image.height = converted.getHeight();
		}
		
		setSize(Menu.image.width, Menu.image.height);
		setVisible(true);
		
	}
	
	@Override
	public void exportPicture() {
		BufferedImage image = Menu.image.layers.get(0).converted;
		for (int i = 1; i < Menu.image.layers.size(); i++) {
			image.getGraphics().drawImage(Menu.image.layers.get(i).converted, 0, 0, null);
		}
		width = Menu.image.getWidth();
		height = Menu.image.getHeight();
		
		try {
			ImageIO.write(image, "png", new File(ExportPopUp.name.getText() + ".bmp"));
		} catch (IOException e) {

		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < Image.layers.size(); i++) {
			g.drawImage(Image.layers.get(i).converted, 0, 0, null);
		}
		
		Graphics2D g2d = (Graphics2D) g;
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.RED);

        for (Rectangle r : Menu.currentRectangles) {
        	g2d.drawRect(r.x, r.y, r.width, r.height);
        }
		
		int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        g2d.drawRect(px, py, pw, ph);
		
	}
	
	public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(int x, int y) {
        x2 = (x);
        y2 = (y);
    }

	
	class MyMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            setStartPoint(e.getX(), e.getY());
        }

        public void mouseDragged(MouseEvent e) {
            setEndPoint(e.getX(), e.getY());
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            setEndPoint(e.getX(), e.getY());
            repaint();
            int px = Math.min(x,x2);
            int py = Math.min(y,y2);
            int pw = Math.abs(x-x2);
            int ph = Math.abs(y-y2);
        
            if (px < 0) {
            	px = 0;
            }
            
            if (py < 0) {
            	py = 0;
            }
            
            if (py + ph > Menu.image.height) {
            	ph = Image.height - py;
            }
            if (px + pw > Menu.image.width) {
				pw = Image.width - px;
			}
            
            Menu.currentRectangles.add(new Rectangle(px, py, pw, ph));
            x = y = x2 = y2 = 0;
        }
    }
	
	int x, y, x2, y2;
}
