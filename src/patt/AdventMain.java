package patt;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import brackeen.*;

public class AdventMain extends GameCore {

    public static void main(String[] args) {
        new AdventMain().run();
    }

    //private Polygon bgImage;
    public static Polygon blackRect;
    //private Polygon farmLand;
    public static int wheatHealthMax;
    public static MyGui startMenu;
    public static MyGui pauseMenu;
    public static MyGui shopMenu;
    private MoosicThread musicThr;
    public static ArrayList<Sprite> grassGrid;
    public static ArrayList<Sprite> dirtGrid;
    public static ArrayList<Weapon> fieldWeapons;
    public static ArrayList<Sprite> springedBearTraps;
    public static Color MyBrown;
    private boolean isPaused;
    private boolean isStart;
    private boolean isShop;
    private boolean gameOver;
    public static MyLabel waveInfo;
    public static MyLabel moneyInfo;
    public static MyLabel lblHealth;
    public static MyLabel weaponInfo;
    public static MyLabel ammoInfo;
    public static int waveNumber;
    public static double moneyNumber;
    public static final int MONEY_START = 1000;

    private double weaponStartTime;
    private double weaponEndTime;
    private boolean weaponToggle = false;
    private boolean attackReleased = true;
    private List<Wheat> wheats;
    private List<Grub> grubs;
    private GrubStartLocation gStarters;

    //These are fraction sizes of the screen to X or Y
    public static int twentyNineThirtyY,
            fourteenFifteenY,
            threeTwentyX,
            nineTenY,
            playerHBSize,
            twoFifthX,
            threeFifthX,
            fiveSixtY,
            elevenSixtY,
            oneTwentyX,
            oneTwentyY,
            nineteenTwentyX,
            nineteenTwentyY,
            oneFourthRight,
            oneFourthLeft,
            oneFourthDown,
            oneFourthUp,
            threeFortyX;


    public static Player player;
    public static int numOfGrubby = 10;
    protected GameAction pause;
    protected GameAction mouseClick;
    protected InputManager inputManager;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction moveUp;
    protected GameAction moveDown;
    protected GameAction attack;
    protected GameAction nextWeapon;
    protected GameAction prevWeapon;
    protected Cursor normMouse = new Cursor(Cursor.DEFAULT_CURSOR);


    @Override
    public void init() {
        super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);

        // use these lines for relative mouse mode
        //inputManager.setRelativeMouseMode(true);
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        createGameActions();

        CreatorMethods.createSprites();
        createWheatField();
        createGrubs();

