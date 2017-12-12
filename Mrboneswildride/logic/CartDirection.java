package Mrboneswildride.logic;


/**
*Used to determine cartesian coordinates for enemies to find the player
**/
public class CartDirection{

	private int initX;
	private int initY;
	private int fnlX;
	private int fnlY;

	/**
	*Default constructor used to initialise takes four int arguments
	*@param int initX the xcoord of the enemy
	*@param int initY the ycoord of the enemy
	*@param int fnlX the xcoord of the player
	*@param int fnlY the ycoord of the player
	**/
	public CartDirection(int initX,int initY,int fnlX, int fnlY){
		this.initX=initX;
		this.initY=initY;
		this.fnlX=fnlX;
		this.fnlY=fnlY;
	}

	/**
	*returns the double value of the cartesian x coordinate
	**/
	public double getCartX(){
		double xDif = fnlX-initX;
		double yDif = fnlY-initY;
		double hyp = Math.sqrt((xDif * xDif) + (yDif * yDif));
		return (xDif/hyp);	
	}

	/**
	*returns the double value of the cartesian x coordinate
	**/
	public double getCartY(){
		double xDif = fnlX-initX;
		double yDif = fnlY-initY;
		double hyp = Math.sqrt((xDif * xDif) + (yDif * yDif));
		return (yDif/hyp);
	}
	
	
}
