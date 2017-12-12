package Mrboneswildride.logic;

import Mrboneswildride.Config;
import java.util.Map;
import java.util.TreeMap;
import java.io.*;

/**
*Class for reading and writing to a highscore file of the game
*as well as returning those values for printing
**/
public class HighScores{

	private Map<Integer, String> map = new TreeMap<Integer, String>();
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	private File highScores = new File("Mrboneswildride"+Config.slash+"sources"+Config.slash+"HighScores.txt");

	/**
	*Default constructor
	*Attempts to read the file, then passes its values
	*to a treemap for sorting
	**/
	public HighScores(){
		try{
			reader = new BufferedReader(new FileReader(highScores));
			String text = null;
			
			while((text = reader.readLine()) != null){
				int index = text.indexOf(" ");
				String name = text.substring(0,index);
				name = name.trim();
				String score = text.substring(index+1);
				score = score.trim();
				int intScore = Integer.parseInt(score);
				map.put(intScore, name);
			}
	
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
				System.out.println("File not found, file will be created upon high score entry.");
			} catch (IOException e){
				e.printStackTrace();
			} finally{
				try{
					if (reader != null){
						reader.close();
					}
				} catch (IOException e){
				}
			}
	}

	/**
	*Returns the values from the HighScore file to be printed
	**/
	public String getHighScores(){
		String ret = "";
		for (int key: map.keySet()){
			ret += map.get(key)+": "+key+" \n";
		}
		return ret;
	}

	/**
	*Checks to see if a score entered is a high score or not
	*Returns true if it is a new high Score
	*Only ten scores are allowed
	*@param int score The score being checked
	**/
	public boolean isHighScore(int score){
		if(map.size() < 10){
			return true;
		}else{
			for(int key: map.keySet()){
				if(score > key){
					return true;
				}
			}
		}
		return false;
	}

	/**
	*If a score is a high score, this will add that score to the HighScore list, 
	*then remove the lowest value if there are more than ten scores
	*@param String name the name of the scoreholder
	*@param int score the score
	**/
	public void addScore(String name, int score){
		if (isHighScore(score)){
			map.put(score, name);
			if(map.size() > 10){
				int lowest = 2147483647;
				for(int key: map.keySet()){
					if(key < lowest){
						lowest = key;
					}
				}
				map.remove(lowest);
			}
		}
	}
	
	/**
	*Writes the highscore tree map the file HighScores.txt
	**/
	public void writeHighScores(){
		try{
			FileWriter writer = new FileWriter(highScores, false);
			PrintWriter printer = new PrintWriter(writer);
			for(int key:map.keySet()){
				printer.println(map.get(key)+" "+key);
			}
			printer.close();
			writer.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	*Test Code
	**/
	public static void main(String args[]){
		HighScores h = new HighScores();
		h.addScore("CTDFS", 103736);
		h.addScore("ARAR", 187336370);
		h.writeHighScores();
		System.out.print(h.getHighScores());
	}
}
