package sk.tuke.gamestudio.game.cube_roll.core;

public class FinishTile extends Tile{
    public FinishTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
