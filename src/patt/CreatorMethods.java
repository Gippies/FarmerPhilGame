package patt;

import java.awt.Color;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.ArrayList;

import brackeen.*;

public class CreatorMethods {

    public static Weapon woodBat;
    public static Weapon sprayCan;
    public static Weapon bearTrapHold;
    public static Weapon Tractor;

    public static void createSprites() {
        //This is setting up the Start and Pause Menus

        CreatorMethods.createGUIStuff();

        LocationCapture.init();

        //Defining new Color
        AdventMain.MyBrown = new Color(156, 95, 58);

        //Defining the Background
		/*	bgImage = new Polygon();
			bgImage.addPoint(0, 0);
			bgImage.addPoint(0, screen.getHeight());
			bgImage.addPoint(screen.getWidth(), screen.getHeight());
			bgImage.addPoint(screen.getWidth(), 0);  */

        Image grassImage = GameCore.loadImage("res/grass.png");

        Animation grassAnim = new Animation();
        grassAnim.addFrame(grassImage, 500);

        int greenX = 0;
        int greenY = 0;

        AdventMain.grassGrid = new ArrayList<Sprite>();

        while (greenY <= AdventMain.screenManager.getHeight()) {
            while (greenX <= AdventMain.screenManager.getWidth()) {

                AdventMain.grassGrid.add(new Sprite(grassAnim));
                AdventMain.grassGrid.get(AdventMain.grassGrid.size() - 1).setX(greenX);
                AdventMain.grassGrid.get(AdventMain.grassGrid.size() - 1).setY(greenY);
                greenX += grassImage.getWidth(null);
            }
            greenX = 0;
            greenY += grassImage.getHeight(null);
        }

        //Defining the black Rectangle
        ScreenFractions.nineTenY = (AdventMain.screenManager.getHeight() * 9) / 10;

        //Defining the Farmland
        Image dirtImage = GameCore.loadImage("res/dirt.png");

        Animation dirtAnim = new Animation();
        dirtAnim.addFrame(dirtImage, 500);

        int dirtX = ScreenFractions.oneFourthRight;
        int dirtY = ScreenFractions.oneFourthDown;

        AdventMain.dirtGrid = new ArrayList<Sprite>();

        while (dirtY < ScreenFractions.oneFourthUp) {
            while (dirtX < ScreenFractions.oneFourthLeft) {

                AdventMain.dirtGrid.add(new Sprite(dirtAnim));
                AdventMain.dirtGrid.get(AdventMain.dirtGrid.size() - 1).setX(dirtX);
                AdventMain.dirtGrid.get(AdventMain.dirtGrid.size() - 1).setY(dirtY);
                dirtX += dirtImage.getWidth(null);
            }
            dirtX = ScreenFractions.oneFourthRight;
            dirtY += dirtImage.getHeight(null);
        }


        //Player Imaging:

        //TODO Begin Implementing multiple Animations in the Player Class
        Image playerImageL = AdventMain.loadImage("res/philL.png");
        Image playerImageR = AdventMain.loadImage("res/philR.png");
        Image playerImageU = AdventMain.loadImage("res/philU.png");

        Animation playerIdleL = new Animation();
        playerIdleL.addFrame(playerImageL, 500);

        Animation playerIdleR = new Animation();
        playerIdleR.addFrame(playerImageR, 500);

        Animation playerIdleU = new Animation();
        playerIdleU.addFrame(playerImageU, 500);

        AdventMain.player = new Player(playerIdleL, playerIdleR, playerIdleU);
        AdventMain.player.setX(AdventMain.screenManager.getWidth() / 2.0f - (AdventMain.player.getWidth() / 2.0f));
        AdventMain.player.setY(AdventMain.screenManager.getHeight() / 2.0f - (AdventMain.player.getHeight() / 2.0f));

        //Weapon Imaging:

        Image batImageL = AdventMain.loadImage("res/BatL.png");
        Image batImageL2 = AdventMain.loadImage("res/BatL2.png");
        Image batImageL3 = AdventMain.loadImage("res/BatL3.png");
        Image batImageL4 = AdventMain.loadImage("res/BatL4.png");
        Image batImageL5 = AdventMain.loadImage("res/BatL5.png");
        Image batImageR = AdventMain.loadImage("res/BatR.png");
        Image batImageR2 = AdventMain.loadImage("res/BatR2.png");
        Image batImageR3 = AdventMain.loadImage("res/BatR3.png");
        Image batImageR4 = AdventMain.loadImage("res/BatR4.png");
        Image batImageR5 = AdventMain.loadImage("res/BatR5.png");

        Animation batSwingL = new Animation();
        batSwingL.addFrame(batImageL5, 50);
        batSwingL.addFrame(batImageL4, 50);
        batSwingL.addFrame(batImageL3, 50);
        batSwingL.addFrame(batImageL2, 50);
        batSwingL.addFrame(batImageL, 50);

        Animation batSwingR = new Animation();
        batSwingR.addFrame(batImageR5, 50);
        batSwingR.addFrame(batImageR4, 50);
        batSwingR.addFrame(batImageR3, 50);
        batSwingR.addFrame(batImageR2, 50);
        batSwingR.addFrame(batImageR, 50);


        woodBat = new Weapon(batSwingL, batSwingR, 1, 250., "WoodBat", Double.POSITIVE_INFINITY);

        Image sprayImageL = AdventMain.loadImage("res/SprayL.png");
        Image sprayImageR = AdventMain.loadImage("res/SprayR.png");
        Image sprayImageL2 = AdventMain.loadImage("res/SprayL2.png");
        Image sprayImageR2 = AdventMain.loadImage("res/SprayR2.png");

        Animation sprayL = new Animation();
        sprayL.addFrame(sprayImageL, 200);
        sprayL.addFrame(sprayImageL2, 200);

        Animation sprayR = new Animation();
        sprayR.addFrame(sprayImageR, 200);
        sprayR.addFrame(sprayImageR2, 200);

        sprayCan = new Weapon(sprayL, sprayR, 0, 1000., "SprayCan", 2);

        Image bearTrapHoldImage = AdventMain.loadImage("res/bearTrapHold.png");
        Animation dummyBearTrap = new Animation();
        dummyBearTrap.addFrame(bearTrapHoldImage, 1000);

        Image bearTrapImage = AdventMain.loadImage("res/bearTrap.png");
        Image springedBearTrapImage = AdventMain.loadImage("res/bearTrapD.png");

        Image tractorImageL = AdventMain.loadImage("res/TractorL.png");
        Image tractorImageR = AdventMain.loadImage("res/TractorR.png");

        Animation tractorL = new Animation();
        tractorL.addFrame(tractorImageL, 1000);

        Animation tractorR = new Animation();
        tractorR.addFrame(tractorImageR, 1000);

        Tractor = new Weapon(tractorL, tractorR, 30, Double.POSITIVE_INFINITY, "Tractor", 1);

        Weapon.bearTrapAnimation = new Animation();
        Weapon.bearTrapAnimation.addFrame(bearTrapImage, 1000);

        Weapon.springedBearTrapAnimation = new Animation();
        Weapon.springedBearTrapAnimation.addFrame(springedBearTrapImage, 1000);


        AdventMain.fieldWeapons = new ArrayList<Weapon>();
        AdventMain.springedBearTraps = new ArrayList<Sprite>();

        bearTrapHold = new Weapon(dummyBearTrap, 0, 100., "BearTrapHold", 10);

        AdventMain.player.addWeapon(woodBat);
        AdventMain.player.getWeapon().updateLocation(AdventMain.player.getX(), AdventMain.player.getY(), AdventMain.player.getWidth(), AdventMain.player.getHeight(), AdventMain.player.getWeapon().getWidth(), AdventMain.player.getWeapon().getHeight(), true);

        //this is for the health bar outline later
        AdventMain.playerHBSize = (int) AdventMain.player.getHealth() * 3;
    }

