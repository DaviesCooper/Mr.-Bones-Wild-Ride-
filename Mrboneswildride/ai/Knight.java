package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*One of the bosses
**/
public class Knight extends Boss{

	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player (used for coordinates)
	*@param int level the current level (used for stat increase)
	**/
	public Knight(Player nplayer, int level){
		super(nplayer, level);
		setMovement(0,60);
		setCharge(61,100);
		setSpawnGhosts(0,30);
	
		try{
			File kLeft = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"KnightBoss2.png");
			File kRight = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"KnightBoss.png");
			FileInputStream fis = new FileInputStream(kLeft);  
			left = ImageIO.read(fis);
			fis = new FileInputStream(kRight);  
			right = ImageIO.read(fis);	
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
}