        try {
            startMoosic();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void createGrubs() {
        grubs = new ArrayList<>();
        //Grub Army Creation

        Grub g = new Grub();

        gStarters = new GrubStartLocation(AdventMain.screen.getWidth(), AdventMain.nineTenY, g.grubImageWidth, g.grubImageHeight);
        for (int k = 0; k < AdventMain.numOfGrubby; k++) {
            Grub grub = new Grub();
            grubs.add(grub);
            gStarters.chooseLoc();
            grub.setLocation(gStarters.getX(), gStarters.getY());
            grub.setHealthBarSize(grub.grubImageWidth);
        }
    }

    private void createWheatField() {
        wheats = new ArrayList<>();

        //Wheat Imaging:
        Image wheatImage = AdventMain.loadImage("res/Wheat.png");
        Image wheatImage2 = AdventMain.loadImage("res/Wheat2.png");
        Image wheatImage3 = AdventMain.loadImage("res/Wheat3.png");
        Image wheatImage4 = AdventMain.loadImage("res/Wheat4.png");
        Image wheatImage5 = AdventMain.loadImage("res/Wheat5.png");

        AdventMain.wheatHealthMax = wheatImage.getWidth(null);

        AdventMain.wheatAnim = new Animation();
        AdventMain.wheatAnim.addFrame(wheatImage, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage2, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage4, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage2, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage3, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage5, 20000);
        AdventMain.wheatAnim.addFrame(wheatImage3, 20000);

        //wheat creation
        float oxLoc = AdventMain.oneFourthRight;
        float oyLoc = AdventMain.oneFourthDown;
        float rightOfWheat = 0;
        float bottomOfWheat;
        int horizontalWheat = 0;
        int verticalWheat = 0;
        boolean h = false;
        int counter = 0;

        while (oyLoc + wheatImage.getHeight(null) < AdventMain.oneFourthUp) {
            wheats.add(new Wheat(wheatAnim));
            wheats.get(counter).setX(oxLoc);
            wheats.get(counter).setY(oyLoc);
            oxLoc += wheatImage.getWidth(null);
            if (!h)
                horizontalWheat++;
            if (oxLoc >= AdventMain.oneFourthLeft - wheatImage.getWidth(null)) {
                h = true;
                rightOfWheat = oxLoc;
                oxLoc = AdventMain.oneFourthRight;
                verticalWheat++;
                oyLoc += wheatImage.getHeight(null);
            }
            counter++;
        }

        bottomOfWheat = oyLoc;

        //Center Wheat in Field
        float rightDifference = AdventMain.oneFourthLeft - rightOfWheat;
        float bottomDifference = AdventMain.oneFourthUp - bottomOfWheat;

        rightDifference /= 2;
        bottomDifference /= 2;

        oxLoc = AdventMain.oneFourthRight + rightDifference;
        oyLoc = AdventMain.oneFourthDown + bottomDifference;

        int cHWheat = 0;
        int cVWheat = 0;
        counter = 0;

        while (cVWheat < verticalWheat) {
            wheats.get(counter).setX(oxLoc);
            wheats.get(counter).setY(oyLoc);
            wheats.get(counter).setHealthBarSize(wheatImage.getWidth(null));
            oxLoc += wheatImage.getWidth(null);
            cHWheat++;
            if (cHWheat == horizontalWheat) {
                cHWheat = 0;
                cVWheat++;
                oxLoc = AdventMain.oneFourthRight + rightDifference;
                oyLoc += wheatImage.getHeight(null);
            }
            counter++;
        }
    }

    @Override
    public void update(long elapsedTime) {
        // check input that can happen whether paused or not

        checkSystemInput();

        if (!gameOver && !isPaused && !isStart && !isShop) {
            checkGameInput();

            player.update(elapsedTime);

            for (Wheat wheat : wheats) {
                wheat.update(elapsedTime);
            }

            for (Grub grub : grubs) {
                grub.update(elapsedTime);
            }
        }


    }

    private void checkGameInput() {

        playerMotionUpdate();

        for (Grub grub : grubs) {
            grub.setOriginalVelocityX(grub.getVelocityX());
            grub.setOriginalVelocityY(grub.getVelocityY());
        }

        grubArmyThink();

        grubCollisionDetect();

        grubWanderStuff();

        grubChangeAnimation();

        grubHealthUpdate();

        waveChangeStuff();

        wheatHealthUpdate();

        //This is the end of the Game:
        if (player.getHealth() <= 0) {
            inputManager.setCursor(normMouse);
            gameOver = true;
        }

    }

    private void waveChangeStuff() {
        if (grubs.size() == 0) {
            waveNumber++;
            waveInfo.setText("Wave: " + waveNumber);
            numOfGrubby += 2;

            if (wheats.size() > 0) {
                moneyNumber += (wheats.size() * Wheat.costOfWheat);
                DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                moneyInfo.setText("Money: " + mf.format(moneyNumber));
            }

            for (int k = 0; k < numOfGrubby; k++) {
                Grub grub = new Grub();
                grubs.add(grub);
                gStarters.chooseLoc();
                grub.setLocation(gStarters.getX(), gStarters.getY());
                grub.setHealthBarSize(grub.grubImageWidth);
            }

            isShop = true;

            for (int k = 0; k < player.getAmountOfWeapons(); k++) {
                if (player.getWeapon(k).getCurrentAmmo() != Double.POSITIVE_INFINITY
                        && player.getWeapon(k).getCurrentAmmo() > 0
                        && !player.getWeapon(k).equals("BearTrapHold")) {
                    player.getWeapon(k).deload();
                }
            }

            springedBearTraps = new ArrayList<Sprite>();
        }

    }

    private void grubWanderStuff() {
        for (Grub grub : grubs) {
            if (grub.isWander()) {
                grub.setVelocityX(grub.getWanderVX());
                grub.setVelocityY(grub.getWanderVY());
                grub.updateWander();
                if (grub.getWanderCounter() >= 100) {
                    grub.stopWander();
                    grub.resetWanderCounter();
                }
            }
        }

    }

    private void wheatHealthUpdate() {
        //This is for Wheat losing health and dying
        List<Wheat> wheatsToRemove = new ArrayList<>();
        for (Wheat wheat : wheats) {
            wheat.setHealthBarSize((wheatHealthMax * wheat.getHealth()) / 100);
            if (wheat.getHealth() <= 0) {
                LocationCapture.addLocation(wheat.getX(), wheat.getY());
                wheatsToRemove.add(wheat);
            }
        }
        for (Wheat wheat : wheatsToRemove) {
            wheats.remove(wheat);
        }
    }

    private void grubHealthUpdate() {
        //This is Grub losing health and Grub death for their Health Bar
        //This is also if the Grub lose their target
        List<Grub> grubsToRemove = new ArrayList<>();
        for (Grub grub : grubs) {
            grub.setHealthBarSize((grub.grubImageWidth * grub.getHealth()) / 100);
            if (grub.hasTarget() && grub.getTarget().getHealth() <= 0)
                grub.lostTarget();
            if (grub.getHealth() <= 0)
                grubsToRemove.add(grub);
        }

        for (Grub grub : grubsToRemove) {
            grubs.remove(grub);
        }

    }

    private void grubChangeAnimation() {
        for (Grub grub : grubs) {
            if (grub.getOriginalVelocityX() != grub.getVelocityX()
                    || grub.getOriginalVelocityY() != grub.getVelocityY()) {
                if (grub.getVelocityY() < 0)
                    grub.setMoveU();
                else if (grub.getVelocityY() > 0)
                    grub.setMoveD();
                else if (grub.getVelocityX() > 0)
                    grub.setMoveR();
                else if (grub.getVelocityX() < 0)
                    grub.setMoveL();
                else if (grub.getOriginalVelocityX() > 0)
                    grub.setIdleR();
                else if (grub.getOriginalVelocityX() < 0)
                    grub.setIdleL();
                else
                    grub.setIdleR();
            }
        }
    }

    private void grubCollisionDetect() {

        if (grubs.size() > 0)
            for (Grub grub : grubs) {
                //This is grub-to-player collision detection, etc.
                if (grub.isColliding(player)) {
                    grub.setVelocityX(0);
                    grub.setVelocityY(0);
                    player.loseHealth(Grub.grubbyStrength);
                }

                //This is grub-to-player's-weapon collision detection
                if (player.getWeapon().getActive() && grub.isColliding(player.getWeapon())) {
                    grub.loseHealth(player.getWeapon().getStrength());
                    if (player.getWeapon().equals("SprayCan")) {
                        grub.poison();
                    }
                }

                //This is grub-to-field-weapons collision detection
                for (int j = 0; j < fieldWeapons.size(); j++) {
                    if (grub.isColliding(fieldWeapons.get(j))) {
                        grub.loseHealth(fieldWeapons.get(j).getStrength());

                        springedBearTraps.add(setSpringedBearTrap(fieldWeapons.get(j).getX(), fieldWeapons.get(j).getY()));
                        fieldWeapons.remove(j);
                    }
                }

                //This is grub-to-wheat collision detection
                if (grub.hasTarget())
                    if (grub.isColliding(grub.getTarget())) {
                        grub.setVelocityX(0);
                        grub.setVelocityY(0);
                        grub.getTarget().loseHealth(Grub.grubbyHunger);
                    }

                //This is grub-to-grub collision detection
                for (Grub grub2 : grubs) {
                    if (grub != grub2 && grub.isColliding(grub2) && !grub2.isWander()) {
                        grub2.startWander();
                        grub2.lostTarget();
                    }

                }

            }

    }

    private void grubArmyThink() {
        //Grubby AI
        for (Grub grub : grubs) {
            grub.setMidGrubX();
            grub.setMidGrubY();
        }
        for (Wheat wheat : wheats) {
            wheat.setMidWheatX();
            wheat.setMidWheatY();
        }


        float midTargetX;
        float midTargetY;

        float midplayX = player.getX() + (player.getWidth() / 2);
        float midplayY = player.getY() + (player.getHeight() / 2);
        float distanceAroundPlayer = 200;

        //This is for Grubby looking where to go
        for (Grub grub : grubs) {


            //This is finding a wheat for the grubby to eat :)
            if (wheats.size() != 0) {
                if (!grub.hasTarget()) {
                    grub.resetDistances();
                    for (Wheat wheat : wheats)
                        grub.addDistance(Math.sqrt(Math.pow(wheat.getMidWheatX() - grub.getMidGrubX(), 2) + Math.pow(wheat.getMidWheatY() - grub.getMidGrubY(), 2)));

                    double minimum = 999999;
                    int targetFinder = -1;
                    //Put a finding algorithm here.
                    for (int l = 0; l < grub.getNumOfDistances(); l++) {
                        if (grub.getDistance(l) < minimum) {
                            targetFinder = l;
                            minimum = grub.getDistance(l);
                        }
                    }

                    if (targetFinder < wheats.size()) {
                        grub.setTarget(wheats.get(targetFinder));
                        grub.foundTarget();
                    }

                }
            }


            if (((grub.getMidGrubX() < midplayX + distanceAroundPlayer && grub.getMidGrubX() > midplayX - distanceAroundPlayer)
                    && (grub.getMidGrubY() < midplayY + distanceAroundPlayer && grub.getMidGrubY() > midplayY - distanceAroundPlayer))
                    || wheats.size() == 0) {
                grub.lostTarget();
                grub.setVelocityX(grub.followPlayer(midplayX, grub.getMidGrubX()));
                grub.setVelocityY(grub.followPlayer(midplayY, grub.getMidGrubY()));
            } else if (grub.hasTarget()) {
                midTargetX = grub.getTarget().getX() + (grub.getTarget().getWidth() / 2);
                midTargetY = grub.getTarget().getY() + (grub.getTarget().getHeight() / 2);
                grub.setVelocityX(grub.followWheat(midTargetX, grub.getMidGrubX()));
                grub.setVelocityY(grub.followWheat(midTargetY, grub.getMidGrubY()));
            } else {
                grub.setVelocityX(0);
                grub.setVelocityY(0);
            }

        }

    }

    //TODO Finish all the animations for the player
    private boolean playerGoingLeft = true;
    private boolean nextWeaponReleased = true;
    private boolean prevWeaponReleased = true;

    private void playerMotionUpdate() {
        float velocityX = 0;
        float velocityY = 0;
        //deleteMe = false;

        if (player.getWeapon().getCurrentAmmo() <= 0 && player.getWeapon().equals("BearTrapHold")) {
            int weaponIndex = player.getCurrentWeapon();
            player.prevWeapon();
            player.deleteWeapon(weaponIndex);
            weaponToggle = false;

            shopMenu.getButton(4).release();
        }

        if (nextWeapon.isPressed() && nextWeaponReleased) {
            player.nextWeapon();
            weaponToggle = false;
            nextWeaponReleased = false;
        } else if (!nextWeapon.isPressed()) {
            nextWeaponReleased = true;
        }

        if (prevWeapon.isPressed() && prevWeaponReleased) {
            player.prevWeapon();
            weaponToggle = false;
            prevWeaponReleased = false;
        } else if (!prevWeapon.isPressed()) {
            prevWeaponReleased = true;
        }

        //This is weapon Behavior from HERE
        if (weaponToggle && weaponEndTime >= weaponStartTime) {
            weaponStartTime = System.currentTimeMillis();
        } else if (weaponToggle) {
            weaponToggle = false;
        }

        if (attack.isPressed() && !weaponToggle && attackReleased) {
            //player.getWeapon(0).useWeapon();
            if (player.getWeapon().equals("BearTrapHold")) {
                fieldWeapons.add(setBearTrap(player.getX(), player.getY()));
                player.getWeapon().deload();
            }

            player.getWeapon().getAnim().start();
            weaponToggle = true;
            weaponStartTime = System.currentTimeMillis();
            weaponEndTime = System.currentTimeMillis() + player.getWeapon().getWeaponTime();
            attackReleased = false;
        } else if (!attack.isPressed()) {
            attackReleased = true;
        }

        if (player.getWeapon().equals("Tractor")) {
            weaponToggle = true;
        }

        if (weaponToggle) {
            player.getWeapon().useWeapon();
        } else {
            player.getWeapon().stopWeapon();
        }
        //to HERE

        if (moveLeft.isPressed()) {
            player.setIdleL();
            velocityX -= player.SPEED;
            playerGoingLeft = true;
        }

        if (moveRight.isPressed()) {
            player.setIdleR();
            velocityX += player.SPEED;
            playerGoingLeft = false;
        }

        if (moveUp.isPressed()) {
            player.setIdleU();
            velocityY -= player.SPEED;
        }

        if (moveDown.isPressed()) {
            player.setIdleU();
            velocityY += player.SPEED;
        }


        //Following four ifs are for staying in-bounds
        if (player.getX() < 0 && !moveRight.isPressed()) {
            velocityX = 0;
        }
        if (player.getX() + player.getWidth() > screen.getWidth() && !moveLeft.isPressed()) {
            velocityX = 0;
        }

        if (player.getY() < 0 && !moveDown.isPressed()) {
            velocityY = 0;
        }
        if (player.getY() + player.getHeight() > nineTenY && !moveUp.isPressed()) {
            velocityY = 0;
        }

        player.setVelocityX(velocityX);
        player.setVelocityY(velocityY);

        player.getWeapon().updateLocation(player.getX(), player.getY(), player.getWidth(), player.getHeight(), player.getWeapon().getWidth(), player.getWeapon().getHeight(), playerGoingLeft);

    }

    private boolean pauseIsPressed = false;

    private void checkSystemInput() {

        if (pause.isPressed()) {
            if (isPaused && !pauseIsPressed) {
                isPaused = false;
                inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
            } else if (!pauseIsPressed) {
                isPaused = true;
                inputManager.setCursor(normMouse);
            }
            pauseIsPressed = true;
        } else {
            pauseIsPressed = false;
        }

        if (isPaused)
            pauseMenuMouseInteraction();

        if (isShop) {
            inputManager.setCursor(normMouse);
            shopMenuMouseInteraction();
        }


        if (isStart) {
            inputManager.setCursor(normMouse);
            startMenuMouseInteraction();
        }

    }

    private boolean mouseButtonIsPressed = false;
    private final double costOfHealth = 500;
    private final double costOfSprayCan = 2000;
    private final double costOfBearTrap = 1000;
    private final double costOfTractor = 5000;

    private void shopMenuMouseInteraction() {

        for (int k = player.getAmountOfWeapons() - 1; k >= 0; k--)
            if (player.getWeapon(k).getCurrentAmmo() == 0) {
                if (player.getCurrentWeapon() == k)
                    player.prevWeapon();
                player.deleteWeapon(k);
                weaponToggle = false;

                shopMenu.getButton(3).release();
                shopMenu.getButton(5).release();
            }

        //This if statement is for the Shop Continue Button
        if (mouseClick.isPressed() && shopMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            shopMenu.getButton(0).press();
            isShop = false;
            inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
            mouseButtonIsPressed = true;
        }

        //Add Health Button Code
        else if (mouseClick.isPressed() && shopMenu.getButton(1).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            if (moneyNumber - costOfHealth >= 0) {
                if (player.getHealth() < 100 && player.getHealth() + 25 <= 100) {
                    shopMenu.getButton(1).press();
                    player.addHealth(25);

                    moneyNumber -= costOfHealth;
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                } else if (player.getHealth() < 100 && player.getHealth() + 25 > 100) {
                    shopMenu.getButton(1).press();
                    double healthAmt = 100 - player.getHealth();
                    double moneyAmt = Math.round((healthAmt * costOfHealth) / 25);
                    player.addHealth(healthAmt);

                    moneyNumber -= moneyAmt;
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                }
            }

            mouseButtonIsPressed = true;
        }

        //Refill Wheat Button Code
        else if (mouseClick.isPressed() && shopMenu.getButton(2).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            int counter = LocationCapture.getSize();
            while (counter > 0) {
                if (moneyNumber - (Wheat.costOfWheat * 2) >= 0) {
                    shopMenu.getButton(2).press();
                    moneyNumber -= (Wheat.costOfWheat * 2);
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                    Wheat wheat = new Wheat(wheatAnim);
                    wheats.add(wheat);
                    wheat.setX(LocationCapture.getLocX(counter - 1));
                    wheat.setY(LocationCapture.getLocY(counter - 1));
                    LocationCapture.deleteLastLoc();
                }
                counter--;
            }

            mouseButtonIsPressed = true;
        }

        //Buy Spray Can Code
        else if (mouseClick.isPressed() && shopMenu.getButton(3).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            if (!shopMenu.getButton(3).isPressed()) {
                if ((moneyNumber - costOfSprayCan) >= 0) {
                    shopMenu.getButton(3).press();
                    CreatorMethods.sprayCan.reload();
                    player.addWeapon(CreatorMethods.sprayCan);
                    moneyNumber -= costOfSprayCan;
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                }

            }

            mouseButtonIsPressed = true;
        }

        //Buy Bear Traps Code
        else if (mouseClick.isPressed() && shopMenu.getButton(4).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            if (!shopMenu.getButton(4).isPressed()) {
                if ((moneyNumber - costOfBearTrap) >= 0) {
                    shopMenu.getButton(4).press();
                    CreatorMethods.bearTrapHold.reload();
                    player.addWeapon(CreatorMethods.bearTrapHold);
                    moneyNumber -= costOfBearTrap;
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                }

            }

            mouseButtonIsPressed = true;
        }

        //Buy Tractor Code
        else if (mouseClick.isPressed() && shopMenu.getButton(5).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            if (!shopMenu.getButton(5).isPressed()) {
                if ((moneyNumber - costOfTractor) >= 0) {
                    shopMenu.getButton(5).press();
                    CreatorMethods.Tractor.reload();
                    player.addWeapon(CreatorMethods.Tractor);
                    moneyNumber -= costOfTractor;
                    DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
                    moneyInfo.setText("Money: " + mf.format(moneyNumber));
                }
            }

            mouseButtonIsPressed = true;
        }

        //Reset Buttons and Mouse Toggle Code
        else if (!mouseClick.isPressed()) {
            for (int k = 0; k < shopMenu.getAmmountOfButtons(); k++) {
                if (!shopMenu.getButton(k).equals("BuySprayCan") &&
                        !shopMenu.getButton(k).equals("BuyBearTraps") &&
                        !shopMenu.getButton(k).equals("BuyTractor"))
                    shopMenu.getButton(k).release();
            }
            mouseButtonIsPressed = false;
        }
    }

    private void startMenuMouseInteraction() {
        if (mouseClick.isPressed() && startMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            startMenu.getButton(0).press();
            isStart = false;
            inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
            mouseButtonIsPressed = true;
        }

        //Hard Mode Button:
        else if (mouseClick.isPressed() && startMenu.getButton(1).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            startMenu.getButton(1).press();
            startMenu.getButton(2).release();
            startMenu.getButton(3).release();
            Grub.SPEED = (float) .2;
            Grub.grubbyHunger = .25;
            Grub.grubbyStrength = .05;
            mouseButtonIsPressed = true;
        }

        //Medium Mode Button:
        else if (mouseClick.isPressed() && startMenu.getButton(2).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            startMenu.getButton(2).press();
            startMenu.getButton(3).release();
            startMenu.getButton(1).release();
            Grub.SPEED = (float) .16;
            Grub.grubbyHunger = .125;
            Grub.grubbyStrength = .025;
            mouseButtonIsPressed = true;
        }

        //Easy Mode Button:
        else if (mouseClick.isPressed() && startMenu.getButton(3).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            startMenu.getButton(3).press();
            startMenu.getButton(1).release();
            startMenu.getButton(2).release();
            Grub.SPEED = (float) .12;
            Grub.grubbyHunger = .0625;
            Grub.grubbyStrength = .0125;
            mouseButtonIsPressed = true;
        } else if (!mouseClick.isPressed()) {
            mouseButtonIsPressed = false;
        }

    }

    //button 0 is close,
    private void pauseMenuMouseInteraction() {

        //This if statement is for the Close Button
        if (mouseClick.isPressed() && pauseMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed) {
            pauseMenu.getButton(0).press();
            musicThr.interrupt();
            stop();
            mouseButtonIsPressed = true;
        } else if (!mouseClick.isPressed()) {
            mouseButtonIsPressed = false;
        }

    }

    @Override
    public void draw(Graphics2D g) {
        // draw background
        g.setColor(Color.green);
        //g.fillPolygon(bgImage);
        for (Sprite sprite : grassGrid) {

            g.drawImage(sprite.getImage(), (int) sprite.getX(), (int) sprite.getY(), null);
        }


        //draw farmland
        g.setColor(MyBrown);
        //g.fillPolygon(farmLand);
        for (Sprite sprite : dirtGrid)
            g.drawImage(sprite.getImage(), (int) sprite.getX(), (int) sprite.getY(), null);

        //Draw Wheat
        g.setColor(Color.black);
        for (Wheat wheat : wheats)
            g.drawImage(wheat.getImage(), (int) wheat.getX(), (int) wheat.getY(), null);

        //Draw Springed Bear Traps
        for (Sprite springedBearTrap : springedBearTraps) {
            g.drawImage(springedBearTrap.getImage(), (int) springedBearTrap.getX(), (int) springedBearTrap.getY(), null);
        }

        //Draw Field Weapons
        for (Weapon fieldWeapon : fieldWeapons) {
            g.drawImage(fieldWeapon.getImage(), (int) fieldWeapon.getX(), (int) fieldWeapon.getY(), null);
        }

        for (Grub grub : grubs) {
            g.setColor(Color.black);
            g.drawImage(grub.getImage(), (int) grub.getX(), (int) grub.getY(), null);

            //draw Grubby Health Bar and Poison Icon
            g.setColor(Color.red);
            if (grub.getHealth() != 100)
                g.fillRect((int) grub.getX(), (int) (grub.getY() - 5), (int) grub.getHealthBarSize(), 5);

            if (grub.isPoisoned())
                g.drawImage(grub.getPoisonIcon().getImage(), (int) grub.getPoisonIcon().getX(), (int) grub.getPoisonIcon().getY(), null);
        }

        //draw Wheat Health Bar
        g.setColor(Color.blue);
        for (Wheat wheat : wheats) {
            if (wheat.getHealth() != 100)
                g.fillRect((int) wheat.getX(), (int) (wheat.getY() - 5), (int) wheat.getHealthBarSize(), 5);
        }

        // draw player and Weapon
        if (player.getWeapon().getActive())
            g.drawImage(player.getWeapon().getImage(), (int) player.getWeapon().getX(), (int) player.getWeapon().getY(), null);
        g.setColor(Color.black);
        if (!player.getWeapon().equals("Tractor"))
            g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), null);

