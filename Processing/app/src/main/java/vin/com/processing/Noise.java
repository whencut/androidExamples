package vin.com.processing;

import processing.core.PApplet;

/**
 * Created by salimatti on 3/18/2017.
 */

public class Noise extends PApplet {

    private float xoff = (float) 0.0;

    @Override
    public void settings() {

      //  fullScreen(OPENGL);
        size(600,400);
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {

        background(204);
        xoff = (float) (xoff + .01);
        float n = noise(xoff) * width;
        stroke(random(255));
        line(n, 0, n, height);

    }
}
