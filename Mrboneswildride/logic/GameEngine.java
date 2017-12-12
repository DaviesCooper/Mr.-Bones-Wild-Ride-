package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
import Mrboneswildride.ai.*;
import Mrboneswildride.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
*@author T02-1
*
*A class which calculates all the actual decisions of the game.
*Takes inputs from most sources, and decides outputs based on algorithms
**/
public class GameEngine extends JPanel implements ActionListener{	
	private Timer timer;
	private GamePanel gamePanel;
	private InfoPanel infoPanel;
	private int ppt = 32;//pixels per tile
	private int[][] mapData;
	private Player player;
	private GraphicPlayer graphicPlayer;
	private int globalCooldown=40;
	private int localCooldown;
	private int manaCooldown = 0;
	private MainGUI frameReference;
	private ArrayList<Point> aiOpenSpawnPos = new ArrayList<Point>();
	private ArrayList<Point> aiWallSpawnPos = new ArrayList<Point>();
	private ArrayList<Point> coinSpawnPos = new ArrayList<Point>();
	private int totalAI = 9;//will multiply by aiMultiplier to get number of ai to spawn
	private int htest = 0;
	private int coinNum = 10;
	private int playerxStart = 0;
	private int playeryStart = 0;
	private boolean pauseMenuActive = false;
	/**
	*Constructor taking one argument
	*int level the level of the current play
	**/
	public GameEngine(int level,double aiMultiplier,int score){//used to initialize engine
		totalAI = (int)Math.round((double)totalAI*aiMultiplier);
		player = new Player();
		player.setSpeed(5);
		player.setScore(score);
		double defence = ((double)level)*0.166+1;
		if(defence>2)
			defence = 2;
		else if(defence<1)
			defence = 1;
		player.setDefense(defence);
		setBackground(Color.black);
        
		if(totalAI==0){
			gamePanel = new GamePanel(level,player,true);//add map, bossLevel
		}else{
			gamePanel = new GamePanel(level,player,false);//add map
		}
		gamePanel.setLocation(0, 0);
		gamePanel.setSize(10000, 10000);
		
		infoPanel = new InfoPanel(player,gamePanel);//add player panel with health
		infoPanel.setLocation(0,450);
		infoPanel.setSize(480,50);
        
		graphicPlayer = new GraphicPlayer(player);//add visual player
		graphicPlayer.setLocation(224,224);
		graphicPlayer.setSize(32,32);
        graphicPlayer.processImage();
		
		add(infoPanel);
		add(graphicPlayer);
		add(gamePanel);
        
		spawnPlayer();//finds position and places character
		findValidAISpawnPositions();//find positions to spawn the ai in
		if(totalAI==0){
			spawnBoss(level);
		}else{
			spawnAI();//actually spawn the ai
			spawnCoins();
		}
		setupPortal();
		 //starts the timer and therefore the game
		timer = new Timer(25,this);
		timer.start();
	}
	/**
	 *Finds the starting spot for the player and spawns the player
	 */
	public void spawnPlayer(){
		mapData = gamePanel.getMapData();
		//Find start position for player that is in boundaries
		boolean stop = false;
		for(int row = 0;row<mapData.length;row++){
			for(int col = 0;col<mapData[0].length;col++){
				if(mapData[row][col]==15){
					gamePanel.setLocation(240-(row*ppt+ppt/2),240-(col*ppt+ppt/2));
					player.setxcoord(235-gamePanel.getX());
					player.setxcoord(235-gamePanel.getY());
					playerxStart = row;
					playeryStart = col;
					stop = true;
					break;
				}
			}
			if(stop){
				break;
			}
		}
	}
	/**
	 *Finds valid AI spawn positions and stores them in an array
	 */
	public void findValidAISpawnPositions(){
		for(int row = 0;row<mapData.length;row++){
			for(int col = 0;col<mapData[0].length;col++){
				//checks to see if the position is within 7 tiles of where the player spawned. 
				//This prevents ai from spawning next to the player
				//upon game start
				if(Math.sqrt(Math.pow(row-playerxStart,2)+Math.pow(col-playeryStart,2))>9){
					if(mapData[row][col]<1){//add position to wall spawn or open spawn array for ai to use
						aiWallSpawnPos.add(new Point(row,col));
					}else{
						aiOpenSpawnPos.add(new Point(row,col));
					}
				}
			}    
		}
	}
	/**
	 *Finds a valid spawn position for the boss, and passes it to the gamePanel
	 */
	public void spawnBoss(int level){
		Random randGen = new Random();
		int indx = randGen.nextInt(aiOpenSpawnPos.size());
		Boss boss = null;
		int bType = 1;
		if(level>6){//decides which of the 6 bosses to spawn
			bType = (level%6);
			if (bType == 0)
				bType=6;
		}else
			bType = level;
		switch(bType){
			case(1):
				boss = new Knight(player,level);
			break;
			case(2):
				boss = new Eyeball(player,level);
			break;	
			case(3):
				boss = new Wizard(player,level);
			break;
			case(4):
				boss = new HellSpawn(player,level);
			break;
			case(5):
				boss = new Demon(player,level);
			break;
			case(6):
				boss = new AntiBones(player,level);
		}
		Point spawnSpot = aiOpenSpawnPos.get(indx);
		boss.setxcoord((int)Math.round(spawnSpot.getX()*ppt));
		boss.setycoord((int)Math.round(spawnSpot.getY()*ppt));
		boss.setScore(100+level*25);
		gamePanel.addAI((Ai) boss);
		aiOpenSpawnPos.remove(indx);    
	}
	/**
	 *Spawn ai and passes the ai to the gamePanel
	 */
	public void spawnAI(){
		//Decide how to spawn AI. Use Total AI number and divide it probably. Will need to import stuff.
		int ghostNum = totalAI/3;
		Random randGen = new Random();
		for(int num = 0;num<ghostNum;num++){
			int indx = randGen.nextInt(aiWallSpawnPos.size());
			//create new ghost and set its position to the following
			Ghost nghost = new Ghost(player);
			Point spawnSpot = aiWallSpawnPos.get(indx);
			nghost.setxcoord(spawnSpot.getX()*ppt);
			nghost.setycoord(spawnSpot.getY()*ppt);
			gamePanel.addAI((Ai) nghost);
			aiWallSpawnPos.remove(indx);
		}
		int otherAINum = totalAI*2/3;
		for(int num = 0;num<otherAINum;num++){
			int type = randGen.nextInt(2);
			int indx = randGen.nextInt(aiOpenSpawnPos.size());
			switch(type){
				case 0://create new regular ai
					Melee melee = new Melee(player);
					Point spawnSpot = aiOpenSpawnPos.get(indx);
					melee.setxcoord((int)Math.round(spawnSpot.getX()*ppt));
					melee.setycoord((int)Math.round(spawnSpot.getY()*ppt));
					gamePanel.addAI((Ai) melee);
					aiOpenSpawnPos.remove(indx);
					break;
				case 1://create ranged ai
					Turret turret = new Turret(player);
					Point spawnSpot2 = aiOpenSpawnPos.get(indx);
					turret.setxcoord((int)Math.round(spawnSpot2.getX()*ppt));
					turret.setycoord((int)Math.round(spawnSpot2.getY()*ppt));
					gamePanel.addAI((Ai) turret);
					aiOpenSpawnPos.remove(indx);
					break;
				default:
					System.out.println("AI type not recognized!");
					break;
			}
		}
	}
	/**
	 *Finds valid positions for coins and spawns them by passing them to the gamePanel
	 */
	public void spawnCoins(){
		for(int row = 0;row<mapData.length;row++){
			for(int col = 0;col<mapData[0].length;col++){
				if(mapData[row][col]>0){
					coinSpawnPos.add(new Point(row,col));
				}
			}
		}

		for(int j = 0;j<coinNum;j++){
			Random randGen = new Random();
			
			int indx = randGen.nextInt(coinSpawnPos.size());
			Point spawnSpot = coinSpawnPos.get(indx);
			Coin c = new Coin();
			c.setxcoord((int)Math.round(spawnSpot.getX()*ppt+8));
			c.setycoord((int)Math.round(spawnSpot.getY()*ppt+8));
			coinSpawnPos.remove(indx);
			gamePanel.addCoin(c);
		}
		
	}
	/**
	 *Finds a suitable position for the portal to spawn and spawns it
	 */
	public void setupPortal(){
		GraphicPortal portal = new GraphicPortal();
		ArrayList<Point> portalPos = new ArrayList<Point>();
		for(int row = 0;row<mapData.length;row++){
			for(int col = 0;col<mapData[0].length;col++){
		                if(mapData[row][col]==14 && row+1<mapData.length){
					if(mapData[row+1][col]==14){
						portalPos.add(new Point(row,col));
					}
				}
			}
		}
		Random randGen = new Random();
		int pos = randGen.nextInt(portalPos.size());
		Point spawnSpot = portalPos.get(pos);
		mapData[(int)spawnSpot.getX()][(int)spawnSpot.getY()-1] = 1;
		mapData[(int)spawnSpot.getX()+1][(int)spawnSpot.getY()-1] = 1;
		portal.setSize(64,32);
		portal.setLocation((int)Math.round(spawnSpot.getX()*ppt),(int)Math.round(spawnSpot.getY()*ppt-30));
		gamePanel.setPortal(portal);
	}

