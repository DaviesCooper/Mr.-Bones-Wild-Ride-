package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.logic.CartDirection;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*Ghost class which utilises all the methods for the ghost AI to use in the game
**/
public class Ghost extends Ai{
	private double xchange;
	private double ychange;
	private double xcoord;
	private double ycoord;
	private double speedMod = 3;
	private int defaultSpeed = 2;
	private int cSpeed = defaultSpeed;
	
	/**
	*Default constructor which takes a player as an argument
	*@param Player player intialises the Ghost 'character'
	**/
	public Ghost(Player player){
		super(player);
		setHealth(19);
		setMaxHealth(19);
		setAiType(2);
		setSpeed(cSpeed);
		setHitBox(16);
		setAttack(5);
		setScore(5);
		setTriggerDistance(650);
		
		try{
		File leftFaceGhost = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Ghost.png"); 
		File rightFaceGhost = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Ghost2.png");
		FileInputStream fis = new FileInputStream(leftFaceGhost);  
		left = ImageIO.read(fis);   
		fis = new FileInputStream(rightFaceGhost); 
		right = ImageIO.read(fis);
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
	
	/**
	*Calculates the behaviour of the Ghost AI
	**/
	public void calcMove(){
		CartDirection dir = new CartDirection(getRoundedxcoord(),getRoundedycoord(),player.getxcoord(),player.getycoord());
		xchange = dir.getCartX();
		ychange = dir.getCartY();
		if(xchange>0)
			setDirection("Right");
		else
			setDirection("Left");
		xcoord += xchange*(double)getSpeed();
		ycoord += ychange*(double)getSpeed();
		setxcoord(getRoundedxcoord());
		setycoord(getRoundedycoord());
	}
	
	/**
	*Checks if a ghost is moving through a wall or not
	*@param boolean open true if not in a wall
	**/
	public void setInOpen(boolean open){
		if(open == true){
			cSpeed = (int)Math.round(defaultSpeed*speedMod);
			setSpeed(cSpeed);
		}else{
			cSpeed = 2;
			setSpeed(cSpeed);	
		}
	}
	
	/**
	*Getters and Setters
	*/
	public int getRoundedxcoord(){
		return (int)Math.round(xcoord);
	}

	public int getRoundedycoord(){
		return (int)Math.round(ycoord);
	}
	public void setxcoord(double x){
		xcoord = x;
		setxcoord(getRoundedxcoord());
	}

	public void setycoord(double y){
		ycoord = y;
		setycoord(getRoundedycoord());
	}
}
