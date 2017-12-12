package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
import Mrboneswildride.logic.*;

import java.util.ArrayList;

/**
*Class to generate the actual map using the rooms made in the level class
**/
public class TileMap{

	/**
	*Size of the dungeon used to create the array
	**/
	private int arrayWidth;
	private int arrayHeight;
	private ArrayList<Room> roomList;
	private Level currentLevel = new Level(16);
	private int[][] finalArray = new int[0][0];

	/**
	*Sets the width of the level
	*@param in aWidth the width of the level
	**/
	public void setAWidth(int aWidth){
		arrayWidth = aWidth;
	}

	/**
	*Sets the height of the level
	*@param int aHeight the height of the level
	**/
	public void setAHeight(int aHeight){
		arrayHeight = aHeight;
	}

	public void setRoomList(ArrayList<Room> arrayList){
		roomList = arrayList;
	}

	public void setFinalArray(int[][] array){
		finalArray = array;
	}

	public int[][] getFinalArray(){
		return finalArray;
	}

	public int getAWidth(){
		return arrayWidth;
	}

	public int getAHeight(){
		return arrayHeight;
	}
	public ArrayList<Room> getRoomList(){
		return roomList;
	}
	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	/**
	*Sets the design for the dungeon to be created using certain parameters
	*@param int level the current level to be played
	**/
	public void setCurrentLevel(int level){
		if (level < 7 && level != 0){
			currentLevel.setDWidth(60+level);
			currentLevel.setDHeight(60+level);
			currentLevel.setMinX(level + 5);
			currentLevel.setMaxX(level + 7);
			currentLevel.setMinY(level + 5);
			currentLevel.setMaxY(level + 7);
			currentLevel.setBlockType(6);
			currentLevel.placeRooms(level + 4);
			currentLevel.placeCorridors();
		} else if (level >= 7){
			currentLevel.setDWidth(75);
			currentLevel.setDHeight(75);
			currentLevel.setMinX(5);
			currentLevel.setMaxX(12);
			currentLevel.setMinY(5);
			currentLevel.setMaxY(12);
			currentLevel.setBlockType(6);
			currentLevel.placeRooms(15);
			currentLevel.placeCorridors();
		} else if (level == 0){
			currentLevel.setDWidth(40);
			currentLevel.setDHeight(40);
			currentLevel.setMinX(20);
			currentLevel.setMaxX(20);
			currentLevel.setMinY(20);
			currentLevel.setMaxY(20);
			currentLevel.setBlockType(6);
			currentLevel.placeRooms(1);
			
		}
	}

	/**
	*Sets all the variables for the level to be generated
	**/
	public void createMap(int level){
		setCurrentLevel(level);	
		setAWidth(currentLevel.getDWidth());
		setAHeight(currentLevel.getDHeight());
	}


	/**
	*Creates the final array of the tilemap
	*@param int level the level to be generated
	**/
	public void finalArray(int level){
		createMap(level);
		int[][] temp = new int [getAHeight()][];
		setRoomList(getCurrentLevel().getRoomList());
		for (int i = 0; i < getAHeight(); i++){
			temp[i] = new int [getAWidth()];
			for (int j = 0; j < getAWidth(); j++){
				temp[i][j] = 0;
			}
		}

		for (Room currentRoom : getRoomList()){
			for(int i = 0; i < currentRoom.getHeight(); i++){
				for(int j = 0; j < currentRoom.getWidth(); j++){
					/*if (currentRoom.getBottom() > m.getAHeight()){
						currentRoom.setTop(m.getAHeight()-currentRoom.getHeight());
					}
					if (currentRoom.getRight() > m.getAWidth()){						
						currentRoom.setLeft(0);
					}
					*/
					temp[currentRoom.getTop()+i][currentRoom.getLeft()+j] = 1;
					//temp[currentRoom.getTop()+i][currentRoom.getLeft()+j] = 0;
					//This is to be used for actual tilemapping instead of testing
				}
			}
		}
		setFinalArray(temp);

	}

	/**
	*Test code
	**/
	public static void main(String[] args){
		TileMap m = new TileMap();
		m.finalArray(1);
		int[][] test = m.getFinalArray();
		for (int i = 0; i < m.getAHeight(); i++){
			for( int j = 0; j < m.getAWidth(); j++ ){
				if( test[ i ][ j ] == 0 ){
					System.out.print("[]");
				}else{
					//System.out.print(temp[ i ][ j ]);
					System.out.print(test[i][j]+" ");
				}
			}
			System.out.println();
		}
	}
			
}
