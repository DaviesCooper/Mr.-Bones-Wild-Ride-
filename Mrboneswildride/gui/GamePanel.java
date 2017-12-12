package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.ai.*;
import Mrboneswildride.Config;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.lang.IndexOutOfBoundsException;
import java.io.*;
/**
 *This class will hold everything that is on the map with the exception of the player.
 *That way, the map and everything in it will move at the same time/rate, and the 
 *player will be separate.
 */
//@SuppressWarnings("serial")
public class GamePanel extends JComponent{
	BufferedImage parts[] = null;
	BufferedImage emptySpot = null;
	BufferedImage map = null;
	int[][] mapData = null;
	int ppt = 32;//pixels per tile
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Ai> ais = new ArrayList<Ai>();//ArrayList for enemy AI
	private ArrayList<Coin> coins = new ArrayList<Coin>();//ArrayList for coins
	private Player player;
	private GraphicPortal portal = null;
	private boolean bossLevel = false;

	/**
	*Constructor which creates a game panelm using player and level variables
	*@param int clevel the current level to be displayed
	*@param Player nplayer all the stats of the player, carried over if a level has already been played
	**/
	public GamePanel(int clevel,Player nplayer,boolean bossLevel){//tile source file to load
		player = nplayer;
		if(clevel!=0 && clevel%7==0){
			clevel = 1;
		}else{
			clevel = clevel%7;
		}
		this.bossLevel = bossLevel;
		setupMap(clevel,bossLevel);//sets up map	
		repaint();		
	}
	/**
	*Finds and loads the correct tilemap then turns it into an actual map based on the level data.
	*@param level Current level being loaded, used to get correct BitMask
	*/
	public void setupMap(int level,boolean bossLevel){		
		String src = "Mrboneswildride"+Config.slash+"sources"+Config.slash+"BitMask"+level+".bmp";        
		ImageSpliter imageSplitter = new ImageSpliter();        
		try{
			parts = imageSplitter.getParts(src);
			File file = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"EmptyTile.bmp"); 
			FileInputStream fis = new FileInputStream(file);  
			emptySpot = ImageIO.read(fis);   
		}catch(IOException e){
			System.out.println("Error reading file");
		}
		BitMasking b = new BitMasking();
		if(bossLevel){
			b.generateBitMask(0);
		}else{
			b.generateBitMask(level);
		}
		mapData = b.getBitMask();
		map = new BufferedImage(mapData.length*ppt,mapData[0].length*ppt,BufferedImage.TYPE_INT_RGB);
		Graphics g = map.getGraphics();
		for(int j = 0;j<mapData.length;j++){
			for(int k = 0;k<mapData[0].length;k++){
				if(mapData[j][k]<=0){
					g.drawImage(emptySpot,j*ppt,k*ppt,null);
				}else{
					g.drawImage(parts[getArrayPos(mapData[j][k])],j*ppt,k*ppt,null);
				}       
			}
		}
	}
	/**
	*Paints all the actual parts of the visible game within a play through
	*@param Graphics g the graphics package used to draw with
	**/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(map,0,0,null);
		//paints all current projectiles
		for(int c = 0;c<coins.size();c++){
			g.drawImage(coins.get(c).getOrganImage(), coins.get(c).getxcoord(), coins.get(c).getycoord(),null);
		}
		for(int l = 0;l<projectiles.size();l++){
			int d = projectiles.get(l).getDamage();
			g.setColor(Color.white);
			int r = 10;
			if(projectiles.get(l).getAiPro()==true){
				g.setColor(Color.red);
			}
			if(d>10){
				r = (int)Math.round(((double)d)/1.5);
			}else{
				r = d;
			}
			g.fillOval(projectiles.get(l).getroundedXcoord()-(r/2), projectiles.get(l).getroundedYcoord()-(r/2),r, r);
			g.setColor(Color.white);
			if(projectiles.get(l).getAiPro()==true){
				g.setColor(Color.red);
			}
			g.setColor(Color.black);
			g.drawOval(projectiles.get(l).getroundedXcoord()-(r/2), projectiles.get(l).getroundedYcoord()-(r/2),r, r);
		}
		for(int l = 0;l<ais.size();l++){
			g.setColor(Color.green);
			if(ais.get(l).getAiType()==3){
				g.drawImage(ais.get(l).getImage(), ais.get(l).getxcoord()-32, ais.get(l).getycoord()-32, null);
			}
			else{
				g.drawImage(ais.get(l).getImage(), ais.get(l).getxcoord()-16, ais.get(l).getycoord()-16, null);
			}
			double healthRatio = ((double)ais.get(l).getHealth())/((double)ais.get(l).getMaxHealth());
			if(healthRatio>0.65){
				g.setColor(Color.green);
			}else if(healthRatio>0.35){
				g.setColor(Color.yellow);
			}else{
				g.setColor(Color.red);
			}   
			g.fillRect(ais.get(l).getxcoord()-10,ais.get(l).getycoord()-20,(int)Math.round(healthRatio*20),3);
		}		
	}

	/**
	*Updates the drawing of the playstate occuring every tick
	**/
	public void updatePanel(){//called every tick of the timer
		
		checkPortalStatus();
		checkCoinCollisions();
		checkForProjectileCollisions();
		checkForAICollisions();
        moveAI();
	}
	/**
	 *Checks to see if the portal should be active or not, and if the player is close enough to go through the portal
	 */
	public void checkPortalStatus(){
		if(portal.isActive()){
			double x1 = portal.getLocation().getX()+32;
			double y1 = portal.getLocation().getY()+5;
			double x2 = player.getxcoord();
			double y2 = player.getycoord();
			
			if(getDistance(x1,y1,x2,y2)<15){
				player.setAppearance('!');
			}
		}
		if(coins.size()==0 && bossLevel==false){
			portal.setActive();
		}else if(bossLevel==true && ais.size()==0){
			portal.setActive();
		}
	}
	/**
	 *Checks to see if the player is close enough to a coin to collect it
	 */
	public void checkCoinCollisions(){
		for(int b = 0;b<coins.size();b++){
			double x1 = player.getxcoord();
			double x2 = coins.get(b).getxcoord();
			double y1 = player.getycoord();
			double y2 = coins.get(b).getycoord();
			if(getDistance(x1,y1,x2,y2)<player.getHitBox()){
				player.setScore(player.getScore()+coins.get(b).getValue());
				coins.remove(b);
			}	
		}
	}
	/**
	 *Computes Collisions between Player projectiles and the AI, and AI projectiles and the Player.
	 */
	public void checkForProjectileCollisions(){
		try{
			for(int j = 0;j<projectiles.size();j++){
				projectiles.get(j).shoot();
				if(!inBounds(projectiles.get(j).getroundedXcoord(),projectiles.get(j).getroundedYcoord())){
					projectiles.remove(j);
				}else if(projectiles.get(j).getAiPro()==false){//if player projectile
					for(int k = 0;k<ais.size();k++){//checks distance from projectile to AI, and if that distance is within
					//the given value for the AIs hitbox, damage the AI and remove the projectile.
						double x1 = projectiles.get(j).getxcoord();
						double y1 = projectiles.get(j).getycoord();
						double x2 = ais.get(k).getxcoord();
						double y2 = ais.get(k).getycoord();
						
						if(getDistance(x1,y1,x2,y2)<ais.get(k).getHitBox()){//Ai hit
							ais.get(k).takeDamage(projectiles.get(j).getDamage());
							projectiles.remove(j);
							if(ais.get(k).getHealth()<=0){
								player.addScore(ais.get(k).getScore());
								ais.remove(k);
							}
							
						}
					}
				}else{//if Ai projectile, check for player hit
					double x1 = projectiles.get(j).getxcoord();
					double y1 = projectiles.get(j).getycoord();
					double x2 = player.getxcoord();
					double y2 = player.getycoord();
					
					if(getDistance(x1,y1,x2,y2)<player.getHitBox()&&projectiles.get(j).getAiPro()){
						player.takeDamage(projectiles.get(j).getDamage());
						projectiles.remove(j);	
					}
				}
			}
		}catch(IndexOutOfBoundsException e){
			//Catches an error when an array position that doesn't exist is accessed.
		}
	}
	/**
	 *Checks to see if an AI is close enough to the player to damage it.
	 */
	public void checkForAICollisions(){
		try{
			for(int k = 0;k<ais.size();k++){
				double x1 = player.getxcoord();
				double y1 = player.getycoord();
				double x2 = ais.get(k).getxcoord();
				double y2 = ais.get(k).getycoord();
				Ai temp = (Ai)ais.get(k);
				if(getDistance(x1,y1,x2,y2)<player.getHitBox()){
					player.takeDamage(temp.getAttack());
				}
			}
		}catch(IndexOutOfBoundsException e){
			//Catches an error when an array position that doesn't exist is accessed.
		}
	}
	/**
	 *Calculates moves for AI. Gets the AIType, which is an int, which represents what the
	 *AI is. 0 is zombie(Melee class). 1 is turret. 2 is ghost. 3 is boss.
	 */
	public void moveAI(){
		int playerx = player.getxcoord();
		int playery = player.getycoord();
		
		for(int l = 0;l<ais.size();l++){
			boolean isTriggered = false;
			int aix = ais.get(l).getxcoord();
			int aiy = ais.get(l).getycoord();
			double dist = getDistance((double)aix,(double)aiy,(double)playerx,(double)playery);
			if(((Ai)ais.get(l)).getTriggerDistance() >= 
				dist){
				isTriggered = true;
			}
			switch(ais.get(l).getAiType()){
				case 0:
					if(isTriggered){
						Ai melee = (Ai)ais.get(l);
						switch(melee.getXMove()){
							case 1:
								if(inBounds(melee.getxcoord()+melee.getXMove()+16,melee.getycoord())){
									melee.setxcoord(melee.getxcoord()+melee.getXMove());
								}
								break;
							case -1:
								if(inBounds(melee.getxcoord()+melee.getXMove()-16,melee.getycoord())){
									melee.setxcoord(melee.getxcoord()+melee.getXMove());
								}
								break;
							default:
								break;
						}
						switch(melee.getYMove()){
							case 1:
								if(inBounds(melee.getxcoord(),melee.getycoord()+melee.getYMove()+16)){
									melee.setycoord(melee.getycoord()+melee.getYMove());
								}
								break;
							case -1:
								if(inBounds(melee.getxcoord(),melee.getycoord()+melee.getYMove()-16)){
									melee.setycoord(melee.getycoord()+melee.getYMove());
								}
								break;
							default:
								break;
						}
					}
					break;
				case 1:
						if(isTriggered){
							Turret turret = (Turret)ais.get(l);
							turret.triggered();
							if(turret.getShooting()){
									addProjectile(turret.shootPlayer());
							}
						}
					break;
				case 2:
					if(isTriggered && dist>(player.getHitBox()/2)){
						Ghost ghost = (Ghost)ais.get(l);
						ghost.calcMove();
						ghost.setInOpen(inBounds(ghost.getRoundedxcoord(),ghost.getRoundedycoord()));
					}
					break;
				case 3:
					if(isTriggered && dist>(player.getHitBox()/2)){
						Boss boss = (Boss)ais.get(l);
						boss.behavior(this);
					}
					break;
				default:
					System.out.println("AI type not recognized!");
			}       	
		}
	}
	/**
	*Checks to see if a game state is valid based upon movement
	*@param int xcoord the desired x coordinate to move to
	*@param int ycoord the desired y coordinate to move to
	**/
	private boolean inBounds(int xcoord, int ycoord){//checks to if object is within bounds        
		int tempx = xcoord;
		int tempy = ycoord;        
		int remainX = tempx%ppt;
		int remainY = tempy%ppt;
		if(mapData[Math.abs(tempx-remainX)/ppt][Math.abs(tempy-remainY)/ppt]<=0){
			return false;
		}
    		return true;//means can move
	}
	/**
	*Calculates distance between 2 given points
	*@param x1 X-coordinate of first point
	*@param y1 Y-coordinate of first point
	*@param x2 X-coordinate of second point
	*@param y2 Y-coordinate of second point
	*@return Distance between points as a double
	*/
	public double getDistance(double x1,double y1,double x2,double y2){
		return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y1-y2,2));
	}

	public int getArrayPos(int value){
		int indx = 0;    
		switch(value){
			case 12:
				indx = 0;
				break;
			case 13:
				indx = 3;
				break;
			case 9:
				indx = 6;
				break;
			case 14:
				indx = 1;
				break;
			case 15:
				indx = 4;
				break;
			case 11:
				indx = 7;
				break;
			case 6:
				indx = 2;
				break;
			case 7:
				indx = 5;
				break;
			case 3:
				indx = 8;
				break;
		}
		return indx;
	}

	/**
	*returns the map of the current play
	**/
	public int[][] getMapData(){
		return mapData;
	}

	/**
	*Adds projectiles to an array
	*@param Projectile p the projectile to add to the map
	**/
	public void addProjectile(Projectile p){
		projectiles.add(p);
	}

	/**
	*Adds an Ai to the array
	*@param GameCharacter ai the ai character to add to the map
	**/
	public void addAI(Ai ai){
		ais.add(ai);
	}
	/**
	 *@return the number of AIleft currently
	 */
	public int getAILeft(){
		return ais.size();
	}
	/**
	 *Adds a coin to the coin array
	 *@param c coin to add
	 */
	public void addCoin(Coin c){
		coins.add(c);
	}
	/**
	 *Sets portal to passed in portal
	 *@param nportal Portal to add
	 */
	public void setPortal(GraphicPortal nportal){
		portal = nportal;
		add(portal);
	}
	/**
	 *@return the number of coins left
	 */
	public int getCoinsLeft(){
		return coins.size();
	}	
}
