package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
*One of the bosses
**/
public class Eyeball extends Boss{

	/**
	*Default constructor
	*Sets behaviour percentages and loads its image
	*@param Player nplayer the player (used for coordinates)
	*@param int level the current level (used for stat increase)
	**/
	public Eyeball(Player nplayer, int level){
		super(nplayer, level);
		setMovement(0,30);
		setUnlimited(40,70);
		setShooting(20,60);
		setBigShot(81,100);
		setExploding(61,80);
	
		try{
			File EyeU =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"bigEyeU.png");
			File EyeD =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"bigEyeD.png");
			File EyeL =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"bigEyeL.png");
			File EyeR =new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"bigEyeR.png");
			FileInputStream fis = new FileInputStream(EyeU);  
			up = ImageIO.read(fis);
			fis = new FileInputStream(EyeD);
			down = ImageIO.read(fis);
			fis = new FileInputStream(EyeL);
			left = ImageIO.read(fis);
			fis = new FileInputStream(EyeR);
			right = ImageIO.read(fis);
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
	}
}
