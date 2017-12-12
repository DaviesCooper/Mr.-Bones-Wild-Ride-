package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*One of the bosses
**/
public class HellSpawn extends Boss{

	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player (used for coodinates)
	*@param int level current level (used for stat increase)
	**/ 
	public HellSpawn(Player nplayer, int level){
		super(nplayer, level);
		setMovement(0,50);
		setUnlimited(40,60);
		setBigShot(71,100);
		setExploding(30,70);
	
		try{
			File wLeft = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Hellspawn2.png");
			File wRight = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Hellspawn.png");
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
