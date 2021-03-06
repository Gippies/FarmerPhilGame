package patt;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Gui {

    private int xLoc;
    private int yLoc;
    private int width;
    private int height;
    private Image bgImage;
    private String title;
    private Color color;
    private ArrayList<Button> buttons;
    private ArrayList<MyLabel> labels;

    private boolean first;
    private int titX;
    private int titY;

    public Gui(Image im, int x, int y, int w, int h, String t) {
        bgImage = im;
        xLoc = x;
        yLoc = y;
        width = w;
        height = h;
        color = Color.black;
        buttons = new ArrayList<Button>();
        labels = new ArrayList<MyLabel>();
        title = t;

        first = true;
        titX = 0;
        titY = 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawImage(bgImage, xLoc, yLoc, width, height, null);

        if (first) {
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(title, g);
            titX = (xLoc + width / 2) - (int) r.getWidth() / 2;
            titY = (int) (yLoc + r.getHeight());
            first = false;
        }

        g.drawString(title, titX, titY);

        for (Button button : buttons) {
            button.draw(g);
        }

        for (MyLabel label : labels) {
            label.draw(g);
        }

    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addMyButton(Button b) {
        buttons.add(b);
    }

    public void addMyLabel(MyLabel l) {
        labels.add(l);
    }

    public Button getButton(int x) {
        return buttons.get(x);
    }

    public int getAmmountOfButtons() {
        return buttons.size();
    }
}
