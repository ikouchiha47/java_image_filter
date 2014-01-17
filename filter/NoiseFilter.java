package org.filter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;
public class NoiseFilter  {

	public final static int GAUSSIAN = 0;
    public final static int UNIFORM = 1;

    private int amount = 25;
    private int distribution = UNIFORM;
    private boolean monochrome = false;
    private float density = 1;
    private Random randomNumbers = new Random();

    public NoiseFilter() {
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setMonochrome(boolean monochrome) {
        this.monochrome = monochrome;
    }

    public boolean getMonochrome() {
        return monochrome;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getDensity() {
        return density;
    }

    private int random() {
        return (int) (((distribution == GAUSSIAN ? randomNumbers.nextGaussian() : 2 * randomNumbers.nextFloat() - 1)) * amount);
    }

    private static int clamp(int x) {
        if (x < 0) {
            return 0;
        }
        else if (x > 0xff) {
            return 0xff;
        }
        return x;
    }

    public int filterRGB(int x, int y, int rgb) {
        if (randomNumbers.nextFloat() <= density) {
            int a = rgb & 0xff000000;
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;

            if (monochrome) {
                int n = random();
                r = clamp(r + n);
                g = clamp(g + n);
                b = clamp(b + n);
            }
            else {
                r = clamp(r + random());
                g = clamp(g + random());
                b = clamp(b + random());
            }
            return a | (r << 16) | (g << 8) | b;
        }
        return rgb;
    }

    //distribution=1; distribution=GAUSSIAN ;setDensity. distribution=0; distribution=UNIFORM ;setAmount; setDensity
    
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();
        int type = src.getType();
        WritableRaster srcRaster = src.getRaster();

        if (dst == null) {
            dst = new BufferedImage(src.getWidth(),src.getHeight(),src.getType());
        }
        WritableRaster dstRaster = dst.getRaster();

        int[] inPixels = new int[width];
        for (int y = 0; y < height; y++) {
            if (type == BufferedImage.TYPE_INT_ARGB) {
                srcRaster.getDataElements(0, y, width, 1, inPixels);
                for (int x = 0; x < width; x++) {
                    inPixels[x] = filterRGB(x, y, inPixels[x]);
                }
                dstRaster.setDataElements(0, y, width, 1, inPixels);
            }
            else {
                src.getRGB(0, y, width, 1, inPixels, 0, width);
                for (int x = 0; x < width; x++) {
                    inPixels[x] = filterRGB(x, y, inPixels[x]);
                }
                dst.setRGB(0, y, width, 1, inPixels, 0, width);
            }
        }

        return dst;
    }
    
    public BufferedImage createUniformNoise(BufferedImage fi, BufferedImage dest, int amount){
    	NoiseFilter noise = new NoiseFilter();
    	noise.setDensity(amount);
    	noise.setDistribution(1);
    	fi= noise.filter(fi,dest);
    	return fi;
    }
}
