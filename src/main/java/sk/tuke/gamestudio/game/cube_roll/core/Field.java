package sk.tuke.gamestudio.game.cube_roll.core;

public class Field {

    private static final String LIGHTGREEN ="\033[38;5;10m";
    private static final String RESET = "\u001B[0m";

    private Tile[][] field;
    private int rowCount;
    private int columnCount;

    public Dice dice;
    private int diceRow;
    private int diceCol;

    private int finishR;
    private int finishC;

    private char lastDirection;

    private int livesCount;
    private int maxLives;


    public Field(int rows, int cols, int startRow, int startCol, int finishRow, int finishCol) {
        this.rowCount = rows;
        this.columnCount = cols;
        this.field = new Tile[rows][cols];

        this.dice = new Dice();
        this.diceRow = startRow;
        this.diceCol = startCol;

        this.finishR = finishRow;
        this.finishC = finishCol;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(i == 0 || j == 0 || i == rowCount-1 || j == columnCount-1){
                    setTile(i,j, new WallTile(i,j));
                    //field[i][j] = new WallTile(i,j);
                    continue;
                }
                if(i == finishRow && j == finishCol){
                    setTile(i,j, new FinishTile(finishRow, finishCol));
                    //field[i][j] = new FinishTile(finishRow, finishCol);
                    continue;
                }
                setTile(i,j, new BasicTile(i,j));
                //field[i][j] = new BasicTile(i,j);
            }
        }

        //field[2][2] = new VoidTile(2,2);
        //setFinish(finishRow, finishCol);
        //FinishTile finishTile = new FinishTile(finishRow, finishCol);


    }
    // field gettery
    public int getRowCount() {
        return rowCount;
    }
    public int getColumnCount() {
        return columnCount;
    }
    // dice gettery
    public int getDiceCol() {
        return diceCol;
    }
    public int getDiceRow() {return diceRow;}
    // FinishTile gettery
    public int getFinishR() {return finishR;}
    public int getFinishC() {return finishC;}

    public boolean isFinishTile(){  // ci je pod kockou dlazdica Finish
        return field[diceRow][diceCol] instanceof FinishTile;
    }

    public boolean isVoidTile(int row, int col){
        return field[row][col] instanceof VoidTile;
    }
    public boolean isWallTile(int row,int col){
        return field[row][col] instanceof WallTile;
    }
    public void setTile(int row, int col, Tile tile) { field[row][col] = tile;}

    public int getLivesCount() { return livesCount;}
    public void setLivesCount(int livesCount) { this.livesCount = livesCount;}
    public int getMaxLives() { return maxLives;}
    public void setMaxLives(int maxLives) { this.maxLives = maxLives;}
    // znizovanie zivotov vypis
    public void loseLife(){
        livesCount--;
        if (livesCount < 5 && livesCount > 1 ){
            System.out.println(LIGHTGREEN + "--Pozor stratil si život ostávajú ti len " + livesCount + " životy" + RESET);;
        }
        else if (livesCount > 5 ) {
            System.out.println(LIGHTGREEN + "--Pozor stratil si život ostáva ti len " + livesCount + " životov" + RESET);
        }
        else if (livesCount == 1){
            System.out.println(LIGHTGREEN + "--Pozor stratil si život ostáva ti len " + livesCount + " život" + RESET);
        }

    }

    public void loseLifeHtml(){
        livesCount--;
    }
    // Pohyb kocky po fielde
    public void moveDice(char direction){
        lastDirection = direction;
        int newRow = diceRow;
        int newCol = diceCol;

        switch (direction){
            case 'W': // hore
                newRow--;
                break;

            case 'S': // dole
                newRow++;
                break;

            case 'A': // dolava
                newCol--;
                break;

            case 'D': // doprava
                newCol++;
                break;

        }
        if(field[newRow][newCol].isWalkable()){
        diceRow = newRow;
        diceCol = newCol;
        dice.rollDice(direction);
        }
        else {
            System.out.println(LIGHTGREEN + "-- Zlý smer pohybu --!" + RESET);
        }

    }

    public char getLastDirection() {
        char direction = lastDirection;
        switch (direction) { // Obratime smer lebo sa vraciame
            case 'W':
                return 'S';
            case 'S':
                return 'W';
            case 'D':
                return 'A';
            case 'A':
                return 'D';
            default:
                return 'X';
        }

    }
}
