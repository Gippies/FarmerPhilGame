package patt;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import brackeen.Animation;
import brackeen.Sprite;

/**
 * A Grub is a Creature that moves slowly on the ground.
 */
public class Grub extends Sprite {

    public static float SPEED = (float) .2;
    public static double grubbyStrength = .05;
    public static double grubbyHunger = .25;
    private static double poisonStrength = .2;
    private double health;
    private double healthBarSize;
    private boolean hasWheatTarget;
    private boolean amWandering;
    private int wanderCounter;
    private float wanderVX;
    private float wanderVY;
    private float midGrubX;
    private float midGrubY;
    public int grubImageWidth;
    public int grubImageHeight;
    private int wanderDirection;
    private Wheat target;
    private ArrayList<Double> distanceFromWheats;
    private int numOfDistances;
    private float originalVelocityX;
    private float originalVelocityY;
    private boolean isPoisoned;
    private Sprite poisIcon;

    private static final int GRUB_MOVE_ANIMATION_SPEED = 200;
    private static final int GRUB_IDLE_ANIMATION_SPEED = 1000;

    private Animation moveR;
    private Animation moveL;
    private Animation moveU;
    private Animation moveD;
    private Animation stopR;
    private Animation stopL;
    private Animation stopD;
    private Animation stopU;

    public Grub() {
        String[][] imagePaths = {
                {"res/GrubbyR.png", "res/GrubbyR2.png", "res/GrubbyR3.png", "res/GrubbyR4.png", "res/GrubbyR5.png"},
                {"res/GrubbyL.png", "res/GrubbyL2.png", "res/GrubbyL3.png", "res/GrubbyL4.png", "res/GrubbyL5.png"},
                {"res/GrubbyU.png", "res/GrubbyU2.png", "res/GrubbyU3.png", "res/GrubbyU4.png", "res/GrubbyU5.png"},
                {"res/GrubbyD.png", "res/GrubbyD2.png", "res/GrubbyD3.png", "res/GrubbyD4.png", "res/GrubbyD5.png"}
        };
        List<List<Image>> allFrames = new ArrayList<>();
        for (String[] imageArray : imagePaths) {
            List<Image> images = new ArrayList<>();
            for (String s : imageArray) {
                images.add(AdventMain.loadImage(s));
            }
            allFrames.add(images);
        }

        moveR = new Animation();
        moveR.addFrames(allFrames.get(0), GRUB_MOVE_ANIMATION_SPEED);
        moveL = new Animation();
        moveL.addFrames(allFrames.get(1), GRUB_MOVE_ANIMATION_SPEED);
        moveU = new Animation();
        moveU.addFrames(allFrames.get(2), GRUB_MOVE_ANIMATION_SPEED);
        moveD = new Animation();
        moveD.addFrames(allFrames.get(3), GRUB_MOVE_ANIMATION_SPEED);
        stopR = new Animation();
        stopR.addFrame(allFrames.get(0).get(0), GRUB_IDLE_ANIMATION_SPEED);
        stopL = new Animation();
        stopL.addFrame(allFrames.get(1).get(0), GRUB_IDLE_ANIMATION_SPEED);
        stopU = new Animation();
        stopU.addFrame(allFrames.get(2).get(0), GRUB_IDLE_ANIMATION_SPEED);
        stopD = new Animation();
        stopD.addFrame(allFrames.get(3).get(0), GRUB_IDLE_ANIMATION_SPEED);
        grubImageWidth = allFrames.get(0).get(0).getWidth(null);
        grubImageHeight = allFrames.get(0).get(0).getHeight(null);
        health = 100;
        hasWheatTarget = false;
        distanceFromWheats = new ArrayList<Double>();
        amWandering = false;
        wanderCounter = 0;
        numOfDistances = 0;
        originalVelocityX = 0;
        originalVelocityY = 0;
        midGrubX = 0;
        midGrubY = 0;
        isPoisoned = false;

        Image poisImage = AdventMain.loadImage("res/poisIcon.png");
        Animation poisAnim = new Animation();
        poisAnim.addFrame(poisImage, 500);
        poisIcon = new Sprite(poisAnim);
        setAnim(stopR);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        poisIcon.setX(this.getX());
        poisIcon.setY(this.getY());

        if (isPoisoned) {
            this.loseHealth(poisonStrength);
        }
    }


