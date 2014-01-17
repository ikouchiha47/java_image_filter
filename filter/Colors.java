package org.filter;
import java.awt.*;
import java.awt.image.*;

abstract class Colors {
	int width, height;
	Color []colors;
	
	Colors(BufferedImage image){
		ImageData id=new ImageData(image);
		width=(id.getImage()).getWidth();
		height=(id.getImage()).getHeight();
		colors=id.getColors();
	}
	
	abstract BufferedImage tuneUp(BufferedImage image, double v);
}


class Colorize extends Colors{
	
	private BufferedImage src;
	Colorize(BufferedImage image){
		super(image);
		this.src=image;
	}
	
	public BufferedImage tuneUp(BufferedImage img, double v){
	return null;
	}
	
	public BufferedImage tuneUp(BufferedImage newImg, Color c){
		int iw=this.width , ih=this.height;
		int x,y,r,g,b,i=0;
		int nr,ng,nb;
		double stepR, stepG, stepB;
		int [][]deltacolors = new int[256][3];

		Color rgba;
		BufferedImage tmp= new BufferedImage(iw,ih,BufferedImage.TYPE_INT_ARGB);
		
		nr=c.getRed();
		ng=c.getGreen();
		nb=c.getBlue();
		
		tmp=(new Grayscale(this.src)).convertTo(newImg);
		
		stepR=(255.0-nr)/255.0; stepG=(255.0-ng)/255.0; stepB=(255.0-nb)/255.0;
		for(x=0; x<=255; x++){
			deltacolors[i][0]=Utils.clamp(nr+(x*stepR));
			deltacolors[i][1]=Utils.clamp(ng+(x*stepG));
			deltacolors[i][2]=Utils.clamp(nb+(x*stepB));
		}
		ImageData id=new ImageData(tmp);
		Color ncolors[]=id.getColors();
		
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=ncolors[i++];
				r=rgba.getRed();
				g=rgba.getGreen();
				b=rgba.getBlue();
				
				r=deltacolors[r][0];
				g=deltacolors[g][0];
				b=deltacolors[b][0];
				newImg.setRGB(x,y,(new Color(r,g,b)).getRGB());
			}
		}
		return newImg;
	}
}

class Saturate extends Colors{
	
	Saturate(BufferedImage image){
		super(image);
	}
	
	public BufferedImage tuneUp(BufferedImage newImg, double s){
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,i=0;
		double tr,tg,tb,max;
		
		Color rgba;
		
		s*=-0.01;
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				r=rgba.getRed();
				g=rgba.getGreen();
				b=rgba.getBlue();
				tr=r; tg=g; tb=b;
				max=Utils.maxim(new int[]{r,g,b});
				
				if (tr != max) 
			        tr += (max - tr) * s;
				if (tg != max) 
			        tg += (max - tg) * s;
				if (tb != max) 
			        tb += (max - tb) * s;
				r=Utils.clamp(tr); g=Utils.clamp(tg); b=Utils.clamp(tb);
				
				newImg.setRGB(x, y, (new Color(r,g,b)).getRGB());
			}
		}
		return newImg;
	}
}

class Vibrance extends Colors{
	
	Vibrance(BufferedImage image){
		super(image);
	}

	public BufferedImage tuneUp(BufferedImage newImg, double adjust){
		
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,a,i=0;

		double tr, tg, tb, max,avg=0.0,amt=0.0;
		adjust *=-1.0;

		Color rgba;

		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=colors[i++];
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				a=rgba.getAlpha();

				max=Utils.maxim(new int[]{ (int)tr, (int)tg, (int)tb});
				avg=(tr+tg+tb)/3.0;
				amt=((Math.abs(max-avg)*2/255)*adjust)/100;

				if(tr!=max)
					tr+=(max-tr)*amt;
				if(tg!=max)
					tg+=(max-tg)*amt;
				if(tb!=max)
					tb+=(max-tb)*amt;
				
				r=Utils.clamp(tr,0,255);
				g=Utils.clamp(tg,0,255);
				b=Utils.clamp(tb,0,255);

				newImg.setRGB(x,y,(new Color(r,g,b,a)).getRGB());
			}
		}
		return newImg;
	}
}

