package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.effects.*;

public class InkedYellowEffect extends Effects{

	public BufferedImage InkedYellow(BufferedImage fi, BufferedImage dest, BufferedImage src){
		Curves cur;
		Adjustments ad;
		
		int [] s, e , p1, p2;
		
		s=new int[]{0,0}; p1=new int[]{20,20}; p2=new int[]{150,150}; e=new int[]{255,255};
		
		cur=new Curves(src);
		fi = cur.tuneUpwithPoints(dest, s, p1, p2, e, new int[]{1,0,0});
		
		cur=new Curves(fi);
		fi = cur.tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,1,0});
		s= new int[]{0,62}; p1= new int[]{20,77}; p2= new int[]{150,161}; e= new int[]{255,192};
		
		cur=new Curves(fi);
		fi = cur.tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,0,1});
		
		ad=new Contrast(fi);
		fi = ad.tuneUp(dest, 10);
		
		Graphics g=fi.createGraphics();
		g.drawImage(fi, 0, 0, null);
		
		return fi;
	   }
}
