package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
/**
*class used to create bitmasking values of each tile
*in order to later apply visual bitmaps
**/
public class BitMasking{
	
	//Int arrray used to store the final values
	private int[][] BitMask = new int[0][0];
	//Int array which is used to calculate the bitmask values
	private int[][] templateTileMap = new int[0][0];
	//Unused technically. Originally used for each level.
	//A better way was found to do this
	private int bitMaskType;
	
	/*
	*Used to initialise the tiles for alterating
	*@param int[][] tiles from the tilemap creation
	**/
	public void setBitMask(int[][] tiles){
		BitMask = tiles;
	}
	
	/*
	*returns the bitmask
	**/
	public int[][] getBitMask(){
		return BitMask;
	}
	
	/**
	*Sets the template tilemap to use for calculating
	*@param int[][] the input tilemap
	**/
	public void setTemplateTileMap(int[][] template){
		templateTileMap = template;
	}

	/**
	*returns the template for use
	**/
	public int[][] getTemplateTileMap(){
		return templateTileMap;
	}

	/**
	*outdated, and not used, but was used to declare the level the player was on
	**@param int input is the current level
	**/
	public void setBitMaskType (int type){
		bitMaskType = type;
	}

	/**
	*returns the level the character is on
	**/
	public int getBitMaskType(){
		return bitMaskType;
	}
	
	/**
	*Generates a tilemap for the current level to exist in
	*@param int the level the player is on
	**/
	public void generateTileMap(int level){
		TileMap currentTileMap = new TileMap();
		currentTileMap.finalArray(level);
		setBitMaskType(currentTileMap.getCurrentLevel().getBlockType());
		setTemplateTileMap(currentTileMap.getFinalArray());
	}

	/**
	*Using the tilemap, created a bitmask which declares the value
	*for each block of the tilemap. Then sets the bitmask variable to this new array.
	*@param int the level the user wants to generate a level for
	**/
	public void generateBitMask(int level){
		generateTileMap(level);
		int[][] currentMap = getTemplateTileMap();
		int[][] newMap = new int[currentMap.length][currentMap.length];
		for(int j = 0; j<currentMap.length; j++){
			for(int i = 0; i<currentMap[j].length; i++){
				int tileValue = 0;
				if(i==0||j==0||j==currentMap.length||i==currentMap[j].length){
					newMap[j][i] = 0;
				}
				else if(currentMap[j][i] == 0){
					newMap[j][i] = 0;
				}
				else if (currentMap[j][i] == 1){
					if (currentMap[j][i-1] == 1){
						tileValue += 1;
					}
					if (currentMap[j][i+1] == 1){
						tileValue += 4;
					}
					if (currentMap[j-1][i] == 1){
						tileValue += 2;
					}
					if (currentMap[j+1][i] == 1){
						tileValue += 8;
					}
					newMap[j][i] = tileValue;
				}
			}
		}
		setBitMask(newMap);
	}

	/**
	*Test code
	**/
	public static void main(String[] args){
		BitMasking b = new BitMasking();
		b.generateBitMask(1);
		int[][]temp = b.getBitMask();
		for (int j = 0; j < temp.length; j++){
			for(int i = 0; i < temp[j].length; i++){
				if (temp[j][i] == 0 || temp[j][i] == -1){
					System.out.print("[ ]");
				}
				else if(temp[j][i]>10){
					System.out.print(temp[j][i]+" ");
				}
				else{
					System.out.print(temp[j][i]+"  ");
				}
			}
			System.out.println();
		}
	}
}
