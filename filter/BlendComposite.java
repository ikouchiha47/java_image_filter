package org.filter;

import java.awt.*;
import java.awt.image.*;

public class BlendComposite {
	public enum BlendMode{
		ADD,
		COLOR_BURN,
		COLOR_DODGE,
		DARKEN,
		LIGHTEN,
		MULTIPLY,
		OVERLAY,
		OVERLAYw,
		SCREEN,
		SOFTLIGHT,
		SUBSTRACT
		
	}
	public BufferedImage Blend(BlendMode mode, BufferedImage parent, BufferedImage layer){
		BufferedImage result = 
				new BufferedImage(parent.getWidth(),parent.getHeight(),parent.getType());
		Graphics2D g=result.createGraphics();
		g.drawImage(parent, 0, 0, null);
		new Blenders(parent,layer);
		
		switch(mode){
		case ADD:
			result=Blenders.Add(parent, layer);
			break;
		case COLOR_BURN:
			result=Blenders.Colorburn(parent, layer);
			break;
		case COLOR_DODGE:
			result=Blenders.Colordodge(parent, layer);
			break;
		case DARKEN:
			result=Blenders.Darken(parent, layer);
			break;
		case LIGHTEN:
			result=Blenders.Lighten(parent, layer);
			break;
		case MULTIPLY:
			result=Blenders.Multiply(parent, layer);
			break;
		case OVERLAY:
			result=Blenders.Overlay(parent, layer);
			break;
		case OVERLAYw:
			result=Blenders.Overlayw(parent, layer);
			break;
		case SCREEN:
			result=Blenders.Screen(parent, layer);
			break;
		case SOFTLIGHT:
			result=Blenders.SoftLight(parent, layer);
			break;
		case SUBSTRACT:
			result=Blenders.Substract(parent, layer);
			break;
		}
		g.drawImage(result, 0, 0, null);
		
		return result;
	}
}

class Blenders{
	static BufferedImage fi;
	static ImageData idP, idL;
	static Color [] colP, colL;
	static int parentWidth, parentHeight;
	