	/**
	*Acts as an action listener which handles input from the keyboard and mouse.
	*using these inputs, decides the course of the player in the game
	*
	*@param ActionEvent e the action implemented into the listener
	**/
	@Override//runs every "tick" to update the game state
	public void actionPerformed(ActionEvent e) {
    		//Check player health first, then go to Gameover if 0 or less
		if(player.getHealth()<=0){
			timer.stop();
			frameReference.playerDied(player.getScore());
		}else if(player.getAppearance()=='!'){
			timer.stop();
			frameReference.setScore(player.getScore());
			frameReference.playerWon();
		}
		if(player.getMana()<100 && manaCooldown>=4 && player.getAppearance()=='#'){
			player.setMana(player.getMana()+1);
			manaCooldown = 0;
		}else{
			manaCooldown++;
		}

            
		InputManager playerInput = InputManager.singleton;
		if(playerInput.isKeyDown(65)){//left-A
			if(validateMove(gamePanel.getX()+player.getSpeed()+10,gamePanel.getY())){
				gamePanel.setLocation(gamePanel.getX()+player.getSpeed(), gamePanel.getY());
				//player.setxcoord(player.getxcoord()-player.getSpeed());
				player.setDirection("Left");
			}
		}else if(playerInput.isKeyDown(68)){//right-D
			if(validateMove(gamePanel.getX()-player.getSpeed()-10, gamePanel.getY())){
				gamePanel.setLocation(gamePanel.getX()-player.getSpeed(), gamePanel.getY());
				//player.setxcoord(player.getxcoord()+player.getSpeed());
				player.setDirection("Right");
			}
		}
		if(playerInput.isKeyDown(87)){//up-W
			if(validateMove(gamePanel.getX(), gamePanel.getY()+player.getSpeed()+10)){
				gamePanel.setLocation(gamePanel.getX(), gamePanel.getY()+player.getSpeed());
				//player.setycoord(player.getycoord()-player.getSpeed());
				player.setDirection("Up");
			}
		}else if(playerInput.isKeyDown(83)){//down-S
			if(validateMove(gamePanel.getX(), gamePanel.getY()-player.getSpeed()-10)){
				gamePanel.setLocation(gamePanel.getX(), gamePanel.getY()-player.getSpeed());
				//player.setycoord(player.getycoord()+player.getSpeed());
				player.setDirection("Down");
			}
		}
		if(playerInput.isKeyDown(27) && pauseMenuActive==false){
			timer.stop();
			PauseMenu pm = new PauseMenu(this,frameReference);
			pauseMenuActive = true;
			playerInput.update();
		}else{
			pauseMenuActive = false;
		}
		//sets player coordinates to GamePanel coordinates
		player.setxcoord(235-gamePanel.getX());
		player.setycoord(235-gamePanel.getY());   
		//player shooting
		if(playerInput.isKeyDown(32)&&player.getMana()>=4&&globalCooldown>=8 ){//space shoot
			player.setMana(player.getMana()-4);
			Player tempPlayer = new Player(player);
			Projectile proj = new Projectile(tempPlayer);
			Point mouseCoords = getMousePos();
			proj.spawn(player.getxcoord(),player.getycoord(),
				(int)Math.round(mouseCoords.getX()-gamePanel.getX()-5),
				(int)Math.round(mouseCoords.getY()-gamePanel.getY()-30));
			gamePanel.addProjectile(proj);
			globalCooldown=0;
		}else if(playerInput.getShot()&&player.getMana()>=4&&globalCooldown>=8){
			player.setMana(player.getMana()-4);
			MouseEvent mousePos = playerInput.getMouseCoords();
			Player tempPlayer = new Player(player);
			Projectile proj = new Projectile(tempPlayer);
			Point mouseCoords = mousePos.getPoint();
			proj.spawn(player.getxcoord(),player.getycoord(),
				(int)Math.round(mouseCoords.getX()-gamePanel.getX()-5),
				(int)Math.round(mouseCoords.getY()-gamePanel.getY()-30));
			gamePanel.addProjectile(proj);
			globalCooldown=0;
		}//mouse shoot
		
        
		//player input for explode attack: e
		if(playerInput.isKeyDown(69)&&globalCooldown>=10&&player.getMana()>=25){
		player.setMana(player.getMana()-25);
			player.explodeAttack(gamePanel,player);
			globalCooldown=0;
		}
			
		//player input for defence bubble: q
		if(playerInput.isKeyDown(81)&&player.getAppearance()=='#'&&globalCooldown>=20&&player.getMana()>=10){
			//System.out.println("bubble");
			player.setMana(player.getMana()-10);
			player.bubble();
			localCooldown=0;
			globalCooldown=0;
		}else if(playerInput.isKeyDown(81)&&player.getAppearance()=='O'&&globalCooldown>=10){
			player.setAppearance('#');
			player.setDefense(1);
			globalCooldown=0;
		}
		//resets to regular health
		/*if(localCooldown>=80){
			player.setAppearance('#');
			player.setDefense(1);	
		}*/
		localCooldown++;
		globalCooldown++;
		gamePanel.updatePanel();

		repaint();
	}

