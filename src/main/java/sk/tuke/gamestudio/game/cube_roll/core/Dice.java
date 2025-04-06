package sk.tuke.gamestudio.game.cube_roll.core;

public class Dice {
    private int[] sides;
    private int topSide; // vrchna strana kocky
    private int markedSide ;

    public Dice() {
        sides = new int[]{1,2,4,3,5,6};// top, front, right, left, back, bottom
        topSide = 0;
        //markedSide = (int) (Math.random() * 6) + 1;
    }
    public boolean isMarkedSideBottom(){
        return sides[5] == markedSide;
    }

    public int getMarkedSide() {
        return markedSide;
    }
    public void setMarkedSide(int diceValue){this.markedSide = diceValue;}

    public void rollDice(char direction) {
        int temp;
        switch (direction) {
            case 'W': // Pohyb hore
                temp = sides[0];
                sides[0] = sides[4];   // Zadna strana ide na vrch
                sides[4] = sides[5];   // Spodna strana ide na zadnú
                sides[5] = sides[1];   // Predna strana ide na spodok
                sides[1] = temp;       // Vrchna strana ide na prednú
                break;
            case 'S': //Pohyb dole
                temp = sides[0];
                sides[0] = sides[1];   // Predna strana ide na vrc
                sides[1] = sides[5];   // Spodna strana ide na prednú
                sides[5] = sides[4];   // Zadna strana ide na spodok
                sides[4] = temp;       // Vrchna strana ide na zadnú
                break;
            case 'D': // Pohyb doprava
                temp = sides[0];
                sides[0] = sides[3];  // Ľava strana ide na vrch
                sides[3] = sides[5];  // Spodna strana ide na ľavú
                sides[5] = sides[2];  // Prava strana ide na spodok
                sides[2] = temp;      // Vrchna strana ide na pravú
                break;
            case 'A': // Pohyb dolava
                temp = sides[0];
                sides[0] = sides[2];  // Prava strana ide na vrch
                sides[2] = sides[5];  // Spodna strana ide na pravú
                sides[5] = sides[3];  // Ľava strana ide na spodok
                sides[3] = temp;      // Vrchna strana ide na ľavú
                break;
        }
    }
    public int getTopSide(){
        return sides[0];
    }
    public int getFrontSide(){
        return sides[1];
    }
    public int getRightSide(){
        return sides[2];
    }
    public int getLeftSide(){
        return sides[3];
    }
    public int getBackSide(){
        return sides[4];
    }
    public int getBottomSide(){
        return sides[5];
    }

}
