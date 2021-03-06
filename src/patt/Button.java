package patt;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Button {

    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 33;

    private int xLoc;
    private int yLoc;
    private int width;
    private int height;
    private int txtX;
    private int txtY;
    private String text;
    private String name;
    private boolean pressed;
    private boolean first;
    private Color buttColor;

    public Button(int x, int y, int w, int h, String t, String n) {
        xLoc = x;
        yLoc = y;
        width = w;
        height = h;
        text = t;
        name = n;
        buttColor = Color.yellow;
        first = true;

        txtX = 0;
        txtY = 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(buttColor);
        g.fillRect(xLoc, yLoc, width, height);

        if (first) {
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(text, g);
            txtX = (xLoc + width / 2) - ((int) r.getWidth() / 2); //(int) (((xLoc + width) - (int) r.getWidth()) / 1.1);
            txtY = (yLoc + height / 2) + ((int) r.getHeight() / 4); //(int) ((yLoc + height) - (r.getHeight() / 2));
            first = false;
        }

        g.setColor(Color.black);
        g.drawString(text, txtX, txtY);
    }

    public void press() {
        pressed = true;
        buttColor = Color.green;
    }

    public void release() {
        pressed = false;
        buttColor = Color.yellow;
    }

    public void colorOverride(Color c) {
        buttColor = c;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setText(String s) {
        text = s;
    }

    public boolean equals(String n) {
        return this.name.equals(n);
    }

    public boolean isInBounds(int x, int y) {
        return (x >= xLoc && x <= xLoc + width && y >= yLoc && y <= yLoc + height);
    }
}
