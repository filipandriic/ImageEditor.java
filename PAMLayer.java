package editor;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

import javax.imageio.ImageIO;
import javax.swing.SwingConstants;

import editor.BMPLayer.MyMouseListener;

import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class PAMLayer extends Layer {
	public PAMLayer() {
		super();
		x = y = x2 = y2 = 0; 
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
		importLayer();
	}
	
	public PAMLayer(boolean export) {
		super();
		exportPicture();
	}
	@Override
	public void importLayer() {
		File image = new File(PicturePopUp.name.getText());
		int seek = 0;
		try {
			FileInputStream byteParser = new FileInputStream(image);
			BufferedReader lineParser = new BufferedReader(new FileReader(image));
			
			String line = lineParser.readLine();
			seek += line.length() + 1;
			if (!line.equals("P7")) {
				System.out.println("Wrong file.");
			}
			line = lineParser.readLine();
			seek += line.length() + 1;
			String helper = new String("[^0-9]*([0-9]*)");
			Pattern pattern = Pattern.compile(helper);
			Matcher matcher = pattern.matcher(line);
			
			if (matcher.find( )) {
		         width = Integer.parseInt(matcher.group(1));     
			}
			
			line = lineParser.readLine();
			seek += line.length() + 1;
			matcher = pattern.matcher(line);
			if (matcher.find( )) {
		         height = Integer.parseInt(matcher.group(1));     
			}
			
			line = lineParser.readLine();
			seek += line.length() + 1;
			matcher = pattern.matcher(line);
			if (matcher.find( )) {
		         depth = Integer.parseInt(matcher.group(1));     
			}
			
			line = lineParser.readLine();
			seek += line.length() + 1;
			matcher = pattern.matcher(line);
			if (matcher.find( )) {
		         maxVal = Integer.parseInt(matcher.group(1));     
			}
			
			line = lineParser.readLine();
			seek += line.length() + 1;
			helper = new String("([A-Z]*) ([A-Z]*.*)");
			pattern = Pattern.compile(helper);
			matcher = pattern.matcher(line);
			if (matcher.find( )) {
		         tuplType = matcher.group(2);     
			}
			
			line = lineParser.readLine();
			seek += line.length() + 1;
			lineParser.close();
			
			byteParser.skip(seek);
			
			if (depth == 3) {
				layer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				pixels = new byte[height * width * 3];
				byteParser.read(pixels);
				for (int i = 0; i < pixels.length; i += 3) {
					byte r = pixels[i];
					byte g = pixels[i + 1];
					byte b = pixels[i + 2];
								
					pixels[i + 0] = b;
					pixels[i + 1] = g;
					pixels[i + 2] = r;
				}
				
				layer.setData(Raster.createRaster(layer.getSampleModel(), new DataBufferByte(pixels, pixels.length), new Point() ));
			} else if (depth == 4) {
				pixels = new byte[height * width * 4];
				layer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
				byteParser.read(pixels);
				for (int i = 0; i < pixels.length; i += 4) {
					byte r = pixels[i];
					byte g = pixels[i + 1];
					byte b = pixels[i + 2];
					byte a = pixels[i + 3];
					
					
					pixels[i] = a;
					pixels[i + 1] = b;
					pixels[i + 2] = g;
					pixels[i + 3] = r;
				}
				
				layer.setData(Raster.createRaster(layer.getSampleModel(), new DataBufferByte(pixels, pixels.length), new Point() ));
				
			}
			
			byteParser.close();
			this.name = PicturePopUp.name.getText();
			
			
			converted = new BufferedImage(layer.getWidth(), layer.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	        converted.getGraphics().drawImage(layer, 0, 0, null);
	        
	        Image.layers.add(this);
	        
	        if (converted.getWidth() > Menu.image.width) {
				Menu.image.width = converted.getWidth();
			}
			if (converted.getHeight() > Menu.image.height) {
				Menu.image.height = converted.getHeight();
			}
			
			setSize(Menu.image.width, Menu.image.height);
			setVisible(true);
			
		} catch (IOException e) {
			
		}
		
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
			FileOutputStream byteWriter = new FileOutputStream(new File(ExportPopUp.name.getText() + ".pam"));
			StringBuilder sb = new StringBuilder();
			
			sb.append("P7\n");
			sb.append("WIDTH " + width + "\n");
			sb.append("HEIGHT " + height + "\n");
			sb.append("DEPTH " + 4 + "\n");
			sb.append("MAXVAL " + 255 + "\n");
			sb.append("TUPLTYPE" + "RGB_ALPHA" + "\n");
			sb.append("ENDHDR" + "\n");
			
			pixels = new byte[width * height * 4];
			
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					pixels[(i + width * j) * 4] = (byte) ((image.getRGB(i, j) >> 16) & 0xff);
					pixels[(i + width * j) * 4 + 1] = (byte) ((image.getRGB(i, j) >> 8) & 0xff);
					pixels[(i + width * j) * 4 + 2] = (byte) (image.getRGB(i, j) & 0xff);
					pixels[(i + width * j) * 4 + 3] = (byte) ((image.getRGB(i, j) >> 24) & 0xff);
				}
			}
			
			byteWriter.write(sb.toString().getBytes());
			byteWriter.write(pixels);
			byteWriter.close();
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
	
	private byte[] pixels;
	private String type = "P7";
	private int depth;
	private int maxVal;
	private String tuplType;
}
