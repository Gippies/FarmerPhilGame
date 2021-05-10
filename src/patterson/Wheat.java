package patterson;

import brackeen.*;

public class Wheat extends Sprite {

    public static double costOfWheat = 50;
    private double health;
    private double healthBarSize;
    private boolean isTarget;
    private float midWheatX;
    private float midWheatY;

    public Wheat(Animation anim) {
        super(anim);
        health = 100;
    }

    public double getHealth() {
        return health;
    }

    public void setHealthBarSize(double x) {
        healthBarSize = x;
    }

    public double getHealthBarSize() {
        return healthBarSize;
    }

    public void loseHealth(double x) {
        health -= x;
    }

    public void Targeted() {
        isTarget = true;
    }

    public void lostTarget() {
        isTarget = false;
    }

    public boolean isATarget() {
        return isTarget;
    }

    public void setMidWheatX() {
        midWheatX = getX() + (getWidth() / 2);
    }

    public void setMidWheatY() {
        midWheatY = getY() + (getHeight() / 2);
    }

    public float getMidWheatX() {
        return midWheatX;
    }

    public float getMidWheatY() {
        return midWheatY;
    }
}
