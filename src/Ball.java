import processing.core.PApplet;
import processing.core.PConstants;

public class Ball {
    double bX; //Current Position
    double bY; //Current Position

    int ballW;
    int ballH;

    private final double startX; //Starting Position
    private final double startY; //Starting Position

    double sDx; //Start Delta X. Distance from the starting point
    double sDy; //Start Delta Y. Distance from the starting point

    double easeX; //Easing for the start Pull. will be mapped to between .5 and 0.01
    double easeY; //Easing for the start Pull. will be mapped to between .5 and 0.01

    double sMx; //Mouse Delta
    double sMy; //Mouse Delta

    double targetEaseX; //Easing for the target(mouse) mapped between .5 and .01
    double targetEaseY; //Easing for the target(mouse) mapped between .5 and .01

    double threshold; //The range in which the Balls will start to react to the mouse

    int counter = 0; //frame counter for reset method

    PApplet s;

    /**
     * Constructor.
     *
     * @param sketch Usually "this" unless I figure out how to change that
     * @param x      Starting x Position of the ball. Should always return here.
     * @param y      Starting Y Position of the ball. Should always return here.
     * @param bW     Width of the Ball
     * @param bH     Height of th Ball
     */
    Ball(PApplet sketch, double x, double y, int bW, int bH) {
        bX = x;
        bY = y;

        ballW = bW;
        ballH = bH;

        startX = x;
        startY = y;
        s = sketch;
        threshold = (double) s.width / 3;
    }

    /**
     * @param value  the value to be mapped
     * @param start1 the original minimum
     * @param stop1  the original max
     * @param start2 the new minimum
     * @param stop2  the new maximum
     * @return returns a number within that new range.
     */
    private double map(double value, double start1, double stop1, double start2, double stop2) {
        double outgoing = start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
        String badness = null;
        if (outgoing != outgoing) {
            badness = "NaN (not a number)";
        } else if (outgoing == Double.NEGATIVE_INFINITY || outgoing == Double.POSITIVE_INFINITY) {
            badness = "infinity";
        }

        if (badness != null) {
            return 1;
        }
        return outgoing;
    }

    /**
     * Method to check if the position has changed
     */
    private boolean hasMoved() {
        return bX != startX && bY != startY;
    }

    /**
     * internal method that should always be active. Will place in the "display" method so its constant Applies a pull
     * towards the center (that gets stronger as the ball moves further from its starting position)
     */
    private void anchorPull() {
        if (hasMoved()) {
            sDx = startX - bX;
            sDy = startY - bY;

            if (sDx < 0) {
                easeX = this.map(sDx, 0, threshold, 0, -0.4);
            } else if (sDx > 0) {
                easeX = this.map(sDx, 0, threshold, 0.1, 0.4);
            }

            if (sDy < 0) {
                easeY = this.map(sDy, 0, threshold, 0, -0.4);
            } else if (sDy > 0) {
                easeY = this.map(sDy, 0, threshold, 0, 0.4);
            }

            if (easeX > 0.4) {
                easeX = 0.4;
            } else if (easeX < -.4) {
                easeX = -.4;
            }

            if (easeY > 0.4) {
                easeY = 0.4;
            } else if (easeY < -.4) {
                easeY = -.4;
            }

            this.bX += sDx * easeX;
            this.bY += sDy * easeY;
        }
    }


    /**
     * Checks if the mouse is within range.
     */
    private boolean mouseRange() {
        if (s.mouseX >= this.startX - threshold && s.mouseX <= this.startX + threshold) {
            if (s.mouseY >= this.startY - threshold && s.mouseY <= this.startY + threshold) {
                counter = 0;
                return true;
            } else return false;
        } else return false;
    }

    /**
     * Internal statement to apply the pull of the mouse, which will be stronger as the mouse gets closer to the Ball.
     * Call in the display() method.
     */
    private void mousePull() {
        if (mouseRange()) {
            counter++;
            sMx = s.mouseX - this.bX;
            sMy = s.mouseY - this.bY;

            if (sMx < 0) {
                targetEaseX = this.map(sMx, 0, threshold, 0.05, 0);
            } else if (sMx > 0) {
                targetEaseX = this.map(sMx, 0, threshold, 0.05, 0);
            }

            if (sMy < 0) {
                targetEaseY = this.map(sMy, 0, threshold, 0.05, 0);
            } else if (sMy > 0) {
                targetEaseY = this.map(sMy, 0, threshold, 0.05, 0);
            }
            if (targetEaseX > 0.05) {
                targetEaseX = 0.05;
            } else if (targetEaseX < -0.05) {
                targetEaseX = -0.05;
            }

            if (targetEaseY > 0.05) {
                targetEaseY = 0.05;
            } else if (targetEaseY < -0.05) {
                targetEaseY = -0.05;
            }

            this.bX += sMx * targetEaseX;
            this.bY += sMy * targetEaseY;
        }
    }

    private void reset() {
        if (!mouseRange()) {
            easeX = .25;
            easeY = .25;
        }
        if (counter > 60) {
            bX = startX;
            bY = startY;
        }
    }

    /**
     * Display method. Call in Draw()
     */
    public void display() {
        s.ellipseMode(PConstants.CENTER);
        s.fill(0);
        s.ellipse((float) bX, (float) bY, ballW, ballH);
        this.anchorPull();
        this.mousePull();
        this.reset();
    }
}
