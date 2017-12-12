package Mrboneswildride.gui;

import Mrboneswildride.logic.*;
import Mrboneswildride.Config;

import javax.imageio.ImageIO;  
import java.awt.image.BufferedImage;  
import java.io.*;  
import java.awt.*;  
/**
*Splits an image into nine pieces, which are used for the bitmasking index
**/ 
public class ImageSpliter {  
	/**
	*Default constructor which does nothing
	**/
	public ImageSpliter(){        
	}

	/**
	*Takes an image source, and splits it into 9 pieces
	*@param String source the source file to be split
	*@try tries to load the source file
	**/
	public BufferedImage[] getParts(String source){
		BufferedImage image = null;
		try{
			File file = new File(source);  
			FileInputStream fis = new FileInputStream(file);  
			image = ImageIO.read(fis); //reading the image file  
		}catch(IOException e){
			System.out.println("Error reading file");
		}
		int rows = 3; //You should decide the values for rows and cols variables  
		int cols = 3;  
		int chunks = rows * cols;   
		int chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
		int chunkHeight = image.getHeight() / rows;  
		int count = 0;  
		BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks  
		for (int x = 0; x < rows; x++) {  
			for (int y = 0; y < cols; y++) {  
				//Initialize the image array with image chunks  
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());  
				// draws the image chunk  
				Graphics2D gr = imgs[count++].createGraphics();  
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, 
				chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
				gr.dispose();  
			}  
		}
		return imgs;
        
	}
}  
