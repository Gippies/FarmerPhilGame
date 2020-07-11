package patt;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;

import brackeen.*;

public class WheatField {

    private ArrayList<Wheat> field = new ArrayList<Wheat>();
    private int wheatNum;

    public WheatField() {
        wheatNum = 0;
    }

    public WheatField(int n) {
        wheatNum = n;
    }

    public Wheat getWheat(int x) {
        return field.get(x);
    }

    public void addWheat(Wheat w) {
        field.add(w);
        wheatNum++;
    }

    public void setWheatLoc(int c, float x, float y) {
        field.get(c).setX(x);
        field.get(c).setY(y);
    }

    public boolean grubColWheat(Grub g, Wheat w) {
        return (g.getX() <= w.getX() + w.getWidth()
                && g.getX() + g.getWidth() >= w.getX()
                && g.getY() <= w.getY() + w.getHeight()
                && g.getY() + g.getHeight() >= w.getY());
    }

    public void deleteWheat() {
        wheatNum--;
    }

    public void deleteWheat(int x) {
        field.remove(x);
        wheatNum--;
    }

    public int getSize() {
        return wheatNum;
    }
}
