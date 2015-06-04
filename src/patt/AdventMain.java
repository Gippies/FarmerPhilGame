package patt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
	public static WheatField field = new WheatField();
	public static GrubArmy badArmy;
	public static GrubStartLocation gStarters;
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
	public void init()
	{
		super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);

        // use these lines for relative mouse mode
        //inputManager.setRelativeMouseMode(true);
        inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        createGameActions();
        
        CreatorMethods.createSprites();
        
        try {
			startMoosic();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(long elapsedTime) {
		// check input that can happen whether paused or not
		
		checkSystemInput();
		
		if (!gameOver && !isPaused && !isStart && !isShop)
		{	
			// check game input
			checkGameInput();
			// update sprite
			player.update(elapsedTime);
			//wheat update
			//wheat.update(elapsedTime);
			
			for (int k = 0; k < field.getSize(); k++)
				field.getWheat(k).update(elapsedTime);
			
			//grubby.update(elapsedTime);
			for (int k = 0; k < badArmy.getSize(); k++)
			{
				//badArmy.updateGrub(k, elapsedTime);
				badArmy.getGrub(k).update(elapsedTime);
			}
		}
        
        
    }
	
	private void checkGameInput() {
		
		playerMotionUpdate();
		
		for (int k = 0; k < badArmy.getSize(); k++)
		{
			badArmy.getGrub(k).setOriginalVelocityX(badArmy.getGrub(k).getVelocityX());
			badArmy.getGrub(k).setOriginalVelocityY(badArmy.getGrub(k).getVelocityY());
		}
		
		grubArmyThink();
		
		grubCollisionDetect();

		grubWanderStuff();
        
		grubChangeAnimation();
        
		grubHealthUpdate();
		
        waveChangeStuff();
        
        wheatHealthUpdate();
        
        //This is the end of the Game:
        if (player.getHealth() <= 0)
        {
        	inputManager.setCursor(normMouse);
        	gameOver = true;
        }
		
	}

	private void waveChangeStuff() {
		//Also need to add wheat spawning?
		if (badArmy.getSize() == 0)
		{
			waveNumber++;
			waveInfo.setText("Wave: " + waveNumber);
			numOfGrubby+=2;
			
			if (field.getSize() > 0)
			{
				moneyNumber += (field.getSize() * Wheat.costOfWheat);
				DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
				moneyInfo.setText("Money: " + mf.format(moneyNumber));
			}
			
			for (int k = 0; k < numOfGrubby; k++)
			{
				badArmy.addGrub(createNormalGrub());
	        	gStarters.chooseLoc();
				badArmy.setGrubLoc(badArmy.getSize()-1, gStarters.getX(), gStarters.getY());
				badArmy.getGrub(badArmy.getSize()-1).setHealthBarSize(Grub.grubImageWidth);
			}
			
			isShop = true;
			
			for (int k = 0; k < player.getAmountOfWeapons(); k++)
			{
				if (player.getWeapon(k).getCurrentAmmo() != Double.POSITIVE_INFINITY
						&& player.getWeapon(k).getCurrentAmmo() > 0
						&& !player.getWeapon(k).equals("BearTrapHold"))
				{
					player.getWeapon(k).deload();
				}
			}
			
			springedBearTraps = new ArrayList<Sprite>();
		}
		
	}

	private void grubWanderStuff() {
		for (int k = 0; k < badArmy.getSize(); k++)
		{
			if (badArmy.getGrub(k).isWander())
			{
				badArmy.getGrub(k).setVelocityX(badArmy.getGrub(k).getWanderVX());
				badArmy.getGrub(k).setVelocityY(badArmy.getGrub(k).getWanderVY());
				badArmy.getGrub(k).updateWander();
				if (badArmy.getGrub(k).getWanderCounter() >= 100)
				{
					badArmy.getGrub(k).stopWander();
					badArmy.getGrub(k).resetWanderCounter();
				}
			}
		}
		
	}

	private void wheatHealthUpdate() {
		//This is for Wheat losing health and dying
        for (int k = 0; k < field.getSize(); k++)
        {
        	field.getWheat(k).setHealthBarSize((wheatHealthMax * field.getWheat(k).getHealth()) / 100);
        	if (field.getWheat(k).getHealth() <= 0)
        	{
        		LocationCapture.addLocation(field.getWheat(k).getX(), field.getWheat(k).getY());
        		field.deleteWheat(k);
        	}
        		
        }
		
	}

	private void grubHealthUpdate() {
		//This is Grub losing health and Grub death for their Health Bar
        //This is also if the Grub lose their target
        for (int k = 0; k < badArmy.getSize(); k++)
        {
        	badArmy.getGrub(k).setHealthBarSize((Grub.grubImageWidth * badArmy.getGrub(k).getHealth()) / 100);
        	if (badArmy.getGrub(k).hasTarget() && badArmy.getGrub(k).getTarget().getHealth() <= 0)
        		badArmy.getGrub(k).lostTarget();
        	if (badArmy.getGrub(k).getHealth() <= 0)
        		badArmy.deleteGrub(k);
        }
		
	}

	private void grubChangeAnimation() {
		for (int k = 0; k < badArmy.getSize(); k++)
        	if (badArmy.getGrub(k).getOriginalVelocityX() != badArmy.getGrub(k).getVelocityX()
        		|| badArmy.getGrub(k).getOriginalVelocityY() != badArmy.getGrub(k).getVelocityY())
      
        		if (badArmy.getGrub(k).getVelocityY() < 0)
        			badArmy.getGrub(k).setMoveU();
        		else if (badArmy.getGrub(k).getVelocityY() > 0)
        			badArmy.getGrub(k).setMoveD();
        		else if (badArmy.getGrub(k).getVelocityX() > 0)
        			badArmy.getGrub(k).setMoveR();
        		else if (badArmy.getGrub(k).getVelocityX() < 0)
        			badArmy.getGrub(k).setMoveL();
        		else if (badArmy.getGrub(k).getOriginalVelocityX() > 0)
        			badArmy.getGrub(k).setIdleR();
        		else if (badArmy.getGrub(k).getOriginalVelocityX() < 0)
        			badArmy.getGrub(k).setIdleL();
        		else
        			badArmy.getGrub(k).setIdleR();
		
	}

	private void grubCollisionDetect() {
		
        if (badArmy.getSize() > 0)
        	for (int k = 0; k < badArmy.getSize(); k++)
        	{
        		//This is grub-to-player collision detection, etc.
        		if (badArmy.grubColSprite(badArmy.getGrub(k), player))
        		{
        			badArmy.getGrub(k).setVelocityX(0);
        			badArmy.getGrub(k).setVelocityY(0);
        			player.loseHealth(Grub.grubbyStrength);
        		}
        		
        		//This is grub-to-player's-weapon collision detection
        		if (player.getWeapon().getActive() && badArmy.grubColSprite(badArmy.getGrub(k), player.getWeapon()))
        		{
        			badArmy.getGrub(k).loseHealth(player.getWeapon().getStrength());
        			if (player.getWeapon().equals("SprayCan"))
        			{
        				badArmy.getGrub(k).poison();
        			}
        		}
        		
        		//This is grub-to-field-weapons collision detection
        		for (int j = 0; j < fieldWeapons.size(); j++)
        		{
        			if (badArmy.grubColSprite(badArmy.getGrub(k), fieldWeapons.get(j)))
        			{
        				badArmy.getGrub(k).loseHealth(fieldWeapons.get(j).getStrength());
        				
        				springedBearTraps.add(setSpringedBearTrap(fieldWeapons.get(j).getX(), fieldWeapons.get(j).getY()));
        				fieldWeapons.remove(j);
        			}
        		}
        		
        		//This is grub-to-wheat collision detection
        		if (badArmy.getGrub(k).hasTarget())
        			if (badArmy.grubColSprite(badArmy.getGrub(k), badArmy.getGrub(k).getTarget()))
        			{
        				badArmy.getGrub(k).setVelocityX(0);
        				badArmy.getGrub(k).setVelocityY(0);
        				badArmy.getGrub(k).getTarget().loseHealth(Grub.grubbyHunger);
        			}
        		
        		//This is grub-to-grub collision detection
        		for (int j = k+1; j < badArmy.getSize(); j++)
        		{
        			if (badArmy.grubColSprite(badArmy.getGrub(k), badArmy.getGrub(j)) && !badArmy.getGrub(j).isWander())
					{
        				badArmy.getGrub(j).startWander();
        				badArmy.getGrub(j).lostTarget();
					}
        			
        		}
        			
        	}
        
	}

	private void grubArmyThink() {
		//Grubby AI
        for (int k = 0; k < badArmy.getSize(); k++)
        {
        	badArmy.getGrub(k).setMidGrubX();
        	badArmy.getGrub(k).setMidGrubY();
        }
        for (int k = 0; k < field.getSize(); k++)
        {
        	field.getWheat(k).setMidWheatX();
        	field.getWheat(k).setMidWheatY();
        }
        	
        
        float midTargetX;
        float midTargetY;
        
        float midplayX = player.getX() + (player.getWidth() / 2);
        float midplayY = player.getY() + (player.getHeight() / 2);
        float distanceAroundPlayer = 200;
        
        //This is for Grubby looking where to go
        for (int k = 0; k < badArmy.getSize(); k++)
        {
        	
        	
        	//This is finding a wheat for the grubby to eat :)
        	if (field.getSize() != 0)
        	{
        		if (!badArmy.getGrub(k).hasTarget())
        		{
        			badArmy.getGrub(k).resetDistances();
        			for (int j = 0; j < field.getSize(); j++)
        				badArmy.getGrub(k).addDistance(Math.sqrt(Math.pow(field.getWheat(j).getMidWheatX() - badArmy.getGrub(k).getMidGrubX(), 2) + Math.pow(field.getWheat(j).getMidWheatY() - badArmy.getGrub(k).getMidGrubY(), 2)));
        		
        			double minimum = 999999;
        			int targetFinder = -1;
        			//Put a finding algorithm here.
        			for (int l = 0; l < badArmy.getGrub(k).getNumOfDistances(); l++)
        			{
        				if (badArmy.getGrub(k).getDistance(l) < minimum)
        				{
        					targetFinder = l;
        					minimum = badArmy.getGrub(k).getDistance(l);
        				}
        			}
        		
        			if (targetFinder < field.getSize())
        			{
        				badArmy.getGrub(k).setTarget(field.getWheat(targetFinder));
        				badArmy.getGrub(k).foundTarget();
        			}
        		
        		}
        	}
        	
        	
        	if (((badArmy.getGrub(k).getMidGrubX() < midplayX + distanceAroundPlayer && badArmy.getGrub(k).getMidGrubX() > midplayX - distanceAroundPlayer)
        			&& (badArmy.getGrub(k).getMidGrubY() < midplayY + distanceAroundPlayer && badArmy.getGrub(k).getMidGrubY() > midplayY - distanceAroundPlayer))
        			|| field.getSize() == 0)
        	{
        		badArmy.getGrub(k).lostTarget();
        		badArmy.getGrub(k).setVelocityX(badArmy.getGrub(k).followPlayer(midplayX, badArmy.getGrub(k).getMidGrubX()));
        		badArmy.getGrub(k).setVelocityY(badArmy.getGrub(k).followPlayer(midplayY, badArmy.getGrub(k).getMidGrubY()));
        	}
        		
        	else if (badArmy.getGrub(k).hasTarget())
        	{
        		midTargetX = badArmy.getGrub(k).getTarget().getX() + (badArmy.getGrub(k).getTarget().getWidth() / 2);
        		midTargetY = badArmy.getGrub(k).getTarget().getY() + (badArmy.getGrub(k).getTarget().getHeight() / 2);
        		badArmy.getGrub(k).setVelocityX(badArmy.getGrub(k).followWheat(midTargetX, badArmy.getGrub(k).getMidGrubX()));
        		badArmy.getGrub(k).setVelocityY(badArmy.getGrub(k).followWheat(midTargetY, badArmy.getGrub(k).getMidGrubY()));
        	}
        	
        	else
        	{
        		badArmy.getGrub(k).setVelocityX(0);
        		badArmy.getGrub(k).setVelocityY(0);
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
        
        if (player.getWeapon().getCurrentAmmo() <= 0 && player.getWeapon().equals("BearTrapHold"))
        {
        	int weaponIndex = player.getCurrentWeapon();
        	player.prevWeapon();
        	player.deleteWeapon(weaponIndex);
        	weaponToggle = false;
        	
        	shopMenu.getButton(4).release();
        }
        
        if (nextWeapon.isPressed() && nextWeaponReleased)
        {
        	player.nextWeapon();
        	weaponToggle = false;
        	nextWeaponReleased = false;
        }
        else if (!nextWeapon.isPressed())
        {
        	nextWeaponReleased = true;
        }
        
        if (prevWeapon.isPressed() && prevWeaponReleased)
        {
        	player.prevWeapon();
        	weaponToggle = false;
        	prevWeaponReleased = false;
        }
        else if (!prevWeapon.isPressed())
        {
        	prevWeaponReleased = true;
        }
        
        //This is weapon Behavior from HERE
        if (weaponToggle && weaponEndTime >= weaponStartTime)
        {
        	weaponStartTime = System.currentTimeMillis();
        }
        else if (weaponToggle)
        {
        	weaponToggle = false;
        }
        
        if (attack.isPressed() && !weaponToggle && attackReleased)
        {
        	//player.getWeapon(0).useWeapon();
        	if (player.getWeapon().equals("BearTrapHold"))
        	{
        		fieldWeapons.add(setBearTrap(player.getX(), player.getY()));
        		player.getWeapon().deload();
        	}
        	
        	player.getWeapon().getAnim().start();
        	weaponToggle = true;
        	weaponStartTime = System.currentTimeMillis();
        	weaponEndTime = System.currentTimeMillis() + player.getWeapon().getWeaponTime();
        	attackReleased = false;
        }
        else if (!attack.isPressed())
        {
        	attackReleased = true;
        }
        
        if (player.getWeapon().equals("Tractor"))
        {
        	weaponToggle = true;
        }
        
        if (weaponToggle)
        {
        	player.getWeapon().useWeapon();
        }
        else
        {
        	player.getWeapon().stopWeapon();
        }
        //to HERE
        
        if (moveLeft.isPressed()) {
        	player.setIdleL();
            velocityX-=player.SPEED;
            playerGoingLeft = true;
        }

        if (moveRight.isPressed()) {
        	player.setIdleR();
            velocityX+=player.SPEED;
            playerGoingLeft = false;
        }
        
        if (moveUp.isPressed()) {
        	player.setIdleU();
            velocityY-=player.SPEED;
        }
        
        if (moveDown.isPressed()) {
        	player.setIdleU();
            velocityY+=player.SPEED;
        }
        
        
        //Following four ifs are for staying in-bounds
        if (player.getX() < 0 && !moveRight.isPressed())
        {
        	velocityX = 0;
        }
        if (player.getX() + player.getWidth() > screen.getWidth() && !moveLeft.isPressed())
        {
        	velocityX = 0;
        }
        
        if (player.getY() < 0 && !moveDown.isPressed())
        {
        	velocityY = 0;
        }
        if (player.getY() + player.getHeight() > nineTenY && !moveUp.isPressed())
        {
        	velocityY = 0;
        }
        
        player.setVelocityX(velocityX);
        player.setVelocityY(velocityY);
        
        player.getWeapon().updateLocation(player.getX(), player.getY(), player.getWidth(), player.getHeight(), player.getWeapon().getWidth(), player.getWeapon().getHeight(), playerGoingLeft);
        
	}
	
	private boolean pauseIsPressed = false;
	
	private void checkSystemInput() {
		
		if (pause.isPressed())
		{
			if (isPaused && !pauseIsPressed)
			{
				isPaused = false;
				inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
			}
			else if (!pauseIsPressed)
			{
				isPaused = true;
				inputManager.setCursor(normMouse);
			}
			pauseIsPressed = true;
		}
		else
		{
			pauseIsPressed = false;
		}
		
		if (isPaused)
			pauseMenuMouseInteraction();
		
		if (isShop)
		{
			inputManager.setCursor(normMouse);
			shopMenuMouseInteraction();
		}
			
		
		if (isStart)
		{
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
		
		for (int k = player.getAmountOfWeapons()-1; k >= 0; k--)
			if (player.getWeapon(k).getCurrentAmmo() == 0)
			{
				if (player.getCurrentWeapon() == k)
					player.prevWeapon();
				player.deleteWeapon(k);
				weaponToggle = false;

				shopMenu.getButton(3).release();
				shopMenu.getButton(5).release();
			}
		
		//This if statement is for the Shop Continue Button
		if (mouseClick.isPressed() && shopMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			shopMenu.getButton(0).press();
			isShop = false;
			inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
			mouseButtonIsPressed = true;
		}
		
		//Add Health Button Code
		else if (mouseClick.isPressed() && shopMenu.getButton(1).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			if (moneyNumber - costOfHealth >= 0)
			{
				if (player.getHealth() < 100 && player.getHealth()+25 <= 100)
				{
					shopMenu.getButton(1).press();
					player.addHealth(25);
			
					moneyNumber-=costOfHealth;
					DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
					moneyInfo.setText("Money: " + mf.format(moneyNumber));
				}
			
				else if (player.getHealth() < 100 && player.getHealth()+25 > 100)
				{
					shopMenu.getButton(1).press();
					double healthAmt = 100 - player.getHealth();
					double moneyAmt = Math.round((healthAmt * costOfHealth) / 25);
					player.addHealth(healthAmt);
			
					moneyNumber-=moneyAmt;
					DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
					moneyInfo.setText("Money: " + mf.format(moneyNumber));
				}
			}
			
			mouseButtonIsPressed = true;
		}
		
		//Refill Wheat Button Code
		else if (mouseClick.isPressed() && shopMenu.getButton(2).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			int counter = LocationCapture.getSize();
			while (counter > 0)
			{
				if (moneyNumber - (Wheat.costOfWheat*2) >= 0)
				{
					shopMenu.getButton(2).press();
					moneyNumber -= (Wheat.costOfWheat * 2);
					DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
					moneyInfo.setText("Money: " + mf.format(moneyNumber));
					field.addWheat(new Wheat(wheatAnim));
					field.setWheatLoc(field.getSize()-1, LocationCapture.getLocX(counter-1), LocationCapture.getLocY(counter-1));
					LocationCapture.deleteLastLoc();
				}
				counter--;
			}
			
			mouseButtonIsPressed = true;
		}
		
		//Buy Spray Can Code
		else if (mouseClick.isPressed() && shopMenu.getButton(3).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			if (!shopMenu.getButton(3).isPressed())
			{
				if ((moneyNumber - costOfSprayCan) >= 0)
				{
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
		else if (mouseClick.isPressed() && shopMenu.getButton(4).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			if (!shopMenu.getButton(4).isPressed())
			{
				if ((moneyNumber - costOfBearTrap) >= 0)
				{
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
		else if (mouseClick.isPressed() && shopMenu.getButton(5).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			if (!shopMenu.getButton(5).isPressed())
			{
				if ((moneyNumber - costOfTractor) >= 0)
				{
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
		else if (!mouseClick.isPressed())
		{
			for (int k = 0; k < shopMenu.getAmmountOfButtons(); k++)
			{
				if (!shopMenu.getButton(k).equals("BuySprayCan") &&
					!shopMenu.getButton(k).equals("BuyBearTraps") &&
					!shopMenu.getButton(k).equals("BuyTractor"))
					shopMenu.getButton(k).release();
			}
			mouseButtonIsPressed = false;
		}
	}

	private void startMenuMouseInteraction() {
		if (mouseClick.isPressed() && startMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			startMenu.getButton(0).press();
			isStart = false;
			inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
			mouseButtonIsPressed = true;
		}
		
		//Hard Mode Button:
		else if (mouseClick.isPressed() && startMenu.getButton(1).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			startMenu.getButton(1).press();
			startMenu.getButton(2).release();
			startMenu.getButton(3).release();
			Grub.SPEED = (float) .2;
			Grub.grubbyHunger = .25;
			Grub.grubbyStrength = .05;
			mouseButtonIsPressed = true;
		}
		
		//Medium Mode Button:
		else if (mouseClick.isPressed() && startMenu.getButton(2).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			startMenu.getButton(2).press();
			startMenu.getButton(3).release();
			startMenu.getButton(1).release();
			Grub.SPEED = (float) .16;
			Grub.grubbyHunger = .125;
			Grub.grubbyStrength = .025;
			mouseButtonIsPressed = true;
		}
		
		//Easy Mode Button:
		else if (mouseClick.isPressed() && startMenu.getButton(3).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			startMenu.getButton(3).press();
			startMenu.getButton(1).release();
			startMenu.getButton(2).release();
			Grub.SPEED = (float) .12;
			Grub.grubbyHunger = .0625;
			Grub.grubbyStrength = .0125;
			mouseButtonIsPressed = true;
		}
		
		else if (!mouseClick.isPressed())
		{
			mouseButtonIsPressed = false;
		}
		
	}

	//button 0 is close,
	private void pauseMenuMouseInteraction() {
		
		//This if statement is for the Close Button
		if (mouseClick.isPressed() && pauseMenu.getButton(0).isInBounds(inputManager.getMouseX(), inputManager.getMouseY()) && !mouseButtonIsPressed)
		{
			pauseMenu.getButton(0).press();
			musicThr.interrupt();
			stop();
			mouseButtonIsPressed = true;
		}
		
		else if (!mouseClick.isPressed())
		{
			mouseButtonIsPressed = false;
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		// draw background
		g.setColor(Color.green);
        //g.fillPolygon(bgImage);
		for (int k = 0; k < grassGrid.size(); k++)
		{
			
			g.drawImage(grassGrid.get(k).getImage(), (int) grassGrid.get(k).getX(), (int) grassGrid.get(k).getY(), null);
		}
        
        
        //draw farmland
        g.setColor(MyBrown);
        //g.fillPolygon(farmLand);
        for (int k = 0; k < dirtGrid.size(); k++)
			g.drawImage(dirtGrid.get(k).getImage(), (int) dirtGrid.get(k).getX(), (int) dirtGrid.get(k).getY(), null);
		
        //Draw Wheat
        g.setColor(Color.black);
        for (int k = 0; k < field.getSize(); k++)
        	g.drawImage(field.getWheat(k).getImage(), (int) field.getWheat(k).getX(), (int) field.getWheat(k).getY(), null);
        
        //Draw Springed Bear Traps
        for (int k = 0; k < springedBearTraps.size(); k++)
        {
        	g.drawImage(springedBearTraps.get(k).getImage(), (int) springedBearTraps.get(k).getX(), (int) springedBearTraps.get(k).getY(), null);
        }
        
        //Draw Field Weapons
        for (int k = 0; k < fieldWeapons.size(); k++)
        {
        	g.drawImage(fieldWeapons.get(k).getImage(), (int) fieldWeapons.get(k).getX(), (int) fieldWeapons.get(k).getY(), null);
        }
        
        //draw Grubby
        g.setColor(Color.black);
        //g.drawImage(grubby.getImage(),(int) grubby.getX(),(int) grubby.getY(), null);
        for (int k = 0; k < badArmy.getSize(); k++)
        	g.drawImage(badArmy.getGrub(k).getImage(), (int) badArmy.getGrub(k).getX(), (int) badArmy.getGrub(k).getY(), null);
        
        //draw Grubby Health Bar and Poison Icon
        g.setColor(Color.red);
        for (int k = 0; k < badArmy.getSize(); k++)
        {
        	if (badArmy.getGrub(k).getHealth() != 100)
        		g.fillRect((int) badArmy.getGrub(k).getX(),(int) (badArmy.getGrub(k).getY() - 5), (int) badArmy.getGrub(k).getHealthBarSize(), 5);
        	
        	if (badArmy.getGrub(k).isPoisoned())
        		g.drawImage(badArmy.getGrub(k).getPoisonIcon().getImage(), (int) badArmy.getGrub(k).getPoisonIcon().getX(), (int) badArmy.getGrub(k).getPoisonIcon().getY(), null);
        }
        
        //draw Wheat Health Bar
        g.setColor(Color.blue);
        for (int k = 0; k < field.getSize(); k++)
        {
        	if (field.getWheat(k).getHealth() != 100)
        		g.fillRect((int) field.getWheat(k).getX(),(int) (field.getWheat(k).getY() - 5), (int) field.getWheat(k).getHealthBarSize(), 5);
        }
        
        // draw player and Weapon
        if (player.getWeapon().getActive())
        	g.drawImage(player.getWeapon().getImage(), (int) player.getWeapon().getX(), (int) player.getWeapon().getY(), null);
        g.setColor(Color.black);
        if (!player.getWeapon().equals("Tractor"))
        	g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), null);
        
        drawLowerGUI(g);
        
        //If the game is over do this:
        if (gameOver)
        {
        	g.setFont(new Font("TimesRoman", Font.PLAIN, 212));
        	drawCenteredStringX("Game Over", screen.getWidth(), screen.getHeight(), g);
        }
        
        //Start Menu
        if (isStart)
        {
        	g.setFont(new Font("Dialog", Font.PLAIN, 24));
        	startMenu.draw(g);
        }
        
        if (isShop)
        {
        	g.setFont(new Font("Dialog", Font.PLAIN, 24));
        	shopMenu.draw(g);
        }
        
        //Pause Menu
        if (isPaused)
        {
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
        g.fillRect(threeTwentyX, fourteenFifteenY,(int) player.getHealth() * 3, screen.getHeight() - twentyNineThirtyY);
        
        //draw Health Bar Outline
        g.setColor(Color.white);
        g.drawRect(threeTwentyX, fourteenFifteenY, playerHBSize, screen.getHeight() - twentyNineThirtyY);
        
        waveInfo.draw(g);
        moneyInfo.draw(g);
        lblHealth.draw(g);
        weaponInfo.draw(g);
        ammoInfo.draw(g);
		
	}

	private void createGameActions()
	{
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
	
	public static Animation grubIdleR, grubIdleL, grubIdleU, grubIdleD, grubMoveR, grubMoveL, grubMoveU, grubMoveD, wheatAnim;
	
	public static Grub createNormalGrub()
	{
		return new Grub(grubIdleR, grubIdleL, grubIdleD, grubIdleU, grubMoveR, grubMoveL, grubMoveD, grubMoveU);
	}
	
	public static Weapon setBearTrap(float x, float y)
	{
		Weapon w = new Weapon(Weapon.bearTrapAnimation, 100, 5, "BearTrap", Double.POSITIVE_INFINITY);
		w.setX(x);
		w.setY(y);
		return w;
	}
	
	public static Sprite setSpringedBearTrap(float x, float y)
	{
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
	
	
	
	private void startMoosic() throws LineUnavailableException
	{
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
