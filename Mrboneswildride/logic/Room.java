package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
import Mrboneswildride.logic.*;

/**
*Creates the size and shape of each room to be placed in the map array
**/
public class Room{
	
	/**Values for the rooms to be placed per level.
	*Centre is used for creating corridors.
	*blockValue is used for bitmasking.
	**/
	private int width;
	private int height;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int centreX;
	private int centreY;
	private int blockValue;

	/**
	*Constructor which takes the dimensions of the room
	*@param int width the width of the desired room
	*@param int height the height of the desired room
	*@param int top the top of the room
	*@param int left the left of the room
	**/
	public Room(int Width,int Height,int Top,int Left){
		setWidth(Width);
		setHeight(Height);
		setTop(Top);
		setBottom(Top+Height);
		setLeft(Left);
		setRight(Left+Width);
		setCentreX((Left+Width+Left)/2);
		setCentreY((Top+Height+Top)/2);
	}

	/**
	*sets room width
	*@param int width the width of the room
	**/
	public void setWidth(int Width){
		width = Width;
	}

	/**
	*sets room height
	*@param int height the height of the room
	**/
	public void setHeight(int Height){
		height = Height;
	}

	/**
	*sets room top
	*@param int top the y coord of the top of the room
	**/
	public void setTop(int Top){
		top = Top;
	}

	/**
	*sets bottom
	*@param int bottom the y coord of the bottom of the room
	**/
	public void setBottom(int Bottom){
		bottom = Bottom;
	}

	/**
	*sets left
	*@param int left the x coord of the left of the room
	**/
	public void setLeft(int Left){
		left = Left;
	}
	
	/**
	*sets right
	*@param the x coord of the right of the room
	**/
	public void setRight(int Right){
		right = Right;
	}

	/**
	*sets centre x coord
	*int CentreX the x coord of the centre of the room
	**/
	public void setCentreX(int CentreX){
		centreX = CentreX;
	}

	/**
	*sets centre y coord
	*int CentreX the y coord of the centre of the room
	**/
	public void setCentreY(int CentreY){
		centreY = CentreY;
	}
	
	/**
	*Sets the block value
	*@param int bValue no longer used
	**/
	public void setBlockValue(int bValue){
		blockValue = bValue;
	}

	/**
	*Returns room width
	**/
	public int getWidth(){
		return width;
	}

	/**
	*Returns room height
	**/
	public int getHeight(){
		return height;
	}

	/**
	*Returns the y coord of the top of the room
	**/
	public int getTop(){
		return top;
	}

	/**
	*Returns the y coord of the bottom of the room
	**/
	public int getBottom(){
		return bottom;
	}

	/**
	*Returns the x coord of the left of the room
	**/
	public int getLeft(){
		return left;
	}
	
	/**
	*Returns the x coord of the right of the room
	**/
	public int getRight(){
		return right;
	}
	
	/**
	*Returns the x coord of the centre of the room
	**/
	public int getCentreX(){
		return centreX;
	}

	/**
	*Returns the y coord of the centre of the room
	**/
	public int getCentreY(){
		return centreY;
	}

	/**
	*Returns the block value of the room (no longer used)
	**/
	public int getBlockValue(){
		return blockValue;
	}
}
