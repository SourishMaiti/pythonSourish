package guimodule;

import processing.core.PApplet;
import processing.core.PImage;

public class MyDisplay extends PApplet{
	PImage img;
	public void setup(){
		size(400,400);
		background(255);
		 img = loadImage("palmTrees.jpg");
		 img.resize(0, height);
		 
	}
	public void draw() {
		//image(img);
		image(img,0,0);
		int[] col = display(second());
		fill(col[0],col[1],col[2]);
		ellipse(width/4,height/5,width/4,height/5);
		
		
	}
	public int[] display(float seconds){
		int [] rgb = new int[3];
		float diff = Math.abs(30-seconds);
		float ratio = diff/30;
		rgb[0]=(int) (255*ratio);
		rgb[1]=(int) (255*ratio);
		rgb[2]=0;
		return rgb;
		
		
	}

}
