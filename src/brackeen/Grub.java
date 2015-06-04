package brackeen;

import java.awt.Image;
import java.util.ArrayList;

import patt.*;

/**
    A Grub is a Creature that moves slowly on the ground.
*/
public class Grub extends Sprite {
	
	public static float SPEED = (float) .2;
	public static double grubbyStrength = .05;
	public static double grubbyHunger = .25;
	public static int grubImageWidth;
	public static int grubImageHeight;
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
	private int wanderDirection;
	private Wheat target;
	private ArrayList<Double> distanceFromWheats;
	private int numOfDistances;
	private float originalVelocityX;
	private float originalVelocityY;
	private boolean isPoisoned;
	private Sprite poisIcon;
	
	private Animation moveR;
	private Animation moveL;
	private Animation moveU;
	private Animation moveD;
	private Animation stopR;
	private Animation stopL;
	private Animation stopD;
	private Animation stopU;

 /*    public Grub(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        //super(left, right, deadLeft, deadRight);
    }  */
	
	public Grub (Animation anim)
	{
		super(anim);
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
	}
	
	public Grub (Animation sR, Animation sL, Animation sD, Animation sU, Animation mR, Animation mL, Animation mD, Animation mU)
	{
		super(sR);
		moveR = mR;
		moveL = mL;
		moveU = mU;
		moveD = mD;
		stopR = sR;
		stopL = sL;
		stopU = sU;
		stopD = sD;
		health = 100;
		hasWheatTarget = false;
		amWandering = false;
		wanderCounter = 0;
		distanceFromWheats = new ArrayList<Double>();
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
	}
	
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		poisIcon.setX(this.getX());
		poisIcon.setY(this.getY());
		
		if (isPoisoned)
		{
			this.loseHealth(poisonStrength);
		}
	}
	

    /**
     * Designed to set the X Velocity of the Grubby to follow the player
     * @param Player Loc
     * @param Grubby Loc
     * @return Velocity X
     */
    public float followPlayer(float pX, float gX)
    {
    	
    	if (gX+10 >= pX && gX-10 <= pX)
    	{
    		return 0;
    	}
    	if (gX > pX)
    	{
    		return -SPEED;
    	}
    	else if (pX > gX)
    	{
    		return SPEED;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    public float followWheat(float wX, float gX)
    {
    	if (gX+10 >= wX && gX-10 <= wX)
    	{
    		return 0;
    	}
    	if (gX > wX)
    	{
    		return -SPEED;
    	}
    	else if (wX > gX)
    	{
    		return SPEED;
    	}
    	else
    	{
    		return 0;
    	}
    }
    
    public double getHealth()
    {
    	return health;
    }
    
    public void setHealthBarSize(double x)
    {
    	healthBarSize = x;
    }
    
    public double getHealthBarSize()
    {
    	return healthBarSize;
    }
    
    public void loseHealth(double x)
    {
    	health -= x;
    }
    
    public boolean hasTarget()
    {
    	return hasWheatTarget;
    }
    
    public void setTarget(Wheat w)
    {
    	target = w;
    }
    
    public void foundTarget()
    {
    	hasWheatTarget = true;
    }
    
    public void lostTarget()
    {
    	hasWheatTarget = false;
    }
    
    public Wheat getTarget()
    {
    	return target;
    }
    
    public void addDistance(Double d)
    {
    	distanceFromWheats.add(d);
    	numOfDistances++;
    }
    
    public Double getDistance(int x)
    {
    	return distanceFromWheats.get(x);
    }
    
    public void resetDistances()
    {
    	distanceFromWheats = new ArrayList<Double>();
    	numOfDistances = 0;
    }
    
    public int getNumOfDistances()
    {
    	return numOfDistances;
    }
    
    public void startWander()
    {
    	amWandering = true;
    	wanderDirection = (int) Math.floor(Math.random()*8);
    	if (wanderDirection == 0)
    		wanderVX = SPEED;
    	else if (wanderDirection == 1)
    		wanderVX = -SPEED;
    	else if (wanderDirection == 2)
    		wanderVY = SPEED;
    	else if (wanderDirection == 3)
    		wanderVY = -SPEED;
    	else if (wanderDirection == 4)
    	{
    		wanderVX = SPEED;
    		wanderVY = SPEED;
    	}
    	else if (wanderDirection == 5)
    	{
    		wanderVX = SPEED;
    		wanderVY = -SPEED;
    	}
    	else if (wanderDirection == 6)
    	{
    		wanderVX = -SPEED;
    		wanderVY = SPEED;
    	}
    	else if (wanderDirection == 7)
    	{
    		wanderVX = -SPEED;
    		wanderVY = -SPEED;
    	}
    	
    }
    
    public void stopWander()
    {
    	amWandering = false;
    	wanderDirection = -1;
    	wanderVX = 0;
    	wanderVY = 0;
    }
    
    public float getWanderVX()
    {
    	return wanderVX;
    }
    
    public float getWanderVY()
    {
    	return wanderVY;
    }
    
    public boolean isWander()
    {
    	return amWandering;
    }
    
    public void updateWander()
    {
    	wanderCounter++;
    }
    
    public void resetWanderCounter()
    {
    	wanderCounter = 0;
    }
    
    public int getWanderCounter()
    {
    	return wanderCounter;
    }
    
    public void setMoveR()
    {
    	setAnim(moveR);
    }
    
    public void setMoveL()
    {
    	setAnim(moveL);
    }
    
    public void setMoveU()
    {
    	setAnim(moveU);
    }
    
    public void setMoveD()
    {
    	setAnim(moveD);
    }
    
    public void setIdleR()
    {
    	setAnim(stopR);
    }
    
    public void setIdleL()
    {
    	setAnim(stopL);
    }
    
    public void setIdleU()
    {
    	setAnim(stopU);
    }
    
    public void setIdleD()
    {
    	setAnim(stopD);
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
	
	public void setMidGrubX()
	{
		midGrubX = getX() + (getWidth() / 2);
	}
	
	public void setMidGrubY()
	{
		midGrubY = getY() + (getHeight() / 2);
	}
	
	public float getMidGrubX()
	{
		return midGrubX;
	}
	
	public float getMidGrubY()
	{
		return midGrubY;
	}
	
	public boolean isPoisoned()
	{
		return isPoisoned;
	}
	
	public void poison()
	{
		isPoisoned = true;
	}
	
	public void poisonWearOff()
	{
		isPoisoned = false;
	}
	
	public Sprite getPoisonIcon()
	{
		return poisIcon;
	}

}
