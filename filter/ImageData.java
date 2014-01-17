package org.filter;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageData {

	private int width,height;
	private BufferedImage img=null,original;
	private Color colors[];

	public ImageData(File input){
		try
		{
			img = ImageIO.read(input);
			this.width = img.getWidth();
			this.height = img.getHeight();
		}

		catch(IOException e){
			System.out.print(e.getMessage());
		}
		//this.colors= new Color[width*height];
		//this.setColors(this.img);
	}
	
	public ImageData(BufferedImage image){
		img=image;
		this.width=image.getWidth();
		this.height=image.getHeight();
		this.colors= new Color[width*height];
		this.setColors(this.img);		
	}
	
	public void setOriginal(BufferedImage original){
		this.original= new BufferedImage(original.getWidth(), original.getHeight(),
				original.getType());
		this.original=original;
	}
	
	public BufferedImage getOriginal(){
		return this.original;
	}

	public void setColors(BufferedImage image){
		int width=this.width, height=this.height;
		int x,y,i=0;
		for(x=0;x<width; x++){
			for(y=0; y<height; y++){
				this.colors[i++]=new Color(image.getRGB(x,y),true);
			}
		}
	}

	public BufferedImage getImage(){
		return this.img;
	}
	
	public Color[] getColors(){
		return colors;
	}
}


