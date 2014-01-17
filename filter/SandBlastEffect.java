package org.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.filter.BlendComposite.BlendMode;
import org.effects.*;

public class SandBlastEffect extends Effects{

	public BufferedImage SandBlast(BufferedImage fi, BufferedImage dest, BufferedImage src){
		BufferedImage hgrad=new BufferedImage(src.getWidth(), src.getHeight(),src.getType());
		BufferedImage tmp;
		int []s,c1,c2,e;
		float dist[]=new float[]{0, .30f, .95f, 1f};
		Color []colors=new Color[]{new Color(251,189,24), new Color(227,166,2),
									new Color(54,39,2), new Color(4,1,31)};
		tmp= (new SpeckelEffect()).Speckel(fi, dest, src);
		fi=(new Gradients()).Horizontal(hgrad,dist,colors,0.40f);
		fi=(new BlendComposite()).Blend(BlendMode.OVERLAY,fi,tmp);
		
		s=new int[]{0,26}; c1=new int[]{54,74}; c2=new int[]{191,197}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithControls(dest, s, c1, c2, e, new int[]{0,0,1});
		s=new int[]{0,0}; c1=new int[]{56,71}; c2=new int[]{188,207}; e=new int[]{255,255};
		fi=(new Curves(fi)).tuneUpwithPoints(dest, s, c1, c2, e, new int[]{1,1,1});
		return fi;
	}

}
