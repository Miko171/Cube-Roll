package sk.tuke.kpi.kp.cube_roll.core;

public abstract class Tile {
    private  int row;
    private  int col;


    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public abstract boolean isWalkable();
}
