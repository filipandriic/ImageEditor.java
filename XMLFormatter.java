package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLFormatter {
	public static void importProject(String input) {
		Menu.image.selections.clear();
		for (Layer l : Menu.image.layers) {
			Menu.image.remove(l);
		}
		Menu.image.layers.clear();
		Menu.operations.clear();
		Menu.constants.clear();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		

			Document document = builder.parse(new File(input));
			
			document.getDocumentElement().normalize();
			 
			Element root = document.getDocumentElement();
			int w, h;
			
			NodeList list = document.getElementsByTagName("FinalPicture");
			Element finalPic = (Element) list.item(0);
			w = Integer.parseInt(finalPic.getAttribute("WIDTH"));
			h = Integer.parseInt(finalPic.getAttribute("HEIGHT"));
			Menu.image.width = w;
			Menu.image.height = h;
			
			list = document.getElementsByTagName("Layer");
			for (int i = 0; i < list.getLength(); i++) {
				Element layer = (Element) list.item(i);
				Menu.image.setVisible(true);
				Menu.enableButtons();
				PicturePopUp.name.setText(layer.getAttribute("Name"));
				if (layer.getAttribute("Name").contains(".bmp")) {
					BMPLayer l = new BMPLayer();
					Menu.image.addLayer(l);
				} else {
					PAMLayer l = new PAMLayer();
					Menu.image.addLayer(l);
				}
			}
			
			list = document.getElementsByTagName("Selection");
			for (int i = 0; i < list.getLength(); i++) {
				Element selection = (Element) list.item(i);
				Selection s = new Selection(selection.getAttribute("Name"), Boolean.parseBoolean(selection.getAttribute("ACTIVE")));
				
				NodeList rectangles = selection.getElementsByTagName("Rectangle");
				for (int j = 0 ; j < rectangles.getLength(); j++) {
					Element rectangle = (Element) rectangles.item(j);
					int x = Integer.parseInt(rectangle.getAttribute("X"));
					int y = Integer.parseInt(rectangle.getAttribute("Y"));
					int width = Integer.parseInt(rectangle.getAttribute("WIDTH"));
					int height = Integer.parseInt(rectangle.getAttribute("HEIGHT"));
					
					Rectangle r = new Rectangle(x, y, width, height);
					s.addRectangle(r);
				}				
				Menu.image.selections.add(s);
			}
			
			list = document.getElementsByTagName("Operation");
			for (int i = 0; i < list.getLength(); i++) {
				Element operation = (Element) list.item(i);
				Menu.operations.add(Integer.parseInt(operation.getAttribute("Number")));
				Menu.constants.add(Integer.parseInt(operation.getAttribute("Constant")));
			}
		
		} catch (ParserConfigurationException e) {
		
		} catch (SAXException e) {

		} catch (IOException e) {
			
		}
	}
	
	public static void exportProject(String output) {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		 
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
			
			Element root = document.createElement("Root");
	        document.appendChild(root);
	        document.setXmlStandalone(true);
	         
	        Element finalPic = document.createElement("FinalPicture");
	        root.appendChild(finalPic);
	        
	        Attr width = document.createAttribute("WIDTH");
	        width.setValue(Integer.toString(Menu.image.width));
	        finalPic.setAttribute("WIDTH", Integer.toString(Menu.image.width));
	        
	        Attr height = document.createAttribute("HEIGHT");
	        height.setValue(Integer.toString(Menu.image.height));
	        finalPic.setAttributeNode(height);
	       
	     
	        Element layers = document.createElement("Layers");
	        root.appendChild(layers);
	        for (Layer l : Menu.image.layers) {
	        	Element layer = document.createElement("Layer");
	        	layers.appendChild(layer);
	        	
	        	Attr name = document.createAttribute("Name");
	        	name.setValue(l.name);
	        	layer.setAttributeNode(name);
	        	
	        	width = document.createAttribute("WIDTH");
	        	width.setValue(Integer.toString(l.width));
		        layer.setAttributeNode(width);
	        	
		        height = document.createAttribute("HEIGHT");
		        height.setValue(Integer.toString(l.height));
		        layer.setAttributeNode(height);       	
	        }
			
			
			Element selections = document.createElement("Selections");
			root.appendChild(selections);
			for (Selection s : Menu.image.selections) {
				Element selection = document.createElement("Selection");
				selections.appendChild(selection);
				
				Attr name = document.createAttribute("Name");
				name.setValue(s.name);
				selection.setAttributeNode(name);
				
				Attr active = document.createAttribute("ACTIVE");
				active.setValue(Boolean.toString(s.active));
				selection.setAttributeNode(active);
				
				for (Rectangle r : s.rectangles) {
					Element rectangle = document.createElement("Rectangle");
					selection.appendChild(rectangle);
					
					Attr x = document.createAttribute("X");
					x.setValue(Integer.toString(r.x));
					rectangle.setAttributeNode(x);
					
					Attr y = document.createAttribute("Y");
					y.setValue(Integer.toString(r.y));
					rectangle.setAttributeNode(y);
					
					width = document.createAttribute("WIDTH");
		        	width.setValue(Integer.toString(r.width));
		        	rectangle.setAttributeNode(width);
		        	
			        height = document.createAttribute("HEIGHT");
			        height.setValue(Integer.toString(r.height));
			        rectangle.setAttributeNode(height);	
				}	
			}
			
			Element operations = document.createElement("CompositeOperation");
			root.appendChild(operations);
			for (int i = 0; i < Menu.operations.size(); i++) {
				Element operation = document.createElement("Operation");
				operations.appendChild(operation);
				
				Attr num = document.createAttribute("Number");
				num.setValue(Integer.toString(Menu.operations.get(i)));
				operation.setAttributeNode(num);
				
				Attr constant = document.createAttribute("Constant");
				constant.setValue(Integer.toString(Menu.constants.get(i)));
				operation.setAttributeNode(constant);
			}
			
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new File(output + ".proj"));
			DOMSource source = new DOMSource(document);
			
			transformer.transform(source, result);
            
		} catch (ParserConfigurationException e) {
			
		} catch (TransformerException e) {
			
		}

        
	}
	
	
	public static void importOperations(String input) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Menu.operations.clear();
		Menu.constants.clear();
		try {
			builder = factory.newDocumentBuilder();
		

			Document document = builder.parse(new File(input));
			
			document.getDocumentElement().normalize();
			 
			Element root = document.getDocumentElement();
			
			NodeList list = document.getElementsByTagName("Operation");
			for (int i = 0; i < list.getLength(); i++) {
				Element operation = (Element) list.item(i);
				Menu.operations.add(Integer.parseInt(operation.getAttribute("Number")));
				Menu.constants.add(Integer.parseInt(operation.getAttribute("Constant")));
			}
		
		} catch (ParserConfigurationException e) {
		
		} catch (SAXException e) {

		} catch (IOException e) {
			
		}
	}
	
	public static void exportOperations(String output) {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		 
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();			
			
			Element root = document.createElement("Root");
	        document.appendChild(root);
	   
	        Element operations = document.createElement("CompositeOperation");
			root.appendChild(operations);
			for (int i = 0; i < Menu.operations.size(); i++) {
				Element operation = document.createElement("Operation");
				operations.appendChild(operation);
				
				Attr num = document.createAttribute("Number");
				num.setValue(Integer.toString(Menu.operations.get(i)));
				operation.setAttributeNode(num);
				
				Attr constant = document.createAttribute("Constant");
				constant.setValue(Integer.toString(Menu.constants.get(i)));
				operation.setAttributeNode(constant);
			}
			
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new FileWriter(output + ".fun"));
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			
            
		} catch (ParserConfigurationException e) {
			
		} catch (TransformerException e) {
			
		} catch (IOException e) {
			
		}
	}
}
