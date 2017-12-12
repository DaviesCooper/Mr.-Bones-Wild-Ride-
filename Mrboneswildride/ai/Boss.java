package Mrboneswildride.ai;

import Mrboneswildride.logic.*;
import Mrboneswildride.gui.*;
import java.util.Random;

/**
*Ai child class used for creating and intialising Bosses
**/
public class Boss extends Ai{

	private Random rand = new Random();
	private int count = 0;
	private int timerTicks = 0;
	private int reload = 0;
	private int reloadNum = 21;
	private int countNum = 200;
	private int index = 0;
	private int lower1;
	private int lower2;
	private int lower3;
	private int lower4;
	private int lower5;
	private int lower6;
	private int lower7;
	private int lower8;
	private int lower9;
	private int upper1;
	private int upper2;
	private int upper3;
	private int upper4;
	private int upper5;
	private int upper6;
	private int upper7;
	private int upper8;
	private int upper9;
	
	/**
	*First Constructor; initializes Boss
	*@param nchar takes player pass is to super class
	*@param level used to modify Boss health
	**/	
	public Boss(Player nchar, int level){
		super(nchar);
		setAiType(3);
		setTriggerDistance(1000);
		setAttack(10);
		setHealth(450+(50*level));
		setMaxHealth(500);
		setHitBox(32);
		setScore(100);
		setSpeed(3);
		setMovement(0,0);
		setCharge(0,0);
		setUnlimited(0,0);
		setShooting(0,0);
		setBigShot(0,0);
		setExploding(0,0);
		setSpawnGhosts(0,0);
		setManaBurn(0,0);
		setRotate(0,0);
	}

	/**
	*Determines how a boss will act based on a random index from 0-99
	*
	*@param gamep allows to direct access to gamePanel for adding projectiles
	*/
	public void behavior(GamePanel gamep){
		GamePanel gamepanel = gamep;
		timerTicks++;
		//When count equals zero a new random index is choosen
		if(count==0){
			index = rand.nextInt(100);
		}//Moves towards player(1)
		if(index>=lower1 && index<=upper1){
			move(getXMove()*getSpeed(),getYMove()*getSpeed());
		}//Charges towards player(2)
		if(index>=lower2 && index<=upper2){
			if(count>25){
				move(getXMove()*getSpeed()*2,getYMove()*getSpeed()*2);
			}count=count+4;
		}//Gives boss super fast reloading(3)
		if(index>=lower3 && index<=upper3){
			reload=reloadNum;
		}//Shoots at player(4)
		if(index>=lower4 && index<=upper4){
			if (reload>20){
				gamepanel.addProjectile(shootPlayer());
				reload=0;
			}reload++;
		}//Shoots high damage slow moving projectiles(5)
		if(index>=lower5 && index<=upper5){
			if (reload>20){
				Projectile p =shootPlayer();
				p.setDamage(100);
				p.setSpeed(5);
				gamepanel.addProjectile(p);
				reload=0;
			}reload++;
		}//Uses explodeAttack(6)
		if(index>=lower6 && index<=upper6){
			if (reload>20){
				explodeAttack(gamepanel, this);
				reload=0;
			}reload++;
		}//Makes boss drain mana of player(7)
		if(index>=lower7 && index<=upper7){
			if(player.getMana()>25 && player.getAppearance()!='O'){
				player.setMana(player.getMana()-1);
			}
		}//The boss spawns some ghosts(8)
		if(index>=lower8 && index<=upper8){
			if(count%100==0){
				Ghost nghost = new Ghost(player);
				nghost.setxcoord((double)this.getxcoord());
				nghost.setycoord((double)this.getycoord());
				gamepanel.addAI((Ai)nghost);
			}count++;	
		}//Rotates boss direction(9). Used solely for last boss.
		if(index>lower9 && index<upper9){
			if(timerTicks%20==0){
				if(getDirection()=="Right")
					setDirection("Left");
				else if(getDirection()=="Left")
					setDirection("Up");
				else if(getDirection()=="Up")
					setDirection("Down");
				else
					setDirection("Right");
			}
		}count++;
		if(count>countNum){
			reload = 0;
			count = 0;
		}
	}	
	
	/**
	*Setter Methods
	*/
	public void setReloadNum(int r){
		reloadNum=r;
	}
	public void setCountNum(int c){
		countNum=c;
	}
	public void setMovement(int l,  int u){
		lower1=l;
		upper1=u;	
	}
	public void setCharge(int l,  int u){
		lower2=l;
		upper2=u;	
	}
	public void setUnlimited(int l,  int u){
		lower3=l;
		upper3=u;	
	}
	public void setShooting(int l,  int u){
		lower4=l;
		upper4=u;	
	}
	public void setBigShot(int l,  int u){
		lower5=l;
		upper5=u;	
	}
	public void setExploding(int l,  int u){
		lower6=l;
		upper6=u;	
	}
	public void setSpawnGhosts(int l,  int u){
		lower7=l;
		upper7=u;	
	}
	public void setManaBurn(int l,  int u){
		lower8=l;
		upper8=u;	
	}
	public void setRotate(int l,  int u){
		lower9=l;
		upper9=u;	
	}
	
}
