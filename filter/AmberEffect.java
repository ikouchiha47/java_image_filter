package org.filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.effects.*;
public class AmberEffect extends Effects {

	public BufferedImage Amber(BufferedImage fi, BufferedImage dest, BufferedImage src){
					
					int s[], e[];
					int p1[], p2[];
					
					s= new int[]{0,0}; p1= new int[]{20,32}; p2= new int[]{120,170}; e= new int[]{255,255};
					fi = new Curves(src).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{1,0,0});
					s= new int[]{0,0}; p1= new int[]{20,13}; p2= new int[]{200,180}; e= new int[]{255,255};
					fi = new Curves(fi).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,1,0});
					s = new int[]{0,127}; p1 = new int[]{20,127}; p2 = new int[]{200,128}; e = new int[]{255,128};					
					fi = new Curves(fi).tuneUpwithPoints(dest, s, p1, p2, e, new int[]{0,0,1});
					fi = new Contrast(fi).tuneUp(dest,5);

					Graphics g=fi.createGraphics();
					g.drawImage(fi, 0, 0, null);
					
					return fi; 
				}

}
