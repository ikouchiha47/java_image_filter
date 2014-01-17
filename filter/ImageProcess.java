package org.filter;


import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

class ImageProcess{

	public static void main(String ar[]){
		int width, height;
		
		File in=new File("john.jpg");
		ImageData i=new ImageData(in);
		width=(i.getImage()).getWidth();
		height=(i.getImage()).getHeight();

		BufferedImage newImg= new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		BufferedImage dest = null;
		
		try{
			dest=(new SandBlastEffect()).SandBlast(dest, newImg, i.getImage());
			ImageIO.write(dest,"png",new File("diaj2.png"));
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}
}


