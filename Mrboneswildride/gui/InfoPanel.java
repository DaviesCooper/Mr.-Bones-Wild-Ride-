package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.Config;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Color;

import javax.imageio.ImageIO;  
import java.awt.image.BufferedImage;  
import java.io.*; 
/**
*Is the info panel of the game.
*overlays the gamepanel, and displays all the curretn health mana etc. of the player
**/
public class InfoPanel extends JPanel{

	private Player player;
	private BufferedImage explodeImage;
	private BufferedImage shieldImage;
	private GamePanel aiInfo;
	
	/**
	*Default constructor which takes the players stats, and current gamepanel as input
	*@param Player player the current player stats
	*@param GamePanel npanel the curretn game state
	**/
	public InfoPanel(Player player,GamePanel npanel){
		this.player = player;
		aiInfo = npanel;
		setBackground(Color.darkGray);       
		try{
			File file = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Explosion.png"); 
			File file2 = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"Bubble.png");
			FileInputStream fis = new FileInputStream(file);  
			explodeImage = ImageIO.read(fis); //reading the image file  
			fis = new FileInputStream(file2);  
			shieldImage = ImageIO.read(fis);
		}catch(IOException e){
			System.out.println("Error reading file");
		}   
		this.setLayout(null);
		JLabel health = new JLabel("Health:");
		health.setLocation(5,5);
		health.setSize(80,20);
		health.setForeground(Color.black);
		add(health);        
		JLabel mana = new JLabel("Mana:");
		mana.setLocation(170,5);
		mana.setSize(50,20);
		mana.setForeground(Color.black);
		add(mana);        
		JLabel shield = new JLabel("(Q)Shield:");
		shield.setLocation(330,25);
		shield.setSize(80,20);
		shield.setForeground(Color.black);
		add(shield);
		JLabel explosion = new JLabel("(E)xplosion:");
		explosion.setLocation(330,5);
		explosion.setSize(100,20);
		explosion.setForeground(Color.black);
		add(explosion);		
		JLabel score = new JLabel("Score:");
		score.setLocation(5,25);
		score.setSize(100,20);
		score.setForeground(Color.black);
		add(score);        
		JLabel ainum = new JLabel("Parts Left:");
		ainum.setLocation(105,25);
		ainum.setSize(100,20);
		ainum.setForeground(Color.black);
		add(ainum);
	}

	/**
	*Paints the info panel to the gamepanel
	*@param Graphics g the graphics package used to paint
	**/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//health stuff first
		if(player.getHealth()>65){
			g.setColor(Color.green);
		}else if(player.getHealth()>30){
			g.setColor(Color.yellow);
		}else{
			g.setColor(Color.red);
		}
		if(player.getHealth()>=0){
			g.fillRect(60,5,player.getHealth(),20);           
		}
		g.drawRect(60,5,100,20);
		g.setColor(Color.black);
		if(player.getHealth()<0){
			g.drawString("0",63,20);
		}else{
			g.drawString(player.getHealth()+"",63,20);
		}
		//mana stuff second
		g.setColor(Color.blue);
		g.drawRect(215,5,100,20);
		g.fillRect(215,5,player.getMana(),20);
		g.setColor(Color.black);
		g.drawString(player.getMana()+"",218,20);        
		//Finally, draw the bubble and explosion pictures. Cover with gray to indicate if they cannot be used.
		g.drawRect(420,25,20,20);
		g.drawRect(420,5,20,20);
		g.drawImage(shieldImage,420,25,null);
		g.drawImage(explodeImage,420,5,null);        
		g.setColor(Color.gray);
		if(player.getMana()<10){
			g.fillRect(420,45,19,(int)Math.round(((double)player.getMana()-10)/10*19));
		}
		if(player.getMana()<25){
			g.fillRect(420,25,19,(int)Math.round(((double)player.getMana()-25)/25*19));
		}
		g.setColor(Color.black);
		g.drawString(player.getScore()+"",55,40);
		g.drawString(aiInfo.getCoinsLeft()+"",205,40);
	}

}
