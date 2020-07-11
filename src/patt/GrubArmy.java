package patt;

import java.util.ArrayList;

import brackeen.*;

public class GrubArmy {
    private ArrayList<Grub> army = new ArrayList<Grub>();
    private int grubNum;
    private int totWidth;
    private int totHeight;

    public GrubArmy(int w, int h) {
        grubNum = 0;
        totWidth = w;
        totHeight = h;
    }

    public void setGrubLoc(int c, float x, float y) {
        army.get(c).setX(x);
        army.get(c).setY(y);
    }

    public Grub getGrub(int x) {
        return army.get(x);
    }

    public void addGrub(Grub g) {
        army.add(g);
        grubNum++;
    }


    public boolean grubColSprite(Grub g, Sprite s) {
        return (g.getX() <= s.getX() + s.getWidth()
                && g.getX() + totWidth >= s.getX()
                && g.getY() <= s.getY() + s.getHeight()
                && g.getY() + totHeight >= s.getY());
    }

    public void deleteGrub() {
        grubNum--;
    }

    public void deleteGrub(int x) {
        army.remove(x);
        grubNum--;
    }

    public int getSize() {
        return grubNum;
    }

}
