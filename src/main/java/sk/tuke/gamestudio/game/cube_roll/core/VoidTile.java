package sk.tuke.gamestudio.game.cube_roll.core;

public class VoidTile extends Tile{
    public VoidTile(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
