package patt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MyLabel {
	
	private int xLoc;
	private int yLoc;
	private String text;
	
	public MyLabel(int x, int y, String t)
	{
		xLoc = x;
		yLoc = y;
		text = t;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString(text, xLoc, yLoc);
	}
	
	public void setText(String s)
	{
		text = s;
	}

}
