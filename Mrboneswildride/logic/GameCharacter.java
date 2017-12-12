package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
/**
 *Creates in game character objects 
 */
public class GameCharacter{
	
	private int health; 
	private int maxHealth;
	private int attack;
	private double defense;
	private int speed;
	private int hitBox;
	private int xcoord;
	private int ycoord;
	private String direction;
	private char appearance;
	private int aiType;
	private int score = 0;
	
	/**
	*Default GameCharacter constructor
	*/
	public GameCharacter(){
	
		health=100;
		attack=10;
		defense=1;
		speed=1;
		hitBox=5;
		appearance=' ';
		xcoord=5;
		ycoord=5;
		direction="Right";
		aiType=-1;
	}
	
	/**
	*Copy constructor
	*
	*@param toCopy is the GameCharacter needing to be copied
	**/
	public GameCharacter(GameCharacter toCopy){
	
		setHealth(toCopy.getHealth());
		setAttack(toCopy.getAttack());
		setDefense(toCopy.getDefense());
		setSpeed(toCopy.getSpeed());
		setHitBox(toCopy.getHitBox());
		setxcoord(toCopy.getxcoord());
		setycoord(toCopy.getycoord());
		setDirection(toCopy.getDirection());
		setAiType(toCopy.getAiType());
	}
	
	/** 
	*@return the type of ai as an int
	*/
	public int getAiType(){
		return aiType;
	}
	/** 
	*@return health of character
	*/
	public int getHealth(){
		return health;
	}
	/** 
	*@return max health of character
	*/
	public int getMaxHealth(){
		return maxHealth;
	}
	/** 
	*@return attack value of character
	*/
	public int getAttack(){
		return attack;
	}
	/** 
	*@return defence value of character
	*/
	public double getDefense(){
		return defense;
	}
	/** 
	*@return speed of character
	*/	
	public int getSpeed(){
		return speed;
	}
	/** 
	*@return hitBox radius of character
	*/
	public int getHitBox(){
		return hitBox;
	}
	/** 
	*@return x coordinate of character
	*/
	public int getxcoord(){
		return xcoord;
	}
	/** 
	*@return y coordinate of character
	*/
	public int getycoord(){
		return ycoord;
	}
	/** 
	*@return direction that the character is facing
	*/
	public String getDirection(){
		return direction;
	}
	/** 
	*@return appearance of character
	*/
	public char getAppearance(){
		return appearance;
	}
	/** 
	*@return current character score
	*/
	public int getScore(){
		return score;
	}
	
	/**
	*@param type Value to set aiType too
	*/
	public void setAiType(int type){
		aiType=type;
	}
	/**
	*@param h Value to set health too
	*/
	public void setHealth(int h){
		health=h;
	}
	/**
	*@param mh Value to set maxHealth too
	*/
	public void setMaxHealth(int mh){
		maxHealth = mh;
	}
	/**
	*@param a Value to set attack too
	*/
	public void setAttack(int a){
		attack=a;
	}
	/**
	*@param d Value to set defense too
	*/	
	public void setDefense(double d){
		defense=d;
	}
	/**
	*@param s Value to set speed too
	*/
	public void setSpeed(int s){
		speed=s;
	}
	/**
	*@param hit Value to set hitBox too
	*/
	public void setHitBox(int hit){
		hitBox=hit;
	}
	/**
	*@param x Value to set xcoord too
	*/
	public void setxcoord(int x){
		xcoord=x;
	}
	/**
	*@param y Value to set ycoord too
	*/
	public void setycoord(int y){
		ycoord=y;
	}
	/**
	*@param d Value to set direction too
	*/
	public void setDirection(String d){
		direction=d;
	}
	/**
	*@param app Value to set appearance too
	*/
	public void setAppearance(char app){
		appearance=app;
	}
	/**
	*@param s Value to set score too
	*/
	public void setScore(int s){
		score = s;
	}
	