	/**
	*Used to retrieve the x and y coords of the mouse for the projectiles
	**/
	private Point getMousePos(){
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		Point onScreen = frameReference.getLocationOnScreen();
		Point finalPos = new Point((int)Math.round(mousePos.getX()-onScreen.getX()),(int)Math.round(mousePos.getY()-onScreen.getY()));
		return finalPos;
	}

	/**
	*Checks to see if the move a player tries to make is valid.
	*Returns true or false.
	*@param int xcoord the inputted x coordinate for the desired move
	*@param int ycoord the inputted y coordinate for the desired move
	**/
	private boolean validateMove(int xcoord, int ycoord){//checks to see if the player move is within bounds
		//Math.abs
		int playerx = 240-xcoord;
		int playery = 240-ycoord;        
		int remainX = playerx%ppt;
		int remainY = playery%ppt;
		if(mapData[Math.abs(playerx-remainX)/ppt][Math.abs(playery-remainY)/ppt]<=0){
			return false;
		}
		return true;//means can move
	}

	/**
	*Sets the reference for th frame of the JFrame
	*@param JFrame ref the reffered JFrame
	**/
	public void setFrameReference(MainGUI ref){
		frameReference = ref;
	}
	/**
	 *Resumes the game after a pause
	 */
	public void resume(){
		timer.start();
	}
}
