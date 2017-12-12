package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Wizard extends Boss{
	//Sets behaviour percentages and loads its image
	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player (used for coordinates)
	*@param int level the current level (used for stat increase)
	**/
	public Wizard(Player nplayer, int level){
		super(nplayer, level);
		setMovement(0,30);
		setUnlimited(20,60);
		setShooting(20,70);
		setSpawnGhosts(71,100);
		setManaBurn(60,100);
	
		try{
			File wLeft = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Wizard2.png");
			File wRight = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Wizard.png");
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