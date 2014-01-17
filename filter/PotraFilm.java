package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class PotraFilm extends Effects{
	
	public BufferedImage Potra(BufferedImage fi, BufferedImage dest, BufferedImage src){
		int []s,c1,c2,e;
		
		s=new int[]{0,0}; c1=new int[]{69,68}; c2=new int[]{217,212}; e=new int[]{255,255};
		fi=(new Curves(src)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{1,0,0});
		
		s=new int[]{0,0}; c1=new int[]{47,52}; c2=new int[]{196,188}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{0,1,0});
		
		s=new int[]{0,0}; c1=new int[]{35,27}; c2=new int[]{165,200}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithControls(dest, s, c1, c2, e, new int[]{0,0,1});
		
		s=new int[]{0,0}; c1=new int[]{20,23}; c2=new int[]{173,157}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{1,1,1});

		Graphics g=fi.createGraphics();
		g.drawImage(fi,0,0,null);
		return fi;
	}

}
