package patt;

import java.awt.Color;
import java.awt.Image;
import java.awt.Polygon;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import brackeen.*;

public class CreatorMethods {
	
	public static Weapon woodBat;
	public static Weapon sprayCan;
	public static Weapon bearTrapHold;
	public static Weapon Tractor;
	
	public static void createSprites()
	{
		//This is to set sprites relative to the size of the screen
		AdventMain.twoFifthX = (AdventMain.screen.getWidth() * 2) / 5;
		AdventMain.fiveSixtY = (AdventMain.screen.getHeight() * 5) / 16;
		AdventMain.threeFifthX = (AdventMain.screen.getWidth() * 3) / 5;
		AdventMain.elevenSixtY = (AdventMain.screen.getHeight() * 11) / 16;
		AdventMain.oneTwentyX = AdventMain.screen.getWidth() / 20;
		AdventMain.oneTwentyY = AdventMain.screen.getHeight() / 20;
		AdventMain.nineteenTwentyX = (AdventMain.screen.getWidth() * 19) / 20;
		AdventMain.nineteenTwentyY = (AdventMain.screen.getHeight() * 19) / 20;
		AdventMain.twentyNineThirtyY = (AdventMain.screen.getHeight() * 29) / 30;
		AdventMain.fourteenFifteenY = (AdventMain.screen.getHeight() * 14) / 15;
		AdventMain.threeTwentyX = (AdventMain.screen.getWidth() * 3) / 20;
		AdventMain.oneFourthRight = AdventMain.screen.getWidth() / 4;
		AdventMain.oneFourthDown = AdventMain.screen.getHeight() / 4;
		AdventMain.oneFourthLeft = (AdventMain.screen.getWidth() * 3) / 4;
		AdventMain.oneFourthUp = (AdventMain.screen.getHeight() * 3) / 4;
		AdventMain.threeFortyX = (AdventMain.screen.getWidth() * 3) / 40;
				
		//This is setting up the Start and Pause Menus
				
		CreatorMethods.createGUIStuff();
				
		LocationCapture.init();
				
		//Defining new Color
		AdventMain.MyBrown = new Color(156,95,58);
				
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
				
			while (greenY <= AdventMain.screen.getHeight())
			{
				while (greenX <= AdventMain.screen.getWidth())
				{
						
					AdventMain.grassGrid.add(new Sprite(grassAnim));
					AdventMain.grassGrid.get(AdventMain.grassGrid.size() - 1).setX(greenX);
					AdventMain.grassGrid.get(AdventMain.grassGrid.size() - 1).setY(greenY);
					greenX += grassImage.getWidth(null);
				}
				greenX = 0;
				greenY += grassImage.getHeight(null);
			}
				
			//Defining the black Rectangle
			AdventMain.nineTenY = (AdventMain.screen.getHeight() * 9) / 10;
				
			AdventMain.blackRect = new Polygon();
			AdventMain.blackRect.addPoint(0, AdventMain.nineTenY);
			AdventMain.blackRect.addPoint(AdventMain.screen.getWidth(), AdventMain.nineTenY);
			AdventMain.blackRect.addPoint(AdventMain.screen.getWidth(), AdventMain.screen.getHeight());
			AdventMain.blackRect.addPoint(0, AdventMain.screen.getHeight());
				
			//Defining the Farmland
				
				
			/*	farmLand = new Polygon();
				farmLand.addPoint(oneFourthRight, oneFourthDown);
				farmLand.addPoint(oneFourthLeft, oneFourthDown);
				farmLand.addPoint(oneFourthLeft, oneFourthUp);
				farmLand.addPoint(oneFourthRight, oneFourthUp);  */
			Image dirtImage = GameCore.loadImage("res/dirt.png");
				
			Animation dirtAnim = new Animation();
			dirtAnim.addFrame(dirtImage, 500);
				
			int dirtX = AdventMain.oneFourthRight;
			int dirtY = AdventMain.oneFourthDown;
				
			AdventMain.dirtGrid = new ArrayList<Sprite>();
				
			while (dirtY < AdventMain.oneFourthUp)
			{
				while (dirtX < AdventMain.oneFourthLeft)
				{
						
					AdventMain.dirtGrid.add(new Sprite(dirtAnim));
					AdventMain.dirtGrid.get(AdventMain.dirtGrid.size() - 1).setX(dirtX);
					AdventMain.dirtGrid.get(AdventMain.dirtGrid.size() - 1).setY(dirtY);
					dirtX += dirtImage.getWidth(null);
				}
				dirtX = AdventMain.oneFourthRight;
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
			AdventMain.player.setX(AdventMain.screen.getWidth() / 2 - (AdventMain.player.getWidth() / 2));
			AdventMain.player.setY(AdventMain.screen.getHeight() / 2 - (AdventMain.player.getHeight() / 2));
				
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
			//AdventMain.player.addWeapon(Tractor);
			//AdventMain.player.addWeapon(bearTrapHold);
			//AdventMain.player.addWeapon(sprayCan);
			AdventMain.player.getWeapon().updateLocation(AdventMain.player.getX(), AdventMain.player.getY(), AdventMain.player.getWidth(), AdventMain.player.getHeight(), AdventMain.player.getWeapon().getWidth(),AdventMain.player.getWeapon().getHeight(), true);
				
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
				
			//wheat = new Wheat(wheatAnim);
			//wheat.setX((float) oneFourthRight);
			//wheat.setY((float) oneFourthDown);
				
				
			//wheat creation
			float oxLoc = AdventMain.oneFourthRight;
			float oyLoc = AdventMain.oneFourthDown;
			float rightOfWheat = 0;
			float bottomOfWheat;
			int horizontalWheat = 0;
			int verticalWheat = 0;
			boolean h = false;
			int counter = 0;
				
			while (oyLoc + wheatImage.getHeight(null) < AdventMain.oneFourthUp)
			{
				AdventMain.field.addWheat(new Wheat(AdventMain.wheatAnim));
				AdventMain.field.setWheatLoc(counter, oxLoc, oyLoc);
				oxLoc += wheatImage.getWidth(null);
				if (!h)
					horizontalWheat++;
				if (oxLoc >= AdventMain.oneFourthLeft - wheatImage.getWidth(null))
				{
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
				
			while (cVWheat < verticalWheat)
			{
				AdventMain.field.setWheatLoc(counter, oxLoc, oyLoc);
				AdventMain.field.getWheat(counter).setHealthBarSize(wheatImage.getWidth(null));
				oxLoc += wheatImage.getWidth(null);
				cHWheat++;
				if (cHWheat == horizontalWheat)
				{
					cHWheat = 0;
					cVWheat++;
					oxLoc = AdventMain.oneFourthRight + rightDifference;
					oyLoc += wheatImage.getHeight(null);
				}
				counter++;
			}
				
			//Grub Army Creation

			Grub g = new Grub();
			AdventMain.badArmy = new GrubArmy(g.grubImageWidth, g.grubImageHeight);
			
			AdventMain.gStarters = new GrubStartLocation(AdventMain.screen.getWidth(), AdventMain.nineTenY, g.grubImageWidth, g.grubImageHeight);
			for (int k = 0; k < AdventMain.numOfGrubby; k++) {
				Grub grub = new Grub();
				AdventMain.badArmy.addGrub(grub);
				AdventMain.gStarters.chooseLoc();
				AdventMain.badArmy.setGrubLoc(k, AdventMain.gStarters.getX(), AdventMain.gStarters.getY());
				AdventMain.badArmy.getGrub(k).setHealthBarSize(grub.grubImageWidth);
			}
				
			//this is for the health bar outline later
			AdventMain.playerHBSize = (int) AdventMain.player.getHealth() * 3;
					
				
	}
	
	public static void createGUIStuff()
	{

		Crop pauseCrop = new Crop("res/pauseWood.png", AdventMain.twoFifthX, AdventMain.fiveSixtY, AdventMain.threeFifthX - AdventMain.twoFifthX, AdventMain.elevenSixtY - AdventMain.fiveSixtY);
		Crop startCrop = new Crop("res/pauseWood.png", AdventMain.oneTwentyX, AdventMain.oneTwentyY, AdventMain.nineteenTwentyX - AdventMain.oneTwentyX, AdventMain.nineteenTwentyY - AdventMain.oneTwentyY);
		//Crop shopCrop = new Crop("res/shopBrick.png", twoFifthX, fiveSixtY, threeFifthX - twoFifthX, elevenSixtY - fiveSixtY);
		Image pauseCImage = pauseCrop.getImage();
		Image startCImage = startCrop.getImage();
		Image shopCImage = new ImageIcon("res/shopBrick.png").getImage();
		AdventMain.pauseMenu = new MyGui(pauseCImage, AdventMain.twoFifthX, AdventMain.fiveSixtY, AdventMain.threeFifthX - AdventMain.twoFifthX, AdventMain.elevenSixtY - AdventMain.fiveSixtY, "Paused");
		AdventMain.startMenu = new MyGui(startCImage, AdventMain.oneTwentyX, AdventMain.oneTwentyY, AdventMain.nineteenTwentyX - AdventMain.oneTwentyX, AdventMain.nineteenTwentyY - AdventMain.oneTwentyY, "Instructions");
		AdventMain.shopMenu = new ShopGui(shopCImage, AdventMain.twoFifthX, AdventMain.fiveSixtY, AdventMain.threeFifthX - AdventMain.twoFifthX, AdventMain.elevenSixtY - AdventMain.fiveSixtY, "Shop");
		
		
		//TODO Make the new buttons relative to the screen size and labels
		MyButt btnClose = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , (AdventMain.elevenSixtY * 5) / 6, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Quit", "Close");
		MyButt btnShopCont = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , ((AdventMain.elevenSixtY * 4) / 6) + 150, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Continue", "ShopCont");
		MyButt btnBuyTractor = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , ((AdventMain.elevenSixtY * 4) / 6) + 100, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Tractor $5000", "BuyTractor");
		MyButt btnHealthAdder = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , (AdventMain.elevenSixtY * 4) / 6, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "+25 Health for $500", "HealthAdder");
		MyButt btnBuyField = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , ((AdventMain.elevenSixtY * 4) / 6) - 50, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Replant Field", "BuyField");
		MyButt btnBuySpray = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , ((AdventMain.elevenSixtY * 4) / 6) - 100, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Spray Can $2000", "BuySprayCan");
		MyButt btnBuyBearTraps = new MyButt(((AdventMain.twoFifthX + AdventMain.threeFifthX) / 2) - MyButt.BUTTON_WIDTH / 2 , ((AdventMain.elevenSixtY * 4) / 6) + 50, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Bear Traps $1000", "BuyBearTraps");
		MyButt btnStartCont = new MyButt(((AdventMain.oneTwentyX + AdventMain.nineteenTwentyX) / 2) - MyButt.BUTTON_WIDTH / 2 , (AdventMain.nineteenTwentyY * 9) / 10, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Continue", "StartCont");
		MyButt btnStartHardM = new MyButt(((AdventMain.oneTwentyX + AdventMain.nineteenTwentyX) / 2) - (MyButt.BUTTON_WIDTH / 2) - 300, (AdventMain.nineteenTwentyY * 9) / 10, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Hard", "StartHardM");
		MyButt btnStartMediumM = new MyButt(((AdventMain.oneTwentyX + AdventMain.nineteenTwentyX) / 2) - (MyButt.BUTTON_WIDTH / 2) - 300, ((AdventMain.nineteenTwentyY * 9) / 10) - 50, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Medium", "StartMediumM");
		MyButt btnStartEasyM = new MyButt(((AdventMain.oneTwentyX + AdventMain.nineteenTwentyX) / 2) - (MyButt.BUTTON_WIDTH / 2) - 300, ((AdventMain.nineteenTwentyY * 9) / 10) - 100, MyButt.BUTTON_WIDTH, MyButt.BUTTON_HEIGHT, "Easy", "StartEasyM");
		
		MyLabel lblWASD = new MyLabel(100, 200, "Use the WASD keys to Move Around the Field. Spacebar to attack.");
		MyLabel lblExplain = new MyLabel(100, 400, "The Grubs are bad, they are trying to eat your crops!! Stop them before they eat your field!");
		
		AdventMain.pauseMenu.addMyButton(btnClose);
		AdventMain.startMenu.addMyButton(btnStartCont);
		AdventMain.startMenu.addMyButton(btnStartHardM);
		AdventMain.startMenu.addMyButton(btnStartMediumM);
		AdventMain.startMenu.addMyButton(btnStartEasyM);
		AdventMain.shopMenu.addMyButton(btnShopCont);
		AdventMain.shopMenu.addMyButton(btnHealthAdder);
		AdventMain.shopMenu.addMyButton(btnBuyField);
		AdventMain.shopMenu.addMyButton(btnBuySpray);
		AdventMain.shopMenu.addMyButton(btnBuyBearTraps);
		AdventMain.shopMenu.addMyButton(btnBuyTractor);
		AdventMain.startMenu.addMyLabel(lblWASD);
		AdventMain.startMenu.addMyLabel(lblExplain);
		
		AdventMain.startMenu.getButton(1).press();
		//TODO Finish LowerGUI Information
		//LowerGui code Starts Here: 
		AdventMain.waveNumber = 1;
		AdventMain.moneyNumber = AdventMain.MONEY_START;
		DecimalFormat mf = new DecimalFormat("$#,###,##0.00");
		
		AdventMain.waveInfo = new MyLabel(AdventMain.twoFifthX, AdventMain.fourteenFifteenY+(AdventMain.screen.getHeight() - AdventMain.twentyNineThirtyY), "Wave: " + AdventMain.waveNumber);
		//moneyInfo = new MyLabel(oneFourthLeft, fourteenFifteenY+(screen.getHeight() - twentyNineThirtyY), "Money: " + mf.format(moneyNumber));
		AdventMain.moneyInfo = new MyLabel(AdventMain.twoFifthX, AdventMain.fourteenFifteenY, "Money: " + mf.format(AdventMain.moneyNumber));
		AdventMain.lblHealth = new MyLabel(AdventMain.threeFortyX, AdventMain.fourteenFifteenY+(AdventMain.screen.getHeight() - AdventMain.twentyNineThirtyY), "Health: ");
		AdventMain.weaponInfo = new MyLabel(AdventMain.threeFifthX, AdventMain.fourteenFifteenY, "Current Weapon: WoodBat");
		AdventMain.ammoInfo = new MyLabel(AdventMain.threeFifthX, AdventMain.fourteenFifteenY+(AdventMain.screen.getHeight() - AdventMain.twentyNineThirtyY), "Wave Ammo: " + Double.POSITIVE_INFINITY);
	}

}
