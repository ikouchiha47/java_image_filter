package org.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ConvolvFilter {

	
	BufferedImage Blur(BufferedImage newImg, BufferedImage src, int adjust){		
		Graphics2D g=newImg.createGraphics();
	    g.drawImage(src,0,0,null);
	    
	    float adj=1.0f/adjust;
		float data[]={adj, adj, adj,
	                  adj, adj, adj,
	                  adj, adj, adj};
		
		Kernel k=new Kernel(3,3,data);
		ConvolveOp blur= new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);   
		newImg=blur.filter(src,null);
	    g.drawImage(newImg,0,0,null);
	    g.dispose();
	    
	    return newImg;
	}
	
	BufferedImage Sharpen(BufferedImage newImg, BufferedImage src){		
		Graphics2D g=newImg.createGraphics();
	    g.drawImage(src,0,0,null);
	    
		float data[]={0.0f, -1.0f, 0.0f,
			    -1.0f, 5.0f, -1.0f,
			     0.0f, -1.0f, 0.0f};
		
		Kernel k=new Kernel(3,3,data);
		ConvolveOp blur= new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);   
		newImg=blur.filter(src,null);
	    g.drawImage(newImg,0,0,null);
	    g.dispose();
	    
	    return newImg;
	}
	
	public  ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
		if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }
        //G(x)=(1/sqrt(2*pi*sigma*sigma))*exp(-x*x/(2*sigma*sigma));
		
        int size = radius * 2 + 1;
        float[] data = new float[size];
        
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }
        
        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }        
        
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
     
           return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

	BufferedImage GaussianBlur(BufferedImage newImg, BufferedImage src, int radius, boolean horizontal){
		Graphics2D g=newImg.createGraphics();
	    g.drawImage(src,0,0,null);
	    ConvolveOp gaussblur= this.getGaussianBlurFilter(radius,horizontal);
	    newImg=gaussblur.filter(src,null);
	    g.drawImage(newImg,0,0,null);
	    g.dispose();
		return newImg;
	}	
}
