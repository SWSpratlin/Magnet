import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

import processing.core.PConstants;

public class Magnet extends PApplet {
    int gridX = 0;
    int gridY = 0;
    PGraphics graphics;
    ArrayList<Ball> balls;

    public void settings() {
        size(500, 500);
        this.g = graphics;
        balls = new ArrayList<Ball>(16);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < height; j++) {
                if (j % 100 == 0 && j != 0) {
                    gridX = 0;
                    gridY = 0;
                    for (int k = 0; k < width; k++) {
                        if (k % 100 == 0 && k != 0) {
                            gridX++;
                            gridY++;
                            Ball tmpBall = new Ball(this, k, j, 40, 40);
                            balls.add(tmpBall);
                        }
                    }
                }
            }
        }
        System.out.println(gridX + " " + gridY);
    }

    public void draw() {
        background(255);
        for (int i = 0; i < 16; i++) {

            if (i + 1 < balls.size() && (i+1) % gridX != 0) {
                drawLine(balls.get(i), balls.get(i+1));
            }
            if (i < (balls.size() - gridX)){
                drawLine(balls.get(i), balls.get(i+gridX));
                System.out.println(i+"");
            }
            balls.get(i).display(i + "");
        }
    }

    public void drawLine(Ball b1, Ball b2) {
        stroke(0);
        strokeWeight(5);
        line((float) b1.bX, (float) b1.bY, (float) b2.bX, (float) b2.bY);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Magnet"};
        Magnet magnet = new Magnet();
        PApplet.runSketch(processingArgs, magnet);
    }
}