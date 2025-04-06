package sk.tuke.gamestudio.game.cube_roll.core;

public class BasicTile extends Tile{

    public BasicTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
