package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.Config;

import javax.imageio.ImageIO;  
import java.awt.image.BufferedImage;  
import java.io.*;  
import java.awt.*;  

import javax.swing.JComponent;
/**
*The title image for the opening jpanel
*is overlain by the buttons etc.
**/
public class TitleImage extends JComponent{
	private BufferedImage title;

	/**
	*Default constructor
	**/
	public TitleImage(){
		//load image here
		setSize(480,550);
		setLocation(0,0);		
		try{
			File file = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"TitleImage.bmp");   
			FileInputStream fis = new FileInputStream(file);  
			title = ImageIO.read(fis);  
		}catch(IOException e){
			System.out.println("Error reading title image");
		}		
	}

	/**
	*Paints the image to the jpanel
	*@param Graphics g the package used to draw with
	**/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(title,0,0,null);
	}
}