    public static void createGUIStuff() {

        //TODO Finish LowerGUI Information
        //LowerGui code Starts Here:
        AdventMain.waveNumber = 1;
        AdventMain.moneyNumber = AdventMain.MONEY_START;
        DecimalFormat mf = new DecimalFormat("$#,###,##0.00");

        AdventMain.waveInfo = new MyLabel(ScreenFractions.twoFifthX, ScreenFractions.fourteenFifteenY + (AdventMain.screenManager.getHeight() - ScreenFractions.twentyNineThirtyY), "Wave: " + AdventMain.waveNumber);
        //moneyInfo = new MyLabel(oneFourthLeft, fourteenFifteenY+(screen.getHeight() - twentyNineThirtyY), "Money: " + mf.format(moneyNumber));
        AdventMain.moneyInfo = new MyLabel(ScreenFractions.twoFifthX, ScreenFractions.fourteenFifteenY, "Money: " + mf.format(AdventMain.moneyNumber));
        AdventMain.lblHealth = new MyLabel(ScreenFractions.threeFortyX, ScreenFractions.fourteenFifteenY + (AdventMain.screenManager.getHeight() - ScreenFractions.twentyNineThirtyY), "Health: ");
        AdventMain.weaponInfo = new MyLabel(ScreenFractions.threeFifthX, ScreenFractions.fourteenFifteenY, "Current Weapon: WoodBat");
        AdventMain.ammoInfo = new MyLabel(ScreenFractions.threeFifthX, ScreenFractions.fourteenFifteenY + (AdventMain.screenManager.getHeight() - ScreenFractions.twentyNineThirtyY), "Wave Ammo: " + Double.POSITIVE_INFINITY);
    }

}
