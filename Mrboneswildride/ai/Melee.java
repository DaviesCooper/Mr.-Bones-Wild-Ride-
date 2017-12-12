package Mrboneswildride.ai;

import Mrboneswildride.logic.Player;
import Mrboneswildride.Config;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
*AI class of the zombie enemy
**/
public class Melee extends Ai{
	private Player player;
	
	/**
	*Default constructor which initialises all the stats of the zombie enemies and loads its image
	**/
	public Melee(Player nplayer){
		super(nplayer);
		setHealth(80);
		setMaxHealth(80);
		setAiType(0);
		setSpeed(1);
		setHitBox(16);
		setAttack(12);
		setScore(10);
		setTriggerDistance(250);
		
		try{
		File leftFaceZombie = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Zombie.png");
		File rightFaceZombie = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Zombie2.png");
		FileInputStream fis = new FileInputStream(leftFaceZombie);
		left = ImageIO.read(fis);
		fis = new FileInputStream(rightFaceZombie);
		right = ImageIO.read(fis);
		}
		catch(IOException e){
			System.out.println("Error reading file");
		}
		
	}
}
