import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

import processing.core.PConstants;

public class Magnet extends PApplet {

    double x;
    double y;
    double easing = 0.01;
    PGraphics graphics;
    ArrayList<Ball> balls;

    public void settings() {
        size(500, 500);
        this.g = graphics;
        balls = new ArrayList<Ball>(20);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < height; j++) {
                if (j % 100 == 0 && j!= 0) {
                    for (int k = 0; k < width; k++) {
                        if (k % 100 == 0 && k != 0) {
                            Ball tmpBall = new Ball(this, k, j, 40, 40);
                            balls.add(tmpBall);
                        }
                    }
                }
            }
        }
    }

    public void draw() {
        background(255);
        for (int i = 0; i < 20; i++) {
            balls.get(i).display();
        }
    }


    public static void main(String[] args) {
        String[] processingArgs = {"Magnet"};
        Magnet magnet = new Magnet();
        PApplet.runSketch(processingArgs, magnet);
    }
}