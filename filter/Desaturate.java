package org.filter;
import java.awt.*;
import java.awt.image.*;

abstract class Desaturate{
	
	int width, height;
	Color []colors;
	BufferedImage image;
	
	Desaturate(BufferedImage image){
		ImageData id=new ImageData(image);
		this.image=id.getImage();
		width=(id.getImage()).getWidth();
		height=(id.getImage()).getHeight();
		colors=id.getColors();
	}
	abstract BufferedImage convertTo(BufferedImage image);
}


class BlackWhite extends Desaturate {
	
	BlackWhite(BufferedImage newImg){
		super(newImg);
	}

	public BufferedImage convertTo(BufferedImage newImg){
		int iw=this.width, ih=this.height;
		int x,y,r,a,i=0;
		double tr,tg,tb,mid;

		Color rgba;

		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				a=rgba.getAlpha();

				mid=(tr+tg+tb);
				tr=(tr*1.5)+(tg*1.5)+(tb*1.5)-mid;
				
				r=Utils.clamp(tr);
				newImg.setRGB(x,y,(new Color(r,r,r,a)).getRGB());
			}
		}
		return newImg;
	}
}


class Grayscale extends Desaturate {
	
	Grayscale(BufferedImage image){
		super(image);
	}

	public BufferedImage convertTo(BufferedImage newImg){
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,i=0;
		double gray=0.0;
		
		Color rgba;

		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				r=rgba.getRed();
				g=rgba.getGreen();
				b=rgba.getBlue();

				gray=(r+g+b)/3;
				r=Utils.clamp(gray,0,255);

				newImg.setRGB(x,y,(new Color(r, r, r)).getRGB());
			}
		}
		return newImg;
	}
}


class Invert extends Desaturate{
	
	
	Invert(BufferedImage image) {
		super(image);
	}

	public BufferedImage convertTo(BufferedImage newImg){
		int iw=this.width , ih=this.height;
		int x,y,r,g,b,i=0;

		Color rgba;
		
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				r=Utils.clamp(255-rgba.getRed());
				g=Utils.clamp(255-rgba.getGreen());
				b=Utils.clamp(255-rgba.getBlue());
				newImg.setRGB(x,y,(new Color(r,g,b)).getRGB());
			}
		}

		return newImg;
	}
}


class Sepia extends Desaturate {
	
	private int shift=-12;
	
	Sepia(BufferedImage image){
		super(image);
	}
	

	@Override
	BufferedImage convertTo(BufferedImage image) {
		return null;
	}
	
	public BufferedImage convertTo(BufferedImage newImg, int shift){
		
		int iw=this.width , ih=this.height;
		int x,y,r,g,b,a,i=0;
		double tr,tg,tb,nr,ng,nb;
		double adjust;
		this.shift=shift;
		adjust=(this.shift==0)?100 : this.shift;
		adjust/=100;
		Color rgba;
			
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				a= rgba.getAlpha();
				
				tr/=255; tg/=255; tb/=255;
				
				nr=(tr*0.393)+(tg*0.769)+(tb*0.189)+adjust;
				ng=(tr*0.349)+(tg*0.686)+(tb*0.168)+adjust;
				nb=(tr*0.272)+(tg*0.534)+(tb*0.131)+adjust;
				
				r=Utils.clamp(Math.ceil(nr*255));
				g=Utils.clamp(Math.ceil(ng*255));
				b=Utils.clamp(Math.ceil(nb*255));
				
				newImg.setRGB(x, y, (new Color(r,g,b,a)).getRGB());
			}
		}
		return newImg;
	}
}
