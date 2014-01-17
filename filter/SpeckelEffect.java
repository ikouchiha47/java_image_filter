package org.filter;

import java.awt.image.BufferedImage;

import org.effects.*;

public class SpeckelEffect extends Effects {

	public BufferedImage Speckel(BufferedImage fi, BufferedImage dest, BufferedImage src){

		fi=new Gamma(src).tuneUp(dest, 1.5f);
		fi=(new Exposure(fi)).setExposure(dest, 50);
		NoiseFilter noise = new NoiseFilter();
		noise.setDensity(7000);
		noise.setDistribution(1);
		fi= noise.filter(fi,dest);
		return fi;
	}
}
