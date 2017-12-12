package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.*;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenu extends JFrame implements ActionListener{

	private GameEngine engine;
	private MainGUI frameRef;
	
	/**
	 *Constructor sets up pause menu
	 *@cengine GameEngine reference to call resume game on
	 *@locFrame JFrame to centre the menu on
	 */
	public PauseMenu(GameEngine cengine, MainGUI locFrame){
		engine = cengine;
		frameRef = locFrame;
		setSize(150,150);
		setLocation(locFrame.getX()+165,locFrame.getY()+40);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		addButtons();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}
	/**
	 *Adds all of the buttons to the menu
	 */
	public void addButtons(){
	
		JButton quit_BTN = new JButton("Quit");
		quit_BTN.setSize(100,30);
		quit_BTN.setLocation(25,68);
		quit_BTN.setActionCommand("Quit");
		quit_BTN.addActionListener(this);
		
		JButton menu_BTN = new JButton("Main");
		menu_BTN.setSize(100,30);
		menu_BTN.setLocation(25,35);
		menu_BTN.setActionCommand("Menu");
		menu_BTN.addActionListener(this);
		
		JButton resume_BTN = new JButton("Resume");
		resume_BTN.setSize(100,30);
		resume_BTN.setLocation(25,2);
		resume_BTN.setActionCommand("Resume");
		resume_BTN.addActionListener(this);
		
		add(resume_BTN);
		add(menu_BTN);
		add(quit_BTN);
	
	}
	/**
	 *Process button presses
	 */
	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
		case "Menu":
			//go to main menu
			setVisible(false);
			frameRef.setupNext("ToMain");
		break;
		case "Resume":
			//resume game
			setVisible(false);
			engine.resume();
		break;
		case "Quit":
			System.exit(0);//quits the game
		break;
		default:
			System.out.println("Command not recognized!");
		}
	}

}
