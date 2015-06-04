package brackeen;

import java.util.ArrayList;

import patt.*;

/**
    The Player extends the Sprite class to add states
    (STATE_NORMAL or STATE_JUMPING) and gravity.
*/
public class Player extends Sprite {

    public float SPEED = .4f;
    private double health;
    private int currentWeapon;
    
    private Animation moveR;
	private Animation moveL;
	private Animation moveU;
	private Animation moveD;
	private Animation stopR;
	private Animation stopL;
	private Animation stopD;
	private Animation stopU;
	
	private ArrayList<Weapon> weaponInv;


    public Player(Animation sL) {
        super(sL);
        health = 100;
        weaponInv = new ArrayList<Weapon>();
        currentWeapon = 0;
    }
    
    public Player(Animation sL, Animation sR, Animation sU) {
        super(sL);
        stopL = sL;
        stopR = sR;
        stopU = sU;
        health = 100;
        weaponInv = new ArrayList<Weapon>();
        currentWeapon = 0;
    }

    /**
        Updates the player's positon and animation. Also, sets the
        Player's state to NORMAL if a jumping Player landed on
        the floor.
    */
    public void update(long elapsedTime) {

        // move player
        super.update(elapsedTime);
        weaponInv.get(currentWeapon).update(elapsedTime);
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
    
    public double getHealth()
    {
    	return health;
    }
    
    public void setHealth(double h)
    {
    	health = h;
    }
    
    public void loseHealth(double l)
    {
    	health-=l;
    }
    
    public void addHealth(double a)
    {
    	health+=a;
    }
    
    public void addWeapon(Weapon w)
    {
    	weaponInv.add(w);
    }
    
    public void deleteWeapon(int k)
    {
    	weaponInv.remove(k);
    	if (weaponInv.get(currentWeapon).equals("Tractor"))
		{
			this.SPEED = .5f;
		}
		else
		{
			this.SPEED = .4f;
		}
    }
    
    public Weapon getWeapon()
    {
    	return weaponInv.get(currentWeapon);
    }
    
    public Weapon getWeapon(int k)
    {
    	return weaponInv.get(k);
    }
    
    public int getAmountOfWeapons()
    {
    	return weaponInv.size();
    }
    
    public int getCurrentWeapon()
    {
    	return currentWeapon;
    }
    
    public void setCurrentWeapon(int k)
    {
    	currentWeapon = k;
    	AdventMain.weaponInfo.setText("Current Weapon: " + this.getWeapon().getName());
    	AdventMain.ammoInfo.setText("Wave Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
		if (AdventMain.player.getWeapon().equals("BearTrapHold"))
		{
			AdventMain.ammoInfo.setText("Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
		}
    }
    
    public void nextWeapon()
    {
    	if (weaponInv.size() > 1)
    	{
    		if ((currentWeapon + 1) < weaponInv.size())
    		{
    			currentWeapon++;
    		}
    		else
    		{
    			currentWeapon = 0;
    		}
    		AdventMain.weaponInfo.setText("Current Weapon: " + this.getWeapon().getName());
    		AdventMain.ammoInfo.setText("Wave Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
			if (AdventMain.player.getWeapon().equals("BearTrapHold"))
			{
				AdventMain.ammoInfo.setText("Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
			}
			
			if (weaponInv.get(currentWeapon).equals("Tractor"))
			{
				this.SPEED = .5f;
			}
			else
			{
				this.SPEED = .4f;
			}
    	}
    	
    }
    
    public void prevWeapon()
    {
    	if (weaponInv.size() > 1)
    	{
    		if ((currentWeapon - 1) >= 0)
    		{
    			currentWeapon--;
    		}
    		else
    		{
    			currentWeapon = weaponInv.size() - 1;
    		}
    		AdventMain.weaponInfo.setText("Current Weapon: " + this.getWeapon().getName());
    		AdventMain.ammoInfo.setText("Wave Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
			if (AdventMain.player.getWeapon().equals("BearTrapHold"))
			{
				AdventMain.ammoInfo.setText("Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
			}
			
			if (weaponInv.get(currentWeapon).equals("Tractor"))
			{
				this.SPEED = .5f;
			}
			else
			{
				this.SPEED = .4f;
			}
    	}
    }
}
