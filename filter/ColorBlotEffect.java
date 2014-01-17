package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class ColorBlotEffect extends Effects {

	public BufferedImage ColorBlot(BufferedImage fi, BufferedImage dest, BufferedImage src){
		int [] s,p1,p2,e;
		
		s=new int[]{0,0}; p1=new int[]{64,34}; p2=new int[]{163,192}; e=new int[]{255,255};
		fi=(new Curves(src)).tuneUpwithControls(dest, s, p1, p2, e, new int[]{1,0,0});
		s=new int[]{0,0}; p1=new int[]{58,78}; p2=new int[]{177,200}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithControls(dest, s, p1, p2, e, new int[]{0,1,0});
		
		s=new int[]{0,108}; p1=new int[]{64,126}; p2=new int[]{192,165}; e=new int[]{255,184};
		fi=(new Curves(fi)).tuneUpwithControls(dest, s, p1, p2, e, new int[]{0,0,1});
		
		fi=(new Brightness(fi)).tuneUp(dest, -5);
		fi=(new Contrast(fi)).tuneUp(dest, 20);
		
		
		Graphics g=fi.createGraphics();
		g.drawImage(fi, 0, 0, null);
		return fi;
	}
}
