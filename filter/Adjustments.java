package org.filter;
import java.awt.*;
import java.awt.image.*;


public abstract class Adjustments{
	int width,height;
	Color []colors;
	
	Adjustments(BufferedImage image){
		ImageData id=new ImageData(image);
		width=(id.getImage()).getWidth();
		height=(id.getImage()).getHeight();
		colors=id.getColors();
	}
	
	public abstract BufferedImage tuneUp(BufferedImage newImg, int v);
	
}

class Contrast extends Adjustments {
	
	public Contrast(BufferedImage image){
		super(image);
	}
	
	
	public BufferedImage tuneUp(BufferedImage newImg,int adjust){
		
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,a,i=0;
		double tr,tg,tb;
		
		double c, t;
		
		Color rgba;
		
		c=1.0+(Utils.normalize(adjust,1,100));  
		t=(1.0-c)/2.0;

		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				a=rgba.getAlpha();

				tr=((tr*c)+t);
				tg=((tg*c)+t);
				tb=((tb*c)+t);

				r=Utils.clamp(tr);
				g=Utils.clamp(tg);
				b=Utils.clamp(tb);
				
				newImg.setRGB(x,y,(new Color(r,g,b,a)).getRGB());
			}
		}
		return newImg;
	}
}

class Brightness extends Adjustments{
	
	public Brightness(BufferedImage image){
		super(image);
	}
	
public BufferedImage tuneUp(BufferedImage newImg, int adjust){
		
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,a,i=0;
		
		double tr, tg,tb;
		double t=Utils.normalize(adjust,1,100);
	
		Color rgba;

		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];

				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				a=rgba.getAlpha();

				tr=((tr/255)+t)*255;
				tg=((tg/255)+t)*255;
				tb=((tb/255)+t)*255;

				r=Utils.clamp(tr);
				g=Utils.clamp(tg);
				b=Utils.clamp(tb);

				newImg.setRGB(x,y,(new Color(r,g,b,a)).getRGB());
			}
		}
		return newImg;
	}
}