	/**
	*Used by GameCharacters to change their x,y positions
	*
	*@param xchange modifies xcoord
	*@param ychange modifies ycoord
	*/
	public void move(int xchange, int ychange){
        setxcoord(getxcoord()+xchange);
        setycoord(getycoord()+ychange);
        //Direction tracking
        if(xchange==-1)
        	setDirection("Left");
        else if(xchange==1)
        	setDirection("Right");
        	
        if(ychange==-1)
        	setDirection("Down");
        else if(ychange==1)
        	setDirection("Up");      
	}
	
	/**
	*Spawns 8 projectiles and adds them to the gamePanel
	*
	*@param gamep gives access to gamePanel to directly add projectiles
	*@param char is copied for its location data
	*/
	//Special attack method
	public void explodeAttack(GamePanel gamep,GameCharacter gchar){
		GamePanel gamePanel = gamep;
		GameCharacter tempChar = new GameCharacter(gchar);
		for(int count=1; count<=8; count++){
			if(count==1){
				tempChar.setDirection("Up");
				Projectile up = new Projectile(tempChar);
				up.spawn();	
				up.setDamage(20);
				gamePanel.addProjectile(up);
			}
			else if(count==2){
				tempChar.setDirection("Down");
				Projectile down = new Projectile(tempChar);
				down.spawn();	
				down.setDamage(20);
				gamePanel.addProjectile(down);
			}
			else if(count==3){
				tempChar.setDirection("Right");
				Projectile right = new Projectile(tempChar);	
				right.spawn();
				right.setDamage(20);				
				gamePanel.addProjectile(right);
			}
			else if(count==4){
				tempChar.setDirection("Left");
				Projectile left = new Projectile(tempChar);
				left.spawn();	
				left.setDamage(20);	
				gamePanel.addProjectile(left);
			}
			else if(count==5){
				tempChar.setDirection("NW");
				Projectile nw = new Projectile(tempChar);
				nw.spawn();	
				nw.setDamage(20);	
				gamePanel.addProjectile(nw);
			}
			else if(count==6){
				tempChar.setDirection("NE");
				Projectile ne = new Projectile(tempChar);
				ne.spawn();	
				ne.setDamage(20);	
				gamePanel.addProjectile(ne);
			}
			else if(count==7){
				tempChar.setDirection("SW");
				Projectile sw = new Projectile(tempChar);
				sw.spawn();	
				sw.setDamage(20);	
				gamePanel.addProjectile(sw);
			}
			else{
				tempChar.setDirection("SE");
				Projectile se = new Projectile(tempChar);
				se.spawn();	
				se.setDamage(20);	
				gamePanel.addProjectile(se);
			}
		}
	}
	
	/**
	*Special defence method that increases defence
	*/
	public void bubble(){
		setAppearance('O');
		setDefense(10);
	}
	
	/**
	*Reduces a GameCharacters health
	*/
	public void takeDamage(int damage){
		if(((double)damage)/defense>1){
			health-=(int)Math.round(((double)damage)/defense);
		}else{
			health--;
		}
    }
	
	//Returns variables as strings for debugging
	public String toString(){
        return "health: " + getHealth() + "\nattack: " + getAttack() + "\ndefense: " + getDefense() + 
	"\nspeed: " + getSpeed() + "\nhitbox: " + getHitBox()  + "\nappearance: " + 
	getAppearance() + "\nxcoord: " + getxcoord() + "\nycoord: " + getycoord() + "\ndirection: " + getDirection() + " ";
	}
		
	//Testing Code
	public static void main(String[] args){
		GameCharacter testChar = new GameCharacter();
		System.out.println("**Testing code**\n");
		System.out.println("Characters default variables values are: \n");
		System.out.println(testChar.toString());
		System.out.println();
		System.out.println("Testing move method by increasing xcoord and ycoord by 5\n");
		testChar.move(5, 5);
		System.out.println(testChar.toString());	
	}	
}
