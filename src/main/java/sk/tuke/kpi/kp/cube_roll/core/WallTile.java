package sk.tuke.kpi.kp.cube_roll.core;

public class WallTile extends Tile{
    public WallTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }
}