	Blenders(BufferedImage parent, BufferedImage layer){
		parentWidth=parent.getWidth(); parentHeight=parent.getHeight();
		fi=new BufferedImage(parentWidth, parentHeight,parent.getType());
		idP=new ImageData(parent);
		idL=new ImageData(layer);
		colP=idP.getColors();
		colL=idL.getColors();
	}
	static BufferedImage Add(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r=(rgbP.getRed())+(rgbL.getRed());
				g=(rgbP.getGreen())+(rgbL.getGreen());
				b=(rgbP.getBlue())+(rgbL.getBlue());
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				r=Utils.clamp(r);
				g=Utils.clamp(g);
				b=Utils.clamp(b);
				a=Utils.clamp(a, 0, 127);
				
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Colorburn(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		int tr1,tg1,tb1,tr2,tg2,tb2;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				tr1=rgbP.getRed();  tg1=rgbP.getGreen();
				tb1=rgbP.getBlue();	tr2=rgbL.getRed();
				tg2=rgbL.getGreen(); tb2=rgbL.getBlue();
				r=(tr1==0)?0 : (Math.max(0, 255 - (((255 - tr2) << 8) / tr1)));
				g=(tg1==0)?0 : (Math.max(0, 255 - (((255 - tg2) << 8) / tg1)));
				b=(tb1==0)?0 : (Math.max(0, 255 - (((255 - tb2) << 8) / tb1)));
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				r=Utils.clamp(r); g=Utils.clamp(g); b=Utils.clamp(b);
				a=Utils.clamp(a,0,127);
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Colordodge(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		int tr1,tr2,tg1,tg2,tb1,tb2;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				tr1=rgbP.getRed(); tr2=rgbL.getRed();
				tg1=rgbP.getGreen(); tg2=rgbL.getGreen();
				tb1=rgbP.getBlue(); tb2=rgbL.getBlue();
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				r=(tr1==255)?255 : (Math.min((tr2 << 8) / (255 - tr1), 255));
				g=(tg1==255)?255 : (Math.min((tg2 << 8) / (255 - tb1), 255));
				b=(tb1==255)?255 : (Math.min((tb2 << 8) / (255 - tg1), 255));
				
				r=Utils.clamp(r); g=Utils.clamp(g); b=Utils.clamp(b);
				a=Utils.clamp(a,0,127);
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	
	static BufferedImage Darken(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		int tr1,tr2,tg1,tg2,tb1,tb2;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				tr1=rgbP.getRed(); tr2=rgbL.getRed();
				tg1=rgbP.getGreen(); tg2=rgbL.getGreen();
				tb1=rgbP.getBlue(); tb2=rgbL.getBlue();
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				r=Math.min(tr1,	tr2);
				g=Math.min(tg1, tg2);
				b=Math.min(tb1, tb2);
				a=Utils.clamp(a,0,127);
				
				fi.setRGB(x,y,new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Lighten(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		int tr1,tr2,tg1,tg2,tb1,tb2;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				tr1=rgbP.getRed(); tr2=rgbL.getRed();
				tg1=rgbP.getGreen(); tg2=rgbL.getGreen();
				tb1=rgbP.getBlue(); tb2=rgbL.getBlue();
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				r=Math.max(tr1,	tr2);
				g=Math.max(tg1, tg2);
				b=Math.max(tb1, tb2);
				a=Utils.clamp(a,0,127);
				
				fi.setRGB(x,y,new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Multiply(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r=((rgbP.getRed())*(rgbL.getRed())) >> 8;
				g=((rgbP.getGreen())*(rgbL.getGreen())) >> 8;
				b=((rgbP.getBlue())*(rgbL.getBlue())) >> 8;
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				r=Utils.clamp(r);
				g=Utils.clamp(g);
				b=Utils.clamp(b);
				a=Utils.clamp(a,0,127);
				
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Overlay(BufferedImage parent, BufferedImage layer){
		int x,y,r1,g1,b1,r2,g2,b2;
		int r,g,b,a,i=0;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth;x++){
			for(y=0;y<parentHeight;y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r1=rgbP.getRed(); r2=rgbL.getRed();
				g1=rgbP.getGreen(); g2=rgbL.getGreen();
				b1=rgbP.getBlue(); b2=rgbL.getBlue();
				
				r=(r2<128) ?((r2*r1) >> 7) : (255 - ((255 - r2) * (255 - r1) >> 7));
				g=(g2<128) ?((g2*g1) >> 7) : (255 - ((255 - g2) * (255 - g1) >> 7));
                b=(b2<128) ?((b2*b1) >> 7) : (255 - ((255 - b2) * (255 - b1) >> 7));
                a=(rgbP.getAlpha()+rgbL.getAlpha());
                
                r=Utils.clamp(r);
                g=Utils.clamp(g);
                b=Utils.clamp(b);
                a=Utils.clamp(a,0,127);
                
                fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Overlayw(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r=Math.min((rgbP.getRed()),(rgbL.getRed()));
				g=Math.min((rgbP.getGreen()),(rgbL.getGreen()));
				b=Math.min((rgbP.getBlue()), (rgbL.getBlue()));
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				r=Utils.clamp(r);
				g=Utils.clamp(g);
				b=Utils.clamp(b);
				a=Utils.clamp(a,0,127);
				
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Screen(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		int r1,r2,g1,g2,b1,b2;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r1=rgbP.getRed(); r2=rgbL.getRed();
				g1=rgbP.getGreen(); g2=rgbL.getGreen();
				b1=rgbP.getBlue(); b2=rgbL.getBlue();
				
				r=255-((255-r1)*(255-r2) >> 8);
				g=255-((255-g1)*(255-g2) >> 8);
				b=255-((255-b1)*(255-b2) >> 8);				
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());	
			}
		}
		return fi;
	}
	
	static BufferedImage SoftLight(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		double tr1,tr2,fr, tg1,tg2,fg, tb1,tb2,fb;
		Color rgbP, rgbL;
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i]; rgbL=colL[i++];
				tr1=rgbP.getRed()/255; tr2=rgbL.getRed()/255;
				tg1=rgbP.getGreen()/255; tg2=rgbL.getGreen();
				tb1=rgbP.getBlue()/255; tb2=rgbL.getBlue()/255;
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				fr=((1-2*tr2)*(tr1*tr1))+(2*tr2*tr1);
				fg=((1-2*tg2)*(tg1*tg1))+(2*tg2*tg1);
				fb=((1-2*tb2)*(tb1*tb1))+(2*tb2*tb1);
				
				r=Utils.clamp(fr*255);
				g=Utils.clamp(fg*255);
				b=Utils.clamp(fb*255);
				a=Utils.clamp(a, 0, 127);
				fi.setRGB(x ,y ,new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
	
	static BufferedImage Substract(BufferedImage parent, BufferedImage layer){
		int x,y,r,g,b,a,i=0;
		Color rgbP, rgbL;
		
		for(x=0; x<parentWidth; x++){
			for(y=0; y<parentHeight; y++){
				rgbP=colP[i];
				rgbL=colL[i++];
				r=Math.abs((rgbP.getRed())-(rgbL.getRed()));
				g=Math.abs((rgbP.getGreen())-(rgbL.getGreen()));
				b=Math.abs((rgbP.getBlue())-(rgbL.getBlue()));
				a=(rgbP.getAlpha())+(rgbL.getAlpha());
				r=Utils.clamp(r);
				g=Utils.clamp(g);
				b=Utils.clamp(b);
				a=Utils.clamp(a, 0, 127);
				fi.setRGB(x, y, new Color(r,g,b,a).getRGB());
			}
		}
		return fi;
	}
}
