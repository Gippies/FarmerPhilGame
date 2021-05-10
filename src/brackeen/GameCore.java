package brackeen;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Simple abstract class used for testing. Subclasses should implement the
 * draw() method.
 */
public abstract class GameCore {

    protected static final int FONT_SIZE = 24;

    private static final DisplayMode[] POSSIBLE_MODES = {
        new DisplayMode(3840, 2160, 32, 0),
        new DisplayMode(1920, 1080, 32, 0),
        new DisplayMode(1366, 768, 32, 0),
        new DisplayMode(1280, 1024, 32, 0),
        new DisplayMode(800, 600, 32, 0),
    };

    private static final Map<String, Image> imageMap = new HashMap<>();
    private boolean isRunning;
    public static ScreenManager screenManager;

    /**
     * Signals the game loop that it's time to quit
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Calls init() and gameLoop()
     */
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screenManager.restoreScreen();
            lazilyExit();
        }
    }

    /**
     * Exits the VM from a daemon thread. The daemon thread waits 2 seconds then
     * calls System.exit(0). Since the VM should exit when only daemon threads
     * are running, this makes sure System.exit(0) is only called if neccesary.
     * It's neccesary if the Java Sound system is running.
     */
    public void lazilyExit() {
        Thread thread = new Thread() {
            public void run() {
                // first, wait for the VM exit on its own.
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
                // system is still running, so force an exit
                System.exit(0);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Sets full screen mode and initiates and objects.
     */
    public void init() {
        screenManager = new ScreenManager();
        DisplayMode displayMode = screenManager
                .findFirstCompatibleMode(POSSIBLE_MODES);
        screenManager.setFullScreen(displayMode);

        Window window = screenManager.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.blue);
        window.setForeground(Color.white);

        isRunning = true;
    }

    public static Image loadImage(String fileName) {
        if (!imageMap.containsKey(fileName)) {
            imageMap.put(fileName, new ImageIcon(fileName).getImage());
        }
        return imageMap.get(fileName);
    }

    /**
     * Runs through the game loop until stop() is called.
     */
    public void gameLoop() {
        long currTime = System.currentTimeMillis();

        while (isRunning) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            update(elapsedTime);

            // draw the screen
            Graphics2D g = screenManager.getGraphics();
            draw(g);
            g.dispose();
            screenManager.update();
        }
    }

    /**
     * Updates the state of the game/animation based on the amount of elapsed
     * time that has passed.
     */
    public void update(long elapsedTime) {
        // do nothing
    }

    /**
     * Draws to the screen. Subclasses must override this method.
     */
    public abstract void draw(Graphics2D g);
}
