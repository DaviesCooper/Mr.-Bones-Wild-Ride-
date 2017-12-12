package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*Creates a turret type enemy AI
**/
public class Turret extends Ai{ 

	private boolean shooting = false;
	private int reload = 20;
	
	/**
	*Default constructor which initialises and creates a turret enemy
	*@param Player nchar the character stats for this class
	**/
	public Turret(Player nchar){
		super(nchar);
		setAiType(1);
		setTriggerDistance(250);
		setAttack(0);
		setHealth(50);
		setMaxHealth(50);
		setScore(15);
		
		try{
			File EyeU =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"EyeU.png");
			File EyeD =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"EyeD.png");
			File EyeL =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"EyeL.png");
			File EyeR =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"EyeR.png");
			FileInputStream fis = new FileInputStream(EyeU);  
			up = ImageIO.read(fis);
			fis = new FileInputStream(EyeD);
			down = ImageIO.read(fis);
			fis = new FileInputStream(EyeL);
			left = ImageIO.read(fis);
			fis = new FileInputStream(EyeR);
			right = ImageIO.read(fis);
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
	
	/**
	*Used to see if the player is within range of the player
	**/
	public void triggered(){
		if(reload>20){
			shooting=true;
			reload=0;}
		else{
			shooting=false;
			reload++;
		}
	}
	
	/**
	*Checks to see if the turret is shooting
	**/
	public boolean getShooting(){
		return shooting;
	}
}
