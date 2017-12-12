package Mrboneswildride.logic;

import Mrboneswildride.Config;
import java.util.Random;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*Class which creates the "coins"(organs)
*for Mr.Bones to pick-up in game
**/
public class Coin{

	private int xcoord;
	private int ycoord;
	private int value;
	private int imageNum = 0;
	private BufferedImage organ = null;
	
	/**
	*Default constructor
	*initialises coin laue, and image
	**/
	public Coin(){
		value = 10;
		Random randGen = new Random();
		imageNum = randGen.nextInt(3);
		
		try{
			File organLoc = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Organ"+imageNum+".png");
            FileInputStream fis = new FileInputStream(organLoc);  
            organ = ImageIO.read(fis);   
		}catch(IOException e){
			System.out.println("Error reading file");
		}
	}
	
	/**
	*Sets the value
	*@param int v the value to be set
	**/
	public void setValue(int v){
		value = v;
	}
	
	/**
	*Sets the x coord
	*int x the x coord to be set
	**/
	public void setxcoord(int x){
		xcoord = x;
	}
	
	/**
	*Sets the y coord
	*int y the y coord to be set
	**/
	public void setycoord(int y){
		ycoord = y;
	}
	
	/**
	*Gets the value
	**/
	public int getValue(){
		return value;
	}
	
	/**
	*Gets the x coord
	**/
	public int getxcoord(){
		return xcoord;
	}

	/**
	*Gets the y coord
	**/
	public int getycoord(){
		return ycoord;
	}

	/**
	*Gets the organ image
	**/
	public BufferedImage getOrganImage(){
		return organ;
	}
}
