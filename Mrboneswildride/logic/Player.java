package Mrboneswildride.logic;

/**
*Extends the GameCharacter class to add Player specific methods and variables 
**/
public class Player extends GameCharacter {
	private int mana;
	private int money;
	
	/**
	*The default constructor
	**/
	public Player() {//adds player features to the GameCharacter
		super();
		setAppearance('#');
		setxcoord(230);
		setycoord(230);
		setSpeed(5);
		setMana(100);
		setHitBox(15);
		setAiType(-1);
	}
	
	/**
	*A copy constructor which copies everything it is passed
	*@param Player toCopy the Player you want to copy
	**/ 
	public Player(Player toCopy){
		setHealth(toCopy.getHealth());
		setAttack(toCopy.getAttack());
		setDefense(toCopy.getDefense());
		setSpeed(toCopy.getSpeed());
		setHitBox(toCopy.getHitBox());
		setxcoord(toCopy.getxcoord());
		setycoord(toCopy.getycoord());
		setDirection(toCopy.getDirection());
	}
	
	/**
	*@return Returns player mana
	*/
	public int getMana(){
		return mana;
	}
	/**
	 *@return Returns player money, currently not in use
	 */
	public int getMoney(){
		return money;
	}
	/**
	 *@param m Value to set mana too
	 */
	public void setMana(int m){
		mana=m;
	}
	/**
	 *@param m value to set money too
	 */
	public void setMoney(int m){
		money = m;
	}
	/**
	 *@param s amount to add to score
	 */
	public void addScore(int s){
		setScore(getScore()+s);
	}

	//Testing Code
	public static void main(String[] args){
		Player testPlayer = new Player();
		System.out.println("**Testing code**\n");
		System.out.println("Player default variables values are: \n");
		System.out.println(testPlayer.toString());
	}
}
