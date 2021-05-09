package patt;

import brackeen.Animation;
import brackeen.Sprite;

public class Weapon extends Sprite {

    private final int strength;
    private final boolean doubleSided;
    private final double weaponTime;
    private final double maxAmmo;
    private final String name;

    private boolean isActive; //this will be for a fraction of a second
    private double currentAmmo;
    private Animation faceLeft;
    private Animation faceRight;
    public static Animation bearTrapAnimation;
    public static Animation springedBearTrapAnimation;

    public Weapon(Animation anim, int str, double wt, String n, double a) {
        super(anim);
        strength = str;
        weaponTime = wt;
        doubleSided = false;
        isActive = false;
        name = n;
        maxAmmo = a;
        currentAmmo = maxAmmo;
    }

    public Weapon(Animation l, Animation r, int str, double wt, String n, double a) {
        super(l);
        faceLeft = l;
        faceRight = r;
        strength = str;
        weaponTime = wt;
        doubleSided = true;
        isActive = false;
        name = n;
        maxAmmo = a;
        currentAmmo = maxAmmo;
    }


    public void useWeapon() {
        isActive = true;
    }

    public boolean equals(Weapon w) {
        return this.name.equals(w.getName());
    }

    public void stopWeapon() {
        isActive = false;
    }

    public boolean getActive() {
        return isActive;
    }

    public int getStrength() {
        return strength;
    }

    public void updateLocation(float px, float py, int pw, int ph, int ww, int wh, boolean left) {
        if (!this.equals(CreatorMethods.Tractor)) {
            if (doubleSided) {
                if (left) {
                    this.setAnim(faceLeft);
                    setX(px - ww);
                    setY(py);
                } else {
                    this.setAnim(faceRight);
                    setX(px + pw);
                    setY(py);
                }
            }
        } else {
            float halfPX = px + (pw / 2);
            float halfPY = py + (ph / 2);
            float halfWX = (ww / 2);
            float halfWY = (wh / 2);

            setX(halfPX - halfWX);
            setY(halfPY - halfWY);

            if (left)
                this.setAnim(faceLeft);
            else
                this.setAnim(faceRight);
        }
    }

    public double getWeaponTime() {
        return weaponTime;
    }

    public String getName() {
        return name;
    }

    public void deload() {
        currentAmmo--;
        AdventMain.ammoInfo.setText("Wave Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
        if (AdventMain.player.getWeapon().equals("BearTrapHold")) {
            AdventMain.ammoInfo.setText("Ammo: " + AdventMain.player.getWeapon().getCurrentAmmo());
        }
    }

    public void reload() {
        currentAmmo = maxAmmo;
    }

    public double getCurrentAmmo() {
        return currentAmmo;
    }
}
