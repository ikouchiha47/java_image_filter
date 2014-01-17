package org.filter;
class Utils {
	public static int clamp(double col,int min, int max){
		return (int)((col > max) ? max : ((col < min) ? min : col));
	}
	
	
	public static int clamp(double col){
		return (int)((col > 255) ? 255 : ((col < 0) ? 0 : col));
	}


	
	public static int distance(int min, int max){
		return (int)Math.sqrt( Math.pow( (max-min), 2) );
	}

	public static int distance(double x1,double y1,double x2,double y2){
		return (int)Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	
	public static double normalize(int val, int dmax, int smax){
		int smin,dmin,sdist,ddist;
		double ratio;
		smin=-smax;
		dmin=-dmax;
		sdist=distance(smin,smax);
		ddist=distance(dmin,dmax);
		
		ratio=((double)ddist)/((double)sdist);
		val=clamp(val,smin,smax);
		
		return dmin+(val-smin)*ratio;
	}

	
	
	public static int maxim(int array[]){
		int max=0,len=array.length;
		for(int i=0; i<len; i++){
			max=(max>array[i]) ? max : array[i];
		}
		return max;
	}
}
