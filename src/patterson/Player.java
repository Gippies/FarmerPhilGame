package patterson;

import brackeen.Animation;
import brackeen.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player extends the Sprite class to add states
 * (STATE_NORMAL or STATE_JUMPING) and gravity.
 */
public class Player extends Sprite {

    private float speed;
    private double health;
    private Weapon currentWeapon;

    private final Animation stopR;
    private final Animation stopL;
    private final Animation stopU;
    private final List<Weapon> weaponInv;

    public Player(Animation stopL, Animation stopR, Animation stopU) {
        super(stopL);
        this.stopL = stopL;
        this.stopR = stopR;
        this.stopU = stopU;
        health = 100;
        speed = 0.4f;
        weaponInv = new ArrayList<>();
    }

    /**
     * Updates the player's positon and animation. Also, sets the
     * Player's state to NORMAL if a jumping Player landed on
     * the floor.
     */
    public void update(long elapsedTime) {
        // move player
        super.update(elapsedTime);
        currentWeapon.update(elapsedTime);
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

    public float getSpeed() {
        return speed;
    }

    public double getHealth() {
        return health;
    }

    public void loseHealth(double l) {
        health -= l;
    }

    public void addHealth(double a) {
        health += a;
    }

    public void addWeapon(Weapon w) {
        weaponInv.add(w);
        if (currentWeapon == null) {
            currentWeapon = w;
        }
    }

    public void deleteWeapon(Weapon weapon) {
        weaponInv.remove(weapon);
        if (currentWeapon.equals(CreatorMethods.Tractor)) {
            speed = .5f;
        } else {
            speed = .4f;
        }
    }

    public void deleteWeapon(int index) {
        weaponInv.remove(index);
        if (currentWeapon.equals(CreatorMethods.Tractor)) {
            speed = .5f;
        } else {
            speed = .4f;
        }
    }

    public Weapon getWeapon() {
        return currentWeapon;
    }

    public Weapon getWeapon(int k) {
        return weaponInv.get(k);
    }

    public int getAmountOfWeapons() {
        return weaponInv.size();
    }

    public int getCurrentWeaponIndex() {
        return weaponInv.indexOf(currentWeapon);
    }

    public void nextWeapon() {
        if (weaponInv.size() > 1) {
            if (weaponInv.indexOf(currentWeapon) + 1 < weaponInv.size()) {
                currentWeapon = weaponInv.get(weaponInv.indexOf(currentWeapon) + 1);
            } else {
                currentWeapon = weaponInv.get(0);
            }
            setWeaponLabels();
        }
    }

    public void prevWeapon() {
        if (weaponInv.size() > 1) {
            if (weaponInv.indexOf(currentWeapon) - 1 >= 0) {
                currentWeapon = weaponInv.get(weaponInv.indexOf(currentWeapon) - 1);
            } else {
                currentWeapon = weaponInv.get(weaponInv.size() - 1);
            }
            setWeaponLabels();
        }
    }

    private void setWeaponLabels() {
        AdventMain.weaponInfo.setText("Current Weapon: " + this.getWeapon().getName());
        AdventMain.ammoInfo.setText("Wave Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
        if (AdventMain.player.getWeapon().equals(CreatorMethods.bearTrapHold)) {
            AdventMain.ammoInfo.setText("Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
        }

        if (currentWeapon.equals(CreatorMethods.Tractor)) {
            speed = .5f;
        } else {
            speed = .4f;
        }
    }
}
