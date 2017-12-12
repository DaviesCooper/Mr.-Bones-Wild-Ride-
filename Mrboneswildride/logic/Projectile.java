package Mrboneswildride.logic;

import Mrboneswildride.gui.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
*Spawns a projectile object in front of the Player that travels until it hit something or reaches the edge of the screen.
**/
public class Projectile{

	private int damage;
	private double xchange;
	private double ychange;
	private int speed = 10;
	private int hitBox;
	private double xcoord;
	private double ycoord;
	private char appearance;
	private GameCharacter player = null;
	private boolean aiPro = false;
	
	/**
	*Constructor which takes a player as an argument
	*@param Player nchar the player you are passing
	**/
	public Projectile(GameCharacter nchar){
		player = nchar;
		if(player.getAiType()!=-1){
			setAiPro(true);
		}
		setxcoord(player.getxcoord());
		setycoord(player.getycoord());
		damage=10;
	}

	/**
	*Constructor which takes two ints for positions
	*@param int x the xcoord
	*@param int y the ycoord
	**/
	public Projectile(int x,int y){
		setxcoord(x);
		setycoord(y);
		setAiPro(true);
		damage=10;        
	}

	/**
	*Constructor which takes a player, and two ints as arguments
	*@param Player nchar the character being passed
	*@param int x the xcoord
	*@param int y the ycoord
	**/
	public Projectile(Player nchar, int x,int y){
		player = nchar;
		damage=10;
		setxcoord(x);
		setycoord(y);
	}
	
	/**
	*Returns the damage
	**/
	public int getDamage(){
		return damage;
	}

	/**
	*returns the speed
	**/
	public int getSpeed(){
		return speed;
	}

	/**
	*returns the hitbos
	**/
	public int getHitBox(){
		return hitBox;
	}

	/**
	*Gets the x coord after rounding
	**/
	public int getroundedXcoord(){
		int roundedX = (int)Math.round(xcoord);
		//System.out.println(roundedX);
		return roundedX;
	}

	/**
	*gets the y coord after rounding
	**/
	public int getroundedYcoord(){
		int roundedY = (int)Math.round(ycoord);
		//System.out.println(roundedY);
		return roundedY;
	}

	/**
	*gets the x coord
	**/
	public double getxcoord(){
		return xcoord;
	}

	/**
	*gets the y coord
	**/
	public double getycoord(){
		return ycoord;
	}

	/**
	*gets its appearance
	**/
	public char getAppearance(){
		return appearance;
	}

	/**
	*Gets AI projectile
	**/
	public boolean getAiPro(){
		return aiPro;
	}
	
	/**
	*Sets damage
	*@param int d the amount of damage a projectile will do
	**/
	public void setDamage(int d){
		damage=d;
	}

	/**
	*Sets speed
	*@param int s the speed of a projectile
	**/
	public void setSpeed(int s){
		speed=s;
	}

	/**
	*Sets the hitbox
	*@param int hit the size of the hotbox
	**/
	public void setHitBox(int hit){
		hitBox=hit;
	}

	/**
	*Sets x coord
	*@param double x the x coord
	**/
	public void setxcoord(double x){
		xcoord=x;
	}

	/**
	*sets y coord
	*@param double y the y coord
	**/
	public void setycoord(double y){
		ycoord=y;
	}

	/**
	*Sets appearance
	*@param Char app the character used to denote a projectile (used for console)
	**/
	public void setAppearance(char app){
		appearance=app;
	}

	/**
	*Sets the ai projectile
	*@param boolean b true to set a projectile
	**/
	public void setAiPro(boolean b){
		aiPro=b;
	}

	/**
	*Moves projectile objects
	**/
	public void shoot(){
		setxcoord(getxcoord()+xchange*speed);
		setycoord(getycoord()+ychange*speed);      
	}

	/**
	*Spawns projectile infront of player object
	**/
	public void spawn(){
	
		if(player.getDirection()=="Left"){
			setAppearance('-');
			setxcoord(player.getxcoord()-1);
			setycoord(player.getycoord());
			xchange=-1;
		}	
		else if (player.getDirection()=="Right"){
			setAppearance('-');
			setxcoord(player.getxcoord()+1);
			setycoord(player.getycoord());
			xchange = 1;
		}
		else if(player.getDirection()=="Up"){
			setAppearance('|');
			setxcoord(player.getxcoord());
			setycoord(player.getycoord()-1);
			ychange = -1;
		}
		else if (player.getDirection()=="Down"){
			setAppearance('|');
			setxcoord(player.getxcoord());
			setycoord(player.getycoord()+1);
			ychange = 1;
		}
		else if (player.getDirection()=="NW"){
			setxcoord(player.getxcoord()-1);
			setycoord(player.getycoord()-1);
			ychange = -1;
			xchange = -1;
		}
		else if (player.getDirection()=="NE"){
			setxcoord(player.getxcoord()+1);
			setycoord(player.getycoord()-1);
			ychange = -1;
			xchange = 1;
		}
		else if (player.getDirection()=="SW"){
			setxcoord(player.getxcoord()-1);
			setycoord(player.getycoord()+1);
			ychange = 1;
			xchange = -1;
		}
		else if (player.getDirection()=="SE"){
			setxcoord(player.getxcoord()+1);
			setycoord(player.getycoord()+1);
			ychange = 1;
			xchange = 1;
		}
	}

	/**
	*Spawns a projectile in a certain direction of the mouse
	*@param int fnlx the x coord
	*@param int fnly the y coord
	**/
	public void spawn(int initX, int initY, int fnlX, int fnlY){
		CartDirection cart = new CartDirection(initX , initY, fnlX, fnlY);
		xchange = cart.getCartX();
		ychange = cart.getCartY();
	}	
	
	//Testing Code
	public static void main(String[] args){
		Player testPlayer = new Player();
		Projectile testProjectile = new Projectile(testPlayer);
		System.out.println("**Testing code**\n");
		System.out.println("Projectile default variables values are: \n");
		System.out.println(testProjectile.toString());
		System.out.println("damage: " + testProjectile.getDamage());
	}
	
}
