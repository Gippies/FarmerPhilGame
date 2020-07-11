package patt;

import java.util.ArrayList;

public class LocationCapture {

    private static ArrayList<Float> xLoc;
    private static ArrayList<Float> yLoc;
    private static int size;

    public static void init() {
        xLoc = new ArrayList<Float>();
        yLoc = new ArrayList<Float>();
        size = 0;
    }

    public static void addLocation(float x, float y) {
        xLoc.add(x);
        yLoc.add(y);
        size++;
    }

    public static void resetLocations() {
        xLoc = new ArrayList<Float>();
        yLoc = new ArrayList<Float>();
        size = 0;
    }

    public static void deleteLastLoc() {
        xLoc.remove(xLoc.size() - 1);
        yLoc.remove(yLoc.size() - 1);
        size--;
    }

    public static float getLocX(int k) {
        return xLoc.get(k);
    }

    public static float getLocY(int k) {
        return yLoc.get(k);
    }

    public static int getSize() {
        return size;
    }

}
