package Mrboneswildride.logic;

import Mrboneswildride.gui.*;
import Mrboneswildride.logic.*;

import java.util.Random;
import java.util.ArrayList;

import java.util.Random;
import java.util.ArrayList;

/**
*Class which creates the rooms and hallways to fill the tilemap
**/
public class Level{

	//Variables for deciding the size of the dungeon,
	//as well as the maximum and minimum sizes for
	//the rooms which will be placed in the dungeon.
	//Block type is for designing based on the level
	//Block size is the amount of pixels per block space.
	private int dungeonWidth;
	private int dungeonHeight;
	private int maxXSize;
	private int maxYSize;
	private int minXSize;
	private int minYSize;
	private int blockType;
	private int blockSize;

	//Constructor for given variables
	public Level(int bSize){
		setBlockSize(bSize);
	}

	/*
	GETTER / SETTERS v
	*/
	/**
	 *@param width Value to set dungeon width too
	 */
	public void setDWidth(int width){
		dungeonWidth = width;
	}
	/**
	 *@param height Value to set dungeon height too
	 */
	public void setDHeight(int height){
		dungeonHeight = height;
	}
	/**
	 *@param minX Value to set minXSize too
	 */
	public void setMinX(int minX){
		minXSize = minX;
	}
	/**
	 *@param minY Value to set minYSize too
	 */
	public void setMinY(int minY){
		minYSize = minY;
	}
	/**
	 *@param maxX Value to set maxXSize too
	 */
	public void setMaxX(int maxX){
		maxXSize = maxX;
	}
	/**
	 *@param maxY Value to set maxYSize too
	 */
	public void setMaxY(int maxY){
		maxYSize = maxY;
	}
	/**
	 *@param bType Value to set blockType too
	 */
	public void setBlockType(int bType){
		blockType = bType;
	}
	/**
	 *@param bSize Value to set blockSize too
	 */
	public void setBlockSize(int bSize){
		blockSize = bSize;
	}
	/**
	 *@return dungeonWidth
	 */
	public int getDWidth(){
		return dungeonWidth;
	}
	/**
	 *@return dungeonHeight
	 */
	public int getDHeight(){
		return dungeonHeight;
	}	
	/**
	 *@return minXSize
	 */
	public int getMinX(){
		return minXSize;
	}
	/**
	 *@return minYSize
	 */
	public int getMinY(){
		return minYSize;
	}
	/**
	 *@return maxXSize
	 */
	public int getMaxX(){
		return maxXSize;
	}
	/**
	 *@return maxYSize
	 */
	public int getMaxY(){
		return maxYSize;
	}
	/**
	 *@return blockSize
	 */
	public int getBlockSize(){
		return blockSize;
	}
	/**
	 *@return blockType
	 */
	public int getBlockType(){
		return blockType;
	}
	/*
	GETTER / SETTERS ^
	*/

	//Array List set for holding all the rooms of the level
	private ArrayList<Room> rooms = new ArrayList<Room>();

	//Initialising the random generator
	private Random rand = new Random();

	/**
	*Function to check whether or not two rooms intersect. If they do, returns true
	*@param Room room1 the first room
	*@param Room room2 the room to check for intersection with
	**/
	public boolean intersect(Room room1, Room room2){
		if (room1.getLeft() <= room2.getRight() &&
			room1.getRight() >= room2.getLeft() &&
			room1.getTop() <= room2.getBottom() &&
			room1.getBottom() >= room2.getTop()){
			return true;
		} else {
			return false;
		}
	}

	//Getter for the Array list. No setter needed however
	public ArrayList<Room> getRoomList(){
		return rooms;
	}
	
	/**Generates rooms randomly within the level.
	*Then checks them for intersection.
	*If they don't, places the currently looked at room,
	*And places them in the level.
	*Repeats until the given number of rooms to be placed is fullfilled
	*@param int numberOfRooms the number of rooms you want placed
	**/
	public void placeRooms(int numberOfRooms){
		for (int i = 0; i < numberOfRooms; i = rooms.size()){
			int w  = rand.nextInt(getMaxX()-getMinX()+1)+getMinX();
			int h = rand.nextInt(getMaxY()-getMinY()+1)+getMinY();
			int t = rand.nextInt(getDHeight()-h-10)+5;
			int l = rand.nextInt(getDWidth()-w-10)+5;
			Room currentRoom = new Room(w,h,t,l);

			boolean failed = false;

			if (rooms.size() > 0){
				for (Room otherRoom: rooms){
					if (intersect(otherRoom, currentRoom)){
						failed = true;
						break;
					}
				}
			
				if (!failed){
					rooms.add(currentRoom);
				}
			} else{
				rooms.add(currentRoom);
			}						
		}
	}
	
	
	/**
	*Connects all the rooms via corridors
	**/
	public void placeCorridors(){
		ArrayList<Room> corridors = new ArrayList<Room>();
		for (int i = 0; i<rooms.size(); i++){
			Room currentRoom = rooms.get(i);
			int currentX = currentRoom.getCentreX();
			int currentY = currentRoom.getCentreY();


			Room connecting = null;
			if(i==rooms.size()-1){
				connecting = rooms.get(0);
			} else{
				connecting = rooms.get(i+1);
			}


			int connectingX = connecting.getCentreX();
			int connectingY = connecting.getCentreY();

			//Making a horizontal corridor
			Room HConnect = new Room(Math.abs(currentX-connectingX), 2, currentY,Math.min(currentX, connectingX));
			corridors.add(HConnect);

			//Making a vertical corridor
			Room VConnect = new Room(2, Math.abs(currentY-connectingY), Math.min(currentY, connectingY), currentX);
			corridors.add(VConnect);
		}
		for (int i =0; i<2; i++){
			for (Room currentRoom: rooms){
				int currentX = currentRoom.getCentreX();
				int currentY = currentRoom.getCentreY();
	
				int next = rand.nextInt(rooms.size());
				Room connecting = rooms.get(next);
				int connectingX = connecting.getCentreX();
				int connectingY = connecting.getCentreY();

				//Making a horizontal corridor
				Room HConnect = new Room(Math.abs(currentX-connectingX), 2, currentY,Math.min(currentX, connectingX));
				corridors.add(HConnect);

				//Making a vertical corridor
				Room VConnect = new Room(2, Math.abs(currentY-connectingY), Math.min(currentY, connectingY), currentX);
				corridors.add(VConnect);
			}
		}
		rooms.addAll(corridors);
	}

	//Test class
	public static void main(String args[]){
		Level l = new Level(16);
		l.setDWidth(100);
		l.setDHeight(100);
		l.setMinX(5);
		l.setMaxX(7);
		l.setMinY(5);
		l.setMaxY(7);
		l.placeRooms(15);
		//l.placeCorridors();
		ArrayList<Room> roomsList = l.getRoomList();
		for (Room room: roomsList){
			System.out.println("CentreX = "+room.getCentreX()+
			" CentreY = "+room.getCentreY()+
			" Width = "+room.getWidth()+
			" Height = "+room.getHeight()+
			" Left = "+room.getLeft()+
			" Top = "+room.getTop());
		}
	}


}