    /**
     * Designed to set the X Velocity of the Grubby to follow the player
     *
     * @param pX Player Loc
     * @param gX Grubby Loc
     * @return Velocity X
     */
    public float followPlayer(float pX, float gX) {

        if (gX + 10 >= pX && gX - 10 <= pX) {
            return 0;
        }
        if (gX > pX) {
            return -SPEED;
        } else if (pX > gX) {
            return SPEED;
        } else {
            return 0;
        }
    }

    public float followWheat(float wX, float gX) {
        if (gX + 10 >= wX && gX - 10 <= wX) {
            return 0;
        }
        if (gX > wX) {
            return -SPEED;
        } else if (wX > gX) {
            return SPEED;
        } else {
            return 0;
        }
    }

    public boolean isColliding(Sprite s) {
        return (x <= s.getX() + s.getWidth()
                && x + getWidth() >= s.getX()
                && y <= s.getY() + s.getHeight()
                && y + getHeight() >= s.getY());
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

    public boolean hasTarget() {
        return hasWheatTarget;
    }

    public void setTarget(Wheat w) {
        target = w;
    }

    public void foundTarget() {
        hasWheatTarget = true;
    }

    public void lostTarget() {
        hasWheatTarget = false;
    }

    public Wheat getTarget() {
        return target;
    }

    public void addDistance(Double d) {
        distanceFromWheats.add(d);
        numOfDistances++;
    }

    public Double getDistance(int x) {
        return distanceFromWheats.get(x);
    }

    public void resetDistances() {
        distanceFromWheats = new ArrayList<Double>();
        numOfDistances = 0;
    }

    public int getNumOfDistances() {
        return numOfDistances;
    }

    public void startWander() {
        amWandering = true;
        wanderDirection = (int) Math.floor(Math.random() * 8);
        if (wanderDirection == 0)
            wanderVX = SPEED;
        else if (wanderDirection == 1)
            wanderVX = -SPEED;
        else if (wanderDirection == 2)
            wanderVY = SPEED;
        else if (wanderDirection == 3)
            wanderVY = -SPEED;
        else if (wanderDirection == 4) {
            wanderVX = SPEED;
            wanderVY = SPEED;
        } else if (wanderDirection == 5) {
            wanderVX = SPEED;
            wanderVY = -SPEED;
        } else if (wanderDirection == 6) {
            wanderVX = -SPEED;
            wanderVY = SPEED;
        } else if (wanderDirection == 7) {
            wanderVX = -SPEED;
            wanderVY = -SPEED;
        }

    }

    public void stopWander() {
        amWandering = false;
        wanderDirection = -1;
        wanderVX = 0;
        wanderVY = 0;
    }

    public float getWanderVX() {
        return wanderVX;
    }

    public float getWanderVY() {
        return wanderVY;
    }

    public boolean isWander() {
        return amWandering;
    }

    public void updateWander() {
        wanderCounter++;
    }

    public void resetWanderCounter() {
        wanderCounter = 0;
    }

    public int getWanderCounter() {
        return wanderCounter;
    }

    public void setMoveR() {
        setAnim(moveR);
    }

    public void setMoveL() {
        setAnim(moveL);
    }

    public void setMoveU() {
        setAnim(moveU);
    }

    public void setMoveD() {
        setAnim(moveD);
    }

    public void setIdleR() {
        setAnim(stopR);
    }

    public void setIdleL() {
        setAnim(stopL);
    }

    public void setIdleU() {
        setAnim(stopU);
    }

    public void setIdleD() {
        setAnim(stopD);
    }

    public void setLocation(float otherX, float otherY) {
        x = otherX;
        y = otherY;
    }

    public void setOriginalVelocityX(float vx) {
        originalVelocityX = vx;
    }

    public void setOriginalVelocityY(float vy) {
        originalVelocityY = vy;
    }

    public float getOriginalVelocityX() {
        return originalVelocityX;
    }

    public float getOriginalVelocityY() {
        return originalVelocityY;
    }

    public void setMidGrubX() {
        midGrubX = getX() + (getWidth() / 2);
    }

    public void setMidGrubY() {
        midGrubY = getY() + (getHeight() / 2);
    }

    public float getMidGrubX() {
        return midGrubX;
    }

    public float getMidGrubY() {
        return midGrubY;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }

    public void poison() {
        isPoisoned = true;
    }

    public void poisonWearOff() {
        isPoisoned = false;
    }

    public Sprite getPoisonIcon() {
        return poisIcon;
    }

}
