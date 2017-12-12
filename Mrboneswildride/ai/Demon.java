package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*One of the bosses
**/
public class Demon extends Boss{
	
	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player (used for coordinates)
	*@param int level the level (used for stat increase)
	**/
	public Demon(Player nplayer, int level){
		super(nplayer, level);
		setReloadNum(10);
		setMovement(1,40);
		setCharge(41,60);
		setUnlimited(41,70);
		setBigShot(60,90);
		setExploding(30,50);
		setManaBurn(89,100);
	
		try{
			File wLeft = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Demon.png");
			File wRight = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Demon2.png");
			FileInputStream fis = new FileInputStream(wLeft);  
			left = ImageIO.read(fis);
			fis = new FileInputStream(wRight);  
			right = ImageIO.read(fis);	
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
}
