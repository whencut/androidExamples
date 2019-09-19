package vin.com.processing;




import processing.core.PApplet;

public class Sketch extends PApplet {



    public void settings() {
       // size(600, 600);
        fullScreen(P3D);

    }

    public void setup() {
       // strokeWeight(20);
    }

    public void draw() {
       // frameRate(120);
        //ellipse(mouseX, mouseY, pmouseX, pmouseY);
        ellipse(mouseX,mouseY,20,20);
        fill(100);
    }
}