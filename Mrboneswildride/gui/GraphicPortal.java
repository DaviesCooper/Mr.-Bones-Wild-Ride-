package Mrboneswildride.gui;

import Mrboneswildride.Config;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class GraphicPortal extends JComponent{
	BufferedImage activeDoor = null;
	BufferedImage inactiveDoor = null;
	private boolean active = false;
	
	/**
	 *Constructor gets images for the portal. Open and closed images
	 */
	public GraphicPortal(){	
		try{
			File doorA = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"DoorActive.png"); 
			File doorI = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"DoorInactive.png");
			FileInputStream fis = new FileInputStream(doorA);  
			activeDoor = ImageIO.read(fis); //reading the image file  
			fis = new FileInputStream(doorI);  
			inactiveDoor = ImageIO.read(fis);
		}catch(IOException e){
			System.out.println("Error reading file");
		}
		repaint();
		
	}
	/**
	 *Paints the door depending on whether it is open or not
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(active){
			g.drawImage(activeDoor,0,0,null);
		}else{
			g.drawImage(inactiveDoor,0,0,null);
		}
	}
	/**
	 *Opens portal
	 */
	public void setActive(){
		active = true;
	}
	/**
	 *@return returns portal status, whether it is open or not
	 */
	public boolean isActive(){
		return active;
	}
}