        drawLowerGUI(g);

        //If the game is over do this:
        if (gameOver) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 212));
            drawCenteredStringX("Game Over", screen.getWidth(), screen.getHeight(), g);
        }

        //Start Menu
        if (isStart) {
            g.setFont(new Font("Dialog", Font.PLAIN, 24));
            startMenu.draw(g);
        }

        if (isShop) {
            g.setFont(new Font("Dialog", Font.PLAIN, 24));
            shopMenu.draw(g);
        }

        //Pause Menu
        if (isPaused) {
            g.setFont(new Font("Dialog", Font.PLAIN, 24));
            pauseMenu.draw(g);
        }

    }

    private void drawLowerGUI(Graphics2D g) {

        //draw black rectangle
        g.setColor(Color.black);
        g.fillPolygon(blackRect);

        //draw Health Bar
        g.setColor(Color.red);
        g.fillRect(threeTwentyX, fourteenFifteenY, (int) player.getHealth() * 3, screen.getHeight() - twentyNineThirtyY);

        //draw Health Bar Outline
        g.setColor(Color.white);
        g.drawRect(threeTwentyX, fourteenFifteenY, playerHBSize, screen.getHeight() - twentyNineThirtyY);

        waveInfo.draw(g);
        moneyInfo.draw(g);
        lblHealth.draw(g);
        weaponInfo.draw(g);
        ammoInfo.draw(g);

    }

    private void createGameActions() {
        gameOver = false;
        isPaused = false;
        isShop = false;
        isStart = true;

        pause = new GameAction("pause");
        //mouseClick = new GameAction("mouseClick", GameAction.DETECT_INITAL_PRESS_ONLY);
        mouseClick = new GameAction("mouseClick");
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveUp = new GameAction("moveUp");
        moveDown = new GameAction("moveDown");
        attack = new GameAction("attack");
        nextWeapon = new GameAction("nextWeapon");
        prevWeapon = new GameAction("prevWeapon");

        inputManager.mapToKey(pause, KeyEvent.VK_ESCAPE);
        inputManager.mapToMouse(mouseClick, InputManager.MOUSE_BUTTON_1);

        inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
        inputManager.mapToKey(moveRight, KeyEvent.VK_D);
        inputManager.mapToKey(moveUp, KeyEvent.VK_W);
        inputManager.mapToKey(moveDown, KeyEvent.VK_S);
        inputManager.mapToKey(attack, KeyEvent.VK_SPACE);
        inputManager.mapToKey(nextWeapon, KeyEvent.VK_E);
        inputManager.mapToKey(prevWeapon, KeyEvent.VK_Q);

    }

    public static Animation wheatAnim;

    public static Weapon setBearTrap(float x, float y) {
        Weapon w = new Weapon(Weapon.bearTrapAnimation, 100, 5, "BearTrap", Double.POSITIVE_INFINITY);
        w.setX(x);
        w.setY(y);
        return w;
    }

    public static Sprite setSpringedBearTrap(float x, float y) {
        Sprite s = new Sprite(Weapon.springedBearTrapAnimation);
        s.setX(x);
        s.setY(y);
        return s;
    }

    private void drawCenteredStringX(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }


    private void startMoosic() throws LineUnavailableException {
        //TODO Get MOOSIC from this website:
        //http://ericskiff.com/music/
        try {
            AudioInputStream audTemp;
            Clip tempClip;
            audTemp = AudioSystem.getAudioInputStream(new File("res/DizzySpells.wav"));
            tempClip = AudioSystem.getClip();
            tempClip.open(audTemp);
            musicThr = new MoosicThread(tempClip);
            musicThr.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
