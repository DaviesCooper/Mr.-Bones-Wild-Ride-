package Mrboneswildride.ai;

import Mrboneswildride.logic.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
*GameCharacter child class used for creating and intialising enemies
**/
public class Ai extends GameCharacter{
    
	private int triggerDistance;
	private int charXCoord;
	private int charYCoord;
	private Random rand = new Random();
	protected Player player;
	protected BufferedImage up = null;
	protected BufferedImage down = null;
	protected BufferedImage right = null;
	protected BufferedImage left = null;
    
	/**
	*First Constructor; initializes AI
	*@param nchar Takes Player for tracking purposes in {@link calcMove(ArrayList<AI> e)}
	**/
	public Ai(Player nchar){//when class is created adds the player for tracking, and gets the player coords
		super();
		setAppearance('E');
		player = nchar;
		charXCoord = player.getxcoord();
		charYCoord = player.getycoord();
		setxcoord(rand.nextInt(20));
		setycoord(rand.nextInt(20));
		setHitBox(16);
		setAiType(0);
	}

	/**
	*Second Constructor; used for copying
	*@param enemy Takes AI and copies certain important values
	**/
	public Ai(Ai enemy){//used for copying
		setxcoord(enemy.getxcoord());
		setycoord(enemy.getycoord());   
	}
	
	/**
	*Calculates the next direction the ai should move in based on the player location, checks to see if that is valid,
	*then moves to that position.
	**/
	//calculates the ai movement based on the ai position and the character position
	public void calcMove(){
		setxcoord(getXMove());	
		setycoord(getYMove());	
	}
	
	/**
	*Called in {@link calcMove(ArrayList<AI> e)} to get the move in the x axis
	*
	*@return Returns movement in the horizontal axis
	**/
	//calculates the ai X move
	public int getXMove(){
		charXCoord = player.getxcoord();
		if (getxcoord() < charXCoord){
		setDirection("Right");
		return 1;
		} 
		else if(getxcoord() > charXCoord){
			setDirection("Left");
			return -1;	
		}
		else
			return 0;
	}
	
	/**
	*Called in {@link calcMove(ArrayList<AI> e)} to get the move in the y axis
	*
	*@return Returns movement in the vertical axis
	**/
	//calculates the ai Y move
	public int getYMove(){
		charYCoord = player.getycoord();
		if (getycoord() < charYCoord){
			return 1;	
		}
		else if(getycoord() > charYCoord){
			return -1;
		}
		else
			return 0;
	}
		
	/**
	*returns a projectiles that is fired towards the player
	*/
	public Projectile shootPlayer(){
		Projectile proj = new Projectile(getxcoord(), getycoord());
		proj.setDamage(7);
		proj.spawn(getxcoord(), getycoord(), player.getxcoord(), player.getycoord());
		double xDif = Math.sqrt((int)Math.round((getxcoord()-player.getxcoord())*(getxcoord()-player.getxcoord())));
		double yDif = Math.sqrt((int)Math.round((getycoord()-player.getycoord())*(getycoord()-player.getycoord())));
		if(getxcoord()>player.getxcoord()&&xDif>yDif)
			setDirection("Left");
		else if(getxcoord()<player.getxcoord()&&xDif>yDif)
			setDirection("Right"); 	
		else if(getycoord()<player.getycoord()&&xDif<yDif)
			setDirection("Down");
		else if(getycoord()>player.getycoord()&&xDif<yDif)
			setDirection("Up");
		return proj;
	}
	
	/**
	*Returns the the AIs image that represents the direction it is facing
	*/
	public BufferedImage getImage(){
		if(getDirection() == "Up" && up!=null)
			return up;
		else if(getDirection() == "Down" && down!=null)
			return down;
		else if(getDirection() == "Left")
			return left;
		else 
			return right;
	}
	
	/**
	*Getters and Setters methods
	*/
	public int getTriggerDistance(){
		return triggerDistance;
	}
	public void setTriggerDistance(int t){
		triggerDistance=t;
	}
	
	//Testing Code
	public static void main(String[] args){//tests ai code
		Player testPlayer = new Player();
		testPlayer.setxcoord(5);
		testPlayer.setycoord(5);
		Ai aitester = new Ai(testPlayer);
		System.out.println("Player at Pos: (5, 5)");
		System.out.println("X: " + aitester.getxcoord() + " Y: " + aitester.getycoord());
		//aitester.calcMove();
		//aitester.calcMove();
		//aitester.calcMove();
		System.out.println("XAfterMove: " + aitester.getxcoord() + " YAfterMove: " + aitester.getycoord());
	}
}
		

	

	
