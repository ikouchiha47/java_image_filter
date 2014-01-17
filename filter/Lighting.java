package org.filter;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

abstract class Lighting {
	int width, height;
	Color []colors;
	
	Lighting(BufferedImage image){
		ImageData id=new ImageData(image);
		width=(id.getImage()).getWidth();
		height=(id.getImage()).getHeight();
		colors=id.getColors();
	}
	
	abstract BufferedImage setExposure(BufferedImage newImg,int adjust);
	abstract BufferedImage setChannels(BufferedImage newImg, int[] rgb);
}

class Exposure extends Lighting{
	Curves cur;

	Exposure(BufferedImage image) {
		super(image);
		cur=new Curves(image);
	}
	
	BufferedImage setExposure(BufferedImage newImg, int adjust){
		
		int []s, c1, c2, e;
		double p;
		
		BufferedImage finalImg;
		
		p=Utils.normalize(adjust, 1, 100);
		adjust=Utils.clamp(adjust, -100, 100);
		
		s =  new int[]{0,0};
		e =  new int[]{255,255};
		c1 = new int[]{3,adjust};
		c2 = new int[]{255 - adjust, 255};
		
		finalImg=cur.tuneUpwithControls(newImg, s, c1, c2, e,new int[]{1,1,1});
		finalImg=(new Brightness(finalImg).tuneUp(finalImg, (int)p));
		
		return finalImg;
		
	}

	@Override
	BufferedImage setChannels(BufferedImage newImg, int[] rgb) {
		return null;
	}
}

class Gamma extends Lighting{
	Gamma(BufferedImage image){
		super(image);
	}
	BufferedImage tuneUp(BufferedImage newImg, float gamma){
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,i=0;
		double tr,tg,tb;
		//float A=1.0f;
		Color rgba;
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				
				tr= Math.pow(tr/255, gamma);
				tg= Math.pow(tg/255, gamma);
				tb= Math.pow(tb/255, gamma);
				
				r=Utils.clamp(tr*255);
				g=Utils.clamp(tg*255);
				b=Utils.clamp(tb*255);
				
				newImg.setRGB(x, y, (new Color(r,g,b)).getRGB());
			}
		}
		return newImg;
	}
	@Override
	BufferedImage setExposure(BufferedImage newImg, int adjust) {
		return null;
	}
	@Override
	BufferedImage setChannels(BufferedImage newImg, int[] rgb) {
		return null;
	}
}

class Channels extends Lighting{
	
	Channels(BufferedImage image){
		super(image);
	}
	
	
	BufferedImage setChannels(BufferedImage newImg, int[] rgb) {
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,i=0;
		double tr,tg,tb;
		
		Color rgba;
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				tr=rgba.getRed();
				tg=rgba.getGreen();
				tb=rgba.getBlue();
				
				
				tr=(tr>0)?(tr+(255-tr)*rgb[0]):(tr-(tr*Math.abs(rgb[0])));
				tg=(tg>0)?(tg+(255-tg)*rgb[1]):(tg-(tg*Math.abs(rgb[1])));
				tr=(tb>0)?(tb+(255-tb)*rgb[2]):(tb-(tb*Math.abs(rgb[2])));
				
				r=Utils.clamp(tr);
				g=Utils.clamp(tg);
				b=Utils.clamp(tb);
				
				newImg.setRGB(x, y, (new Color(r,g,b).getRGB()));
				}
			}
		return newImg;
	}
	
	@Override
	BufferedImage setExposure(BufferedImage newImg, int adjust) {
		return null;
	}
}

class Vignette extends Lighting{
	Vignette(BufferedImage image){
		super(image);
	}

	@Override
	BufferedImage setExposure(BufferedImage newImg, int adjust) {
		return null;
	}

	@Override
	BufferedImage setChannels(BufferedImage newImg, int[] rgb) {
		return null;
	}
	
	BufferedImage RadialVignette(BufferedImage newImg, float [] dist35, float[]dist75, Color [] colors35, Color [] colors75){
		float radius=Math.max(this.width, this.height) / 1.3f;
		Graphics2D g=newImg.createGraphics();
		Point2D center=new Point2D.Double(this.width/2,this.height/2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.drawImage(newImg, 0, 0, null);
		int focus = Math.min(this.width / 2, this.height / 2);
        g.setPaint(new RadialGradientPaint(
                center,
                radius,
                new Point(focus, focus),
                dist35,
                colors35, MultipleGradientPaint.CycleMethod.NO_CYCLE
        ));
        g.fillRect(-2, -2, width+4, height+4);
        
		g.setComposite(AlphaComposite.SrcOver.derive(.35f));
		g.setPaint(new RadialGradientPaint(
                center,
                Math.max(this.width, this.height) / 1.67f,
                new Point(this.width/2, this.height / 2),
                dist75,
                colors75,
                MultipleGradientPaint.CycleMethod.NO_CYCLE
        ));
        g.fillRect(0, 0, this.width, this.height);

		g.setComposite(AlphaComposite.SrcOver.derive(.75f));
		g.dispose();
		return newImg;		
	}
	
	
 }


class Temperature extends Lighting {
	
	
	Temperature(BufferedImage image) {
		super(image);
	}

	public BufferedImage warmth(BufferedImage newImg, int delta){
		int x,y,r,g,b,i=0;
		int w=this.width , h=this.height;
		Color rgba;
		
		Graphics g2= newImg.createGraphics();
		
		for(x=0; x<w; x++){
			for(y=0; y<h; y++){
				rgba=this.colors[i++];
				r=rgba.getRed();
				g=rgba.getGreen();
				b=rgba.getBlue();
				r=Utils.clamp((double)r+delta);
				g=Utils.clamp((double)g+delta);
				
				newImg.setRGB(x, y, (new Color(r,g,b)).getRGB());
			}
		}
		
		g2.drawImage(newImg, 0, 0, null);
		return newImg;
	}
	
	
	@Override
	BufferedImage setExposure(BufferedImage newImg, int adjust) {
		return null;
	}

	@Override
	BufferedImage setChannels(BufferedImage newImg, int[] rgb) {
		return null;
	}
}

class Gradients{
	BufferedImage Horizontal(BufferedImage grad, float [] dist, Color[] colors,float opacity){
		int height=grad.getHeight(), width=grad.getWidth();
		Graphics2D gd=grad.createGraphics();
		Point2D start=new Point2D.Float(0,height/2);
		Point2D end= new Point2D.Float(width,height/2);
		LinearGradientPaint lgp = new LinearGradientPaint(start,end,dist,colors);
		gd.setPaint(lgp);
		gd.fillRect(0, 0, width, height);  	
		gd.setComposite(AlphaComposite.SrcOver.derive(opacity));
		gd.dispose();
		
		return grad;
	}
}
