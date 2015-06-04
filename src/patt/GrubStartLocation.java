package patt;

public class GrubStartLocation {
	
		private int[] xLoc;
		private int[] yLoc;
		private int xChoice;
		private int yChoice;
		
		public GrubStartLocation(int sWidth, int sHeight, int gWidth, int gHeight)
		{
			xLoc = new int[3];
			yLoc = new int[3];
			
			xLoc[0] = 0;
			xLoc[1] = (sWidth / 2) - (gWidth / 2);
			xLoc[2] = sWidth - gWidth;
			
			yLoc[0] = 5;
			yLoc[1] = sHeight / 2;
			yLoc[2] = sHeight - gHeight;
		}
		
		public void chooseLoc()
		{
			int xRand = (int) (Math.random() * 3);
			xChoice = xLoc[xRand];
			
			if (xRand != 1)
				yChoice = yLoc[(int) (Math.random() * 3)];
			else
			{
				int finder = (int) (Math.random() * 2);
				if (finder == 1)
					finder++;
				yChoice = yLoc[finder];
			}
		}
		
		public int getX()
		{
			return xChoice;
		}
		
		public int getY()
		{
			return yChoice;
		}

}
