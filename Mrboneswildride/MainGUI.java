package Mrboneswildride;

import Mrboneswildride.gui.*;
import Mrboneswildride.logic.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.Color;

/**
*The main Gui of the game, which is responsible for creating a window
*for the game to exist in, and filling that window with everything the game needs
**/
public class MainGUI extends JFrame implements ActionListener{

	/**
	*Main clas for testing
	**/
	public static void main(String[] args){
		MainGUI newGUI = new MainGUI();	
	}

	private InputManager input = new InputManager();
	private JTextField level = null;
	private int clevel = 1;
	private double aiMulti = 1.2;
	private int maxLives = 3; 
	private int cLives = maxLives;
	private TitleImage titleImage = new TitleImage();
	private int score = 0;
	private boolean bossLevel = false;	
	private ArrayList<JComponent> currentObjs = new ArrayList<JComponent>();
	private GameEngine gameEngine = null;
	
	/**
	*Default constructor
	*Initialises the window and recalls the title image
	*This is so that the title image undegoes the config file
	**/
	public MainGUI(){
		checkOperatingSystem();
		this.titleImage = new TitleImage();
		this.setBackground(Color.black);
		this.setSize(480, 530);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		mainScreen();
		
	}

	/**
	 *Checks the operating system to ensure that source files load correctly
	 */
	public void checkOperatingSystem(){
		Properties p = System.getProperties();
		String os = p.getProperty("os.name");		
		if(os.toLowerCase().contains("windows")){
			Config.slash = "\\";
		}else if(os.toLowerCase().contains("unix")||os.toLowerCase().contains("linux")){
			Config.slash = "/";
		}
	}
	/**
	*Starts the game based on a mouse click of the buttons. Listens for the buttons being pressed
	*@param ActionEvent e the event we are listening for
	**/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "StartGame":
			//start a new game;
			setupNext(e.getActionCommand());
			break;
		case "LoadGame":
			//go to load game screen;
			setupNext(e.getActionCommand());
			break;
		case "ToMain":
			setupNext(e.getActionCommand());
			break;
		case "LoadLevel":
			setupNext(e.getActionCommand());
			break;
		case "Controls":
			JOptionPane.showMessageDialog(this,"Use W,A,S,D to move and Space Bar or Mouse to shoot."+
						" Aim with the mouse as well.\n"+
						"You have 100 health and 100 mana.\nMana is used for shooting and abilities."+
						" You have two abilities, shield (Q) and Explode (E).\nShield reduces incoming damage,"+
						" but prevents mana regeneration.\nIt must be turned off manually (also Q)."+
						" Cast cost is 10 Mana.\n" +
						"Explode attack shoots projectiles out from you in every direction, and they do double damage."+
						" Cast cost is 25 Mana.\nTo complete each level, you must collect all your organs on the map.\n"+
						"Killing enemies adds to your score. Try to get a new HighScore!");
			break;
		case "BossLevel":
			bossLevel=!bossLevel;
			break;
		case "ViewScores":
			viewScores();
			break;
		case "ExitGame":
			System.exit(0);
			break;
		default:
			System.out.println("Invalid action command");
		
		}
		e.getActionCommand();
	}

	/**
	*Chooses what to do based on what is pressed
	*@param String screenToSet the scrren in which the game goes to
	**/
	public void setupNext(String screenToSet){
		for(int indx = 0;indx<currentObjs.size();indx++){
			this.remove(currentObjs.get(indx));
		}
		cLives = maxLives;
		currentObjs.clear();
		this.repaint();
		switch(screenToSet){
			case "StartGame":
				score = 0;
				gameScreen(clevel,aiMulti);
				break;
			case "LoadGame":
				score = 0;
				loadScreen();
				break;
			case "ToMain":
				mainScreen();
				break;
			case "LoadLevel":
				int levelToLoad = Integer.parseInt(level.getText());
				clevel = levelToLoad;
				aiMulti = (levelToLoad-1)*1.2;
				if(bossLevel){
					gameScreen(levelToLoad,0);
				}else{
					gameScreen(levelToLoad,aiMulti);
				}
				break;
			default:
				System.out.println("Command not recognized ");
				break;
		}
	}
	
	/**
	*The main screen of the game. The introductory screen
	*Adds all the buttons created in their own methods, to the screen.
	**/
	public void mainScreen(){     
		JButton startGame_BTN = new JButton("Start New Game");
		JButton howPlay_BTN = new JButton("How To Play");
		JButton loadGame_BTN = new JButton("Load Game");
		JButton highScore_BTN = new JButton("HighScores");
		JButton exitGame_BTN = new JButton("Exit");
		currentObjs.add(startGame_BTN);
		currentObjs.add(howPlay_BTN);
		currentObjs.add(loadGame_BTN);
		currentObjs.add(highScore_BTN);
		currentObjs.add(exitGame_BTN);
		currentObjs.add(titleImage);
		startGameButton(startGame_BTN);
		howToPlayButton(howPlay_BTN);
		loadGameButton(loadGame_BTN);
		highScoreButton(highScore_BTN);
		exitButton(exitGame_BTN);
		requestFocusInWindow();
		requestFocus();
		this.add(startGame_BTN);
		this.add(howPlay_BTN);
		this.add(loadGame_BTN);
		this.add(highScore_BTN);
		this.add(exitGame_BTN);
		this.add(titleImage);
	}

	/**
	*Creates the start game button and initialises it
	**/
	public void startGameButton(JButton button){		
		//startGame_BTN setup
		button.setSize(200, 30);
		button.setLocation(140, 170);
		button.addActionListener(this);
		button.setActionCommand("StartGame");
	}
	
	/**
	*Creates the how to play button and initialises it
	**/
	public void howToPlayButton(JButton button){
		//howPlay_BTN setup
		button.setSize(200, 30);
		button.setLocation(140, 220);
		button.addActionListener(this);
		button.setActionCommand("Controls");
	}
	
	/**
	*Creates the load game button and initialises it
	**/
	public void loadGameButton(JButton button){	
		//loadGame_BTN setup
		button.setSize(200, 30);
		button.setLocation(140, 270);
		button.addActionListener(this);
		button.setActionCommand("LoadGame");
	}
	
	/**
	*Creates the highscore button and initialises it
	**/
	public void highScoreButton(JButton button){	
		//highscore_BTN setup
		button.setSize(200, 30);
		button.setLocation(140, 320);
		button.addActionListener(this);
		button.setActionCommand("ViewScores");
	}
	
	/**
	*Creates the exit button and initialises it
	**/
	public void exitButton(JButton button){	
		//exitGame_BTN setup
		button.setSize(200,30);
		button.setLocation(140, 370);
		button.addActionListener(this);
		button.setActionCommand("ExitGame");
	}

	/**
	*setup load screen here. This is merely the display of it, not the actual
	*file retrieval.
	**/
	public void loadScreen(){
		TitleImage titleImage = new TitleImage();
		JButton load_BTN = setupLoad();
		JCheckBox boss_BOX = setupBoss();
		JButton back_BTN = setupBack();
		level = new JTextField(clevel+"");
		level.setSize(200,30);
		level.setLocation(140, 200);
		level.setEditable(true);
		currentObjs.add(load_BTN);
		currentObjs.add(level);
		currentObjs.add(back_BTN);
		currentObjs.add(titleImage);
		currentObjs.add(boss_BOX);
		add(load_BTN);
		add(boss_BOX);
		add(back_BTN);
		add(level);
		add(titleImage);
		repaint();
	}

	/**
	*Creates the load level button and intialises it
	**/
	public JButton setupLoad(){	
		JButton load_BTN = new JButton("Load Level");
		load_BTN.setSize(200,30);
		load_BTN.setLocation(140, 260);
		load_BTN.addActionListener(this);
		load_BTN.setActionCommand("LoadLevel");
		return load_BTN;
	}
	
	/**
	*Creates the boss level check box and initialises it
	**/
	public JCheckBox setupBoss(){
		JCheckBox boss_BOX = new JCheckBox("Is Boss Level");
		boss_BOX.setSize(200,20);
		boss_BOX.setLocation(140,230);
		boss_BOX.addActionListener(this);
		boss_BOX.setActionCommand("BossLevel");
		return boss_BOX;
	}
	
	/**
	*Creates the Back button and initialises it
	**/
	public JButton setupBack(){	
		JButton back_BTN = new JButton("Back");
		back_BTN.setSize(200,30);
		back_BTN.setLocation(140, 310);
		back_BTN.addActionListener(this);
		back_BTN.setActionCommand("ToMain");
		return back_BTN;
	}	

	/**
	*game play screen
	*@param int nlevel the level in which to generate the game
	*@param double aiMultiplier the aiMultiplier based on the level
	**/
	public void gameScreen(int nlevel,double aiMultiplier){	
        		gameEngine = new GameEngine(nlevel,aiMultiplier,score);
		gameEngine.setLocation(0, 0);
		gameEngine.setSize(10000, 10000);
		input = new InputManager();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		gameEngine.setFrameReference(this);
		requestFocusInWindow();
		requestFocus();        
		add(gameEngine);        
		currentObjs.add(gameEngine);      
		repaint();
	}

	/**
	*Used to advance the player from one level to the next
	*@param int preLevel the level the player just beat
	**/
	public void nextLevel(int prevLevel){//called to setup next level
		remove(gameEngine);
		clevel = prevLevel+1;
		aiMulti += aiMulti*0.25;
		cLives = maxLives;
		gameScreen(clevel,aiMulti);
		bossLevel = false;
	}

	/**
	*Used to set-up for the boss level
	*@param int blevel the level for the boss
	**/
	public void nextBoss(int bLevel){
		remove(gameEngine);
		gameScreen(clevel,0);
		bossLevel = true;
	}

	/**
	*Allows a player to restart a level they have died upon, given that they still have lives
	**/
	public void retryLevel(){
		cLives--;
		remove(gameEngine);
		if(bossLevel==true){
			gameScreen(clevel,0);
		}else{
			gameScreen(clevel,aiMulti);
		}
	}

	/**
	*What happens when the player dies
	*@param int score the score to display upon death
	**/
	public void playerDied(int score){
		if(cLives>2){//quite a few lives left
			JOptionPane.showMessageDialog(this,"You Died, How Tragic! You only have " + (cLives-1) + " lives left!");
			retryLevel();
		}else if(cLives==2){//one life, grammar correct
			JOptionPane.showMessageDialog(this,"You Died, How Tragic! You only have " + (cLives-1) + " life left!");
			retryLevel();
		}else if(cLives==1){//last life
			JOptionPane.showMessageDialog(this,"You Died, How Tragic! This is your last life!");
			retryLevel();
		}else{//dead
			JOptionPane.showMessageDialog(this,"You Died, How Tragic! You are out of lives, Game Over!!!");
			checkScore(score);
			HighScores scores = new HighScores();
			JOptionPane.showMessageDialog(this,"Your Score: "+score+"\n\nHIGHSCORES\n"+scores.getHighScores());
			cLives = maxLives; 
			aiMulti = 1.2;
			clevel = 1;
			remove(gameEngine);
			mainScreen();
			repaint();
			bossLevel = false;
		}
	}

	/**
	*What happens when the player beats a level
	**/
	public void playerWon(){
		cLives = maxLives;
		JOptionPane.showMessageDialog(this,"You Survived, How Surprising! Lets see how you do next time!");
		if(bossLevel==false){
			bossLevel = true;
			nextBoss(clevel);		
		}else{
			bossLevel = false;
			nextLevel(clevel);
		}
	}

	/**
	*Sets player score
	*@param int s the score input to set
	**/
	public void setScore(int s){
		score = s;
	}

	/**
	*Views the highscores of the HighScores.txt file in sources
	**/
	public void viewScores(){
		HighScores scores = new HighScores();
		JOptionPane.showMessageDialog(this,scores.getHighScores());
	}

	/**
	*Checks to see if the score achieved is a High Score.
	*If it is, it then passes it through the HighScore class, in order
	*to write the highscore to the HighScore.txt file.
	**/
	public void checkScore(int nscore){
		HighScores scores = new HighScores();
		if(scores.isHighScore(nscore)){
			String name = "";
			try{
				name = JOptionPane.showInputDialog(this,"New High Score! Enter name: ");
				name = name.split(" ")[0];//makes sure there is no spacing in the name
				if(!name.equals("")){
					scores.addScore(name,nscore);
					scores.writeHighScores();
				}else{//nothing entered
					JOptionPane.showMessageDialog(this,"High Score not recorded!");
				}
			}catch(NullPointerException e){//on cancel
				JOptionPane.showMessageDialog(this,"High Score not recorded!");
			}
			
		}
	}
}
