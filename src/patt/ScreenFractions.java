package patt;

public class ScreenFractions {
    public static int twentyNineThirtyY,
            fourteenFifteenY,
            threeTwentyX,
            nineTenY,
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

    public static void init(int screenWidth, int screenHeight) {
        twoFifthX = screenWidth * 2 / 5;
        fiveSixtY = screenHeight * 5 / 16;
        threeFifthX = screenWidth * 3 / 5;
        elevenSixtY = screenHeight * 11 / 16;
        oneTwentyX = screenWidth / 20;
        oneTwentyY = screenHeight / 20;
        nineteenTwentyX = screenWidth * 19 / 20;
        nineteenTwentyY = screenHeight * 19 / 20;
        twentyNineThirtyY = screenHeight * 29 / 30;
        fourteenFifteenY = screenHeight * 14 / 15;
        threeTwentyX = screenWidth * 3 / 20;
        oneFourthRight = screenWidth / 4;
        oneFourthDown = screenHeight / 4;
        oneFourthLeft = screenWidth * 3 / 4;
        oneFourthUp = screenHeight * 3 / 4;
        threeFortyX = screenWidth * 3 / 40;
        nineTenY = screenHeight * 9 / 10;
    }
}
