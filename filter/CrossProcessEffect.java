package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class CrossProcessEffect extends Effects {
	public BufferedImage CrossProcess(BufferedImage fi, BufferedImage dest, BufferedImage src){
		int []s,c1,c2,e;
		s=new int[]{0,0}; c1=new int[]{108,5}; c2=new int[]{158,250}; e=new int[]{255,255};
		fi=(new Curves(src)).tuneUpwithControls(dest, s, c1, c2, e, new int[]{1,0,0});
		
		s=new int[]{0,0}; c1=new int[]{10,24}; c2=new int[]{208,193}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{0,1,0});
		
		s=new int[]{0,20}; c1=new int[]{64,74}; c2=new int[]{191,181}; e=new int[]{255,230};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{0,0,1});
		
		s=new int[]{0,0}; c1=new int[]{64,64}; c2=new int[]{128,128}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{1,1,1});
		
		Graphics g=fi.createGraphics();
		g.drawImage(fi, 0, 0, null);
		return fi;
		
	}
}
