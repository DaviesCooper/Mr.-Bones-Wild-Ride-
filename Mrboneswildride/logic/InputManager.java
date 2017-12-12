package Mrboneswildride.logic;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/**
*Listens for key inputs from the keyboard, then stores them in a cache, or can be
*directly accessed for use by the engine
**/
public class InputManager implements KeyListener,MouseListener, MouseMotionListener{
	public static InputManager singleton = null;
 	
	/**
	*Default constructor used to create a singleton
	**/
	public InputManager() {
		singleton = this;
	}
	//stores key input in multiple boolean arrays
	public int keys[] = new int[256];
	private String keyCache = "";	
	private boolean[] keyIsUp = new boolean[256];
	private boolean[] keyIsDown = new boolean[256];
	private boolean keyPressed=false;
	private boolean keyReleased=false;
	private boolean shoot = false;
	private MouseEvent mousePos = null;
    
	/**
	*gets key pressed, checks to see if it is valid and adds it to the appropriate array
	*@param KeyEvent e the event in which we grab the key code for
	**/
	public void keyPressed(KeyEvent e) {
		//test System.out.println("InputManager: A key has been pressed code=" + e.getKeyCode());
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
          		keys[e.getKeyCode()] = (int) System.currentTimeMillis();
          		keyIsDown[e.getKeyCode()] = true;
      			keyIsUp[e.getKeyCode()] = false;
           		keyPressed = true;
           		keyReleased = false;
		}
	}

 	/**
	*gets key released, checks to see if it is valid and adds it to the appropriate array
	*@param KeyEvent e the event in which we grab the key code for
	**/
	public void keyReleased(KeyEvent e) {
		//test System.out.println("InputManager: A key has been released code=" + e.getKeyCode());
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = 0;
			keyIsUp[e.getKeyCode()] = true;
			keyIsDown[e.getKeyCode()] = false;
			keyPressed = false;
			keyReleased = true;
		}
	}

	//methods for checking if specific keys have been pressed
	/**
	 *Checks to see if a designated key is pressed
	 *@param key Key to check to see if it is down
	 *@return true if key is down, false otherwise
	 */
	public boolean isKeyDown(int key){
  	     	return keyIsDown[key];
	}
    /**
	 *Checks to see if a designated key is not pressed
	 *@param key Key to check to see if it is up
	 *@return true if key is up, false otherwise
	 */
	public boolean isKeyUp(int key){
	    	return keyIsUp[key];
	}
	/**
	 *Adds pressed key to cache
	 *@param e KeyEvent to add to keyCache
	 */
	public void keyTyped(KeyEvent e) {
	       	keyCache += e.getKeyChar();
	}
	/**
	 *@return keycache as string
	 */
	public String getKeyCache(){
		return keyCache;
	}
	/**
	 *@return true if any key is pressed
	 */
	public boolean isAKeyDown(){
		return keyPressed;
	}

	/**
	*Resets the key values, and clears the keycache
	**/
	public void update() {
		keyIsUp = new boolean[256];
		keyIsDown = new boolean[256];
		keyReleased = false;
		if( keyCache.length() > 1024 ) {
			keyCache = "";
		}
	}
	/**
	 *Returns true if player clicked the mouse
	 */
	public boolean getShot(){
		return shoot;
	}
	/**
	 *@return returns mouse position
	 */
	public MouseEvent getMouseCoords(){
		return mousePos;
	}
	/**
	 *Not used
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	/**
	 *Sets shoot to true and gets the mouse position of the click
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		shoot = true;
		mousePos = e;
	}
	/**
	 *sets shoot to false and resets the mouse position to null
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		shoot = false;
		mousePos = null;
	}
	/**
	 *not used
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}
	/**
	 *not used
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	/**
	 *Sets MousePos for shooting if the mouse moves
	 */
	@Override
	public void mouseMoved(MouseEvent e){
		mousePos = e;
	}
	/**
	 *Sets MousePos for shooting if the mouse moves 
	 */
	@Override
	public void mouseDragged(MouseEvent e){
		mousePos = e;
	}
}
