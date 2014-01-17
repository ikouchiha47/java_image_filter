package org.filter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.effects.*;

public class SunsetEffect extends Effects {
	
	public BufferedImage Sunset(BufferedImage fi, BufferedImage dest, BufferedImage src){
		Curves cur;
		int []s,p1,p2,e;
		s=new int[]{0,0}; p1=new int[]{64,48}; p2=new int[]{171,128}; e=new int[]{255,190};
		cur=new Curves(src);
		fi= cur.tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,0,1});
		
		s=new int[]{0,43}; p1=new int[]{64,94}; p2=new int[]{180,192}; e=new int[]{255,255};
		fi= (new Curves(fi)).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{1,0,0});
		
		float dist[]=new float[] {0f, .60f, 1f};
		float dist2[]=new float[] {0, .70f, 1f};
		
		Color colors[]={ new Color(0x0, true), new Color(0,0,0,50), Color.BLACK.brighter()};
		Color colors2[]=new Color[] {new Color(0x0, true), new Color(0x00333333, true), Color.BLACK};
		
		fi=(new Vignette(fi)).RadialVignette(fi,dist,dist2,colors,colors2);
		fi=new Contrast(fi).tuneUp(dest, 5);
		Graphics g=fi.createGraphics();
		g.drawImage(fi, 0, 0, null);
			
		return fi;
	}
}
