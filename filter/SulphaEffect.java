package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class SulphaEffect extends Effects {

	public BufferedImage Sulpha(BufferedImage fi, BufferedImage dest, BufferedImage src){
		int [] s,p1,p2,e;
		
		s=new int[]{0,63}; p1=new int[]{40,92}; p2=new int[]{170,191}; e=new int[]{255,255};
		fi=(new Curves(src)).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{1,0,0});
		s=new int[]{0,0}; p1=new int[]{50,50}; p2=new int[]{128,128}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,1,0});
		
		s=new int[]{0,63}; p1=new int[]{65,96}; p2=new int[]{192,160}; e=new int[]{255,192};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,0,1});

		fi=(new ConvolvFilter()).GaussianBlur(dest, fi, 2,true);
		Graphics g=fi.createGraphics();
		g.drawImage(fi, 0, 0, null);
		return dest;
	}

}
