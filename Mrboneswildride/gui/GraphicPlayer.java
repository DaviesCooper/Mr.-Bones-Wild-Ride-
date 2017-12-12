package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.Config;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.io.*;
/**
*The graphics component of the player (i.e. appearance)
**/
public class GraphicPlayer extends JComponent{
	private BufferedImage[] mrBones = new BufferedImage[7];
	private Player player;
	private int indx = 0;
	/**
	*loads an image for the player appearance
	*@param Player player the player which holds this image
	*@try tries to load an image file
	**/
	public GraphicPlayer(Player player){
		this.player = player;
		try{
			File file = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones.png");
			File file2 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones2.png");
			File file3 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones3.png");
			File file4 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones4.png");
			File file5 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones5.png");
			File file6 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones6.png");
			File file7 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"MrBones7.png");
			FileInputStream fis = new FileInputStream(file);  
			mrBones[0] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file2);  
			mrBones[1] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file3);  
			mrBones[2] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file4);  
			mrBones[3] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file5);  
			mrBones[4] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file6);  
			mrBones[5] = ImageIO.read(fis); //reading the image file
			fis = new FileInputStream(file7);  
			mrBones[6] = ImageIO.read(fis); //reading the image file
		}catch(IOException e){
			System.out.println("Error reading file");
		}
		repaint();
	}

	/**
	*Paints the image of the player to the screen
	*@param Graphics g the graphics which we use to draw with
	**/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(player.getAppearance()=='O'){
			g.setColor(Color.blue);
			g.drawOval(0, 0,31, 31); 
			g.drawOval(0, 0,32, 32);
		}
		
		g.drawImage(mrBones[indx], 0,0,null);
				
	}

	/**
	*Sets the position for the player to be drawn
	*@param int newx the xcoord
	*@param newy the ycoord
	**/
	public void setPos(int newx, int newy){
		player.setxcoord(newx);
		player.setycoord(newy);
	}
	/**
	 *Decides what image should be used for Mr.Bones 
	 */
	public void processImage(){
		if(player.getDefense()>1.9){
			indx = 6;
		}else if(player.getDefense()>1.9){
			indx = 5;
		}else if(player.getDefense()>1.8){
			indx = 4;
		}else if(player.getDefense()>1.6){
			indx = 3;
		}else if(player.getDefense()>1.4){
			indx = 2;
		}else if(player.getDefense()>1.3){
			indx = 1;
		}else{
			indx = 0;
		}
	}

}
