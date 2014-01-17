package org.filter;
import java.awt.*;
import java.awt.image.*;

class Bezier{

	private double  c1[]=new double[2], c2[]=new double[2];
	private double c3[]=new double[2], c4[]=new double[2];
	private double points[]=new double[1024];
	
	
   static double b1(double t){
		return t*t*t;
	}
   static double b2(double t){
		return (3* t* t* (1-t));
	}
   static double b3(double t){
		return (3* t* (1-t) * (1-t));
	}
   static double b4(double t){
		return ((1-t)* (1-t)* (1-t));
	}

   void setControls(int c1[], int c2[], int c3[], int c4[]){
		int i;		
		for(i=0; i<2; i++){
			this.c1[i]=c1[i];
		}
		for(i=0; i<2; i++){
			this.c2[i]=c2[i];
		}
		for(i=0; i<2; i++){
			this.c3[i]=c3[i];
		}
		for(i=0; i<2; i++){
			this.c4[i]=c4[i];
		}
	}
		

	double[] getPoint(double t){
		double coX, coY;
		coX=((this.c1[0])*(b1(t)))+((this.c2[0])*(b2(t)))+((this.c3[0])*(b3(t)))+((this.c4[0])*(b4(t)));
		coY=((this.c1[1])*(b1(t)))+((this.c2[1])*(b2(t)))+((this.c3[1])*(b3(t)))+((this.c4[1])*(b4(t)));
		return new double[] {coX,coY};
	}

	 double[] genrateColorTable(){
		double i,point[];
		point=new double[1024];
		for(i=0; i<1024; i++){
			point=this.getPoint(i/1024);
			this.points[(int) point[0]]=point[1];
		}
		
		return points;
	}
}

class BezierControls{
	//private double xy0[] = new double[2], xy4[] = new double[2];
	//private double xy5[] = new double[2], xy3[] = new double[2];
	
	double x0,y0,x4,y4,x5,y5,x3,y3;	
	private double c1,c2,c3;
	private double t1,t2;
	
	static double b0(double t){
		return Math.pow(1-t, 3);
	}
	
	static double b1(double t){
		return 3 * Math.pow(1-t, 2) * t;
	}
	
	static double b2(double t){
		return 3 * (1-t) * Math.pow(t, 2);
	}
	
	static double b3(double t){
		return Math.pow(t, 3);
	}
	
	BezierControls(int[]xy0, int[]xy4, int[]xy5, int[]xy3){
		this.x0=xy0[0]; this.y0=xy0[1];
		this.x4=xy4[0]; this.y4=xy4[1];
		this.x5=xy5[0]; this.y5=xy5[1];
		this.x3=xy3[0]; this.y3=xy3[1];
	}
	
	static int [] solvexy(double a, double b, double c, double d, double e, double f){
		double j = (c- a/d * f)/(b - a * e / d);
		double i = (c - (b * j))/a;
		
		return new int[] {(int) Math.round(j),(int) Math.round(i)};
	}
	
	int [] getControls(){
		int x12[]=new int[2] , y12[]=new int[2];
		this.c1=Math.sqrt(Math.pow(x4-x0, 2)+ Math.pow(y4-y0,2));
		this.c2=Math.sqrt(Math.pow(x5-x4, 2)+ Math.pow(y5-y4,2));
		this.c3=Math.sqrt(Math.pow(x3-x5, 2)+ Math.pow(y3-y5,2));
		
		this.t1=c1/(c1+c2+c3);
		this.t2=(c1+c2)/(c1+c2+c3);
		
		x12=solvexy(b1(this.t1), b2(this.t1), x4-(x0*b0(this.t1))-(x3 * b3(this.t1)), b1(this.t2), b2(this.t2), x5-(x0 * b0(this.t2))-(x3 * b3(this.t2)));

		y12=solvexy(b1(this.t1), b2(this.t1), y4-(y0*b0(this.t1))-(y3 * b3(this.t1)), b1(this.t2), b2(this.t2), y5-(y0 * b0(this.t2))-(y3 * b3(this.t2)));
		
		return new int[] {x12[0], y12[0], x12[1], y12[1]};
	}
	
	double [] equalizeColorTable(double[] points){
		int i;
		try{
			if(points[0]!=-1);
		}
		catch(Exception e){
			points[0]= y0;
		}
		
		for(i=0; i<=255; i++){
			try{
				if(points[i]!=-1);
			}
			catch(Exception e){
				points[i]=points[i-1];
			}
		}
		return points;
	}
	
	
}

public class Curves {
	
	private int width,height;
	double []points;
	Color[] colors;
	
	Bezier bez=new Bezier();
	
	public Curves(BufferedImage newImg){
		ImageData id=new ImageData(newImg);
		width=(id.getImage()).getWidth();
		height=(id.getImage()).getHeight();
		colors=id.getColors();
	}
	
	BufferedImage drawImage(BufferedImage newImg, double[] points, int channel[]){
		int iw=this.width, ih=this.height;
		int x,y,r,g,b,a,i=0;

		Color rgba;
		for(x=0; x<iw; x++){
			for(y=0; y<ih; y++){
				rgba=this.colors[i++];
				r=rgba.getRed();
				g=rgba.getGreen();
				b=rgba.getBlue();
				a=rgba.getAlpha();
				
				if(channel[0]==1)
					r=Utils.clamp(this.points[r]);
				if(channel[1]==1)
					g=Utils.clamp(this.points[g]);
				if(channel[2]==1)
					b=Utils.clamp(this.points[b]);
				
				newImg.setRGB(x,y,(new Color(r,g,b,a)).getRGB());
			}
		}
		return newImg;
	}
		
	public BufferedImage tuneUpwithControls(BufferedImage newImg, int[]s, int[]c1, int[]c2, int[]e, int[] channel){
				
		bez.setControls(s,c1,c2,e);
		points=bez.genrateColorTable();
		return this.drawImage(newImg, points, channel);
	}
	
	public BufferedImage tuneUpwithPoints(BufferedImage newImg, int s[], int p1[], int p2[], int e[], int channel[]){
		BezierControls bezc=new BezierControls(s,p1,p2,e);
		int controls[]=bezc.getControls();
		int c1[]=new int[]{controls[0],controls[1]};
		int c2[]=new int[]{controls[2],controls[3]};
		bez.setControls(s,c1,c2,e);
		points=bez.genrateColorTable();
		points=bezc.equalizeColorTable(points);
		
		return this.drawImage(newImg, points, channel);
	}
	
	BufferedImage tuneUp(BufferedImage newImg, double[]s, double[]c1, double[]c2,double[]e, int[] channel){
		return null;
	}
}	




	
