import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PGraphics2D;
import processing.core.PSurfaceNone;
import java.util.ArrayList;
import processing.core.PConstants;

public class Magnet extends PApplet {
    int gridX = 0;
    int gridY = 0;
    PGraphics graphics;
    ArrayList<Ball> balls;
    PSurfaceNone surf;

    public void settings() {
        size(displayWidth, displayHeight);
        fullScreen();
        this.g = graphics;
        balls = new ArrayList<Ball>();
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
        System.out.println(gridX + " " + gridY);
    }

    public void draw() {
        background(255);
        for (int i = 0; i < balls.size(); i++) {

            if (i + 1 < balls.size() && (i + 1) % gridX != 0) {
                drawLine(balls.get(i), balls.get(i + 1));
            }
            if (i < (balls.size() - gridX)) {
                drawLine(balls.get(i), balls.get(i + gridX));
            }
            if ((i + 1) < balls.size() - gridX && (i + 1) % gridX != 0) {
                drawLine(balls.get(i), balls.get(i + gridX + 1));
            }
            if ((i - 1) >= 0 && i % gridX != 0 && i < balls.size() - gridX) {
                drawLine(balls.get(i), balls.get(i + gridX - 1));
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