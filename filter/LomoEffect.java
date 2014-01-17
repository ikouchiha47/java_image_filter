package org.filter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class LomoEffect extends Effects{


public BufferedImage Lomo(BufferedImage fi, BufferedImage dest, BufferedImage src){
	Lighting l;
	Adjustments ad;
	Curves cur;
	Colors col;
	
	ad=new Brightness(src);
	fi=((Brightness) ad).tuneUp(dest, 5);	
	
	l=new Exposure(fi);
	fi=l.setExposure(dest, 3);
		 
	cur=new Curves(fi);
	fi=cur.tuneUpwithControls(dest, new int[]{0,0}, new int[]{200,50},
							  new int[]{100,200}, new int[]{255,255}, new int[]{1,1,1});
		 
	col=new Saturate(fi);
	fi=col.tuneUp(dest, 10);
	 
	l=new Gamma(fi);
	fi=col.tuneUp(dest, 1.05);
		 
	float [] dist=new float[] {.0f, .5f, 1f};
	float dist2[]=new float[] {0, .80f, 1f};
	Color[] colors={new Color(0x0, true), new Color(0x0, true), Color.BLACK};
	Color colors2[]=new Color[] {new Color(0x0, true), new Color(0x00333333, true), Color.BLACK};

	fi=(new Vignette(fi)).RadialVignette(fi,dist,dist2,colors,colors2);
		 
	ad=new Brightness(fi);
	fi=ad.tuneUp(dest, 5);
		 
	 Graphics g=fi.createGraphics();
	 g.drawImage(fi, 0, 0, null);
	 
	 return fi;
	}

}
