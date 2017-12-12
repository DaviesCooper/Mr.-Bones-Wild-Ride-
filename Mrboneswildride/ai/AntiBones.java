package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*Class which extends boss.
*Creates the last boss, and loads his images etc.
**/
public class AntiBones extends Boss{

	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player being passed
	*@param int level the level of the game (because levels are reiterated)
	**/
	public AntiBones(Player nplayer, int level){
		super(nplayer, level);
		setMovement(0,50);
		setCharge(71,80);
		setUnlimited(10,60);
		setShooting(0,30);
		setBigShot(81,100);
		setExploding(30,70);
		setSpawnGhosts(45,70);
		setManaBurn(81,90);
		setRotate(0,100);
	
		try{
			File LastU =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"LastBoss.png");
			File LastD =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"LastBoss2.png");
			File LastL =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"LastBoss3.png");
			File LastR =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"LastBoss4.png");
			FileInputStream fis = new FileInputStream(LastU);  
			up = ImageIO.read(fis);
			fis = new FileInputStream(LastD);
			down = ImageIO.read(fis);
			fis = new FileInputStream(LastL);
			left = ImageIO.read(fis);
			fis = new FileInputStream(LastR);
			right = ImageIO.read(fis);
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
}
