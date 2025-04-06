package sk.tuke.gamestudio.game.cube_roll.core;

public class MapFactory {
    public static Field createTrainMap() {
        Field field = new Field(5, 5, 3,1,2,3);
        field.setTile(1,1, new VoidTile(1,1));
        field.setMaxLives(100);
        field.setLivesCount(100);
        field.dice.setMarkedSide(1);
        return field;
    }

    public static Field createEasyMap() {
        Field field = new Field(6, 6, 3,2,2,3);
        field.setLivesCount(4);
        field.setMaxLives(4);
        field.dice.setMarkedSide(1);
        // Void dlazdice
        field.setTile(1,1, new VoidTile(1,1));
        field.setTile(4,4, new VoidTile(4,4));

        return field;
    }

    public static Field createMediumMap() {
        Field field = new Field(8, 8, 1, 6, 5, 5);
        field.setLivesCount(2);
        field.setMaxLives(2);
        field.dice.setMarkedSide(3);

        // Void dlazdice
        field.setTile(1,1, new VoidTile(1,1));
        field.setTile(2,1, new VoidTile(2,1));
        field.setTile(1,2, new VoidTile(1,2));
        field.setTile(2,6, new VoidTile(2,6));
        field.setTile(6,5, new VoidTile(7,5));
        field.setTile(6,6, new VoidTile(6,6));
        field.setTile(5,6, new VoidTile(5,6));

        // Wall dlazdice
        field.setTile(1,3, new WallTile(1,3));
        field.setTile(3,3, new WallTile(3,3));
        field.setTile(4,4, new WallTile(4,4));
        field.setTile(2,5, new WallTile(2,5));
        field.setTile(3,6, new WallTile(1,3));

        return field;
    }

    public static Field createHardMap() {
        Field field = new Field(6, 11, 3,1, 1,4);
        field.setLivesCount(1);
        field.setMaxLives(1);
        field.dice.setMarkedSide(5);

        for(int i = 0; i < field.getRowCount(); i++){
            for(int j = 0; j < field.getColumnCount(); j++){
                if(i == 0 || j == 0 || i == field.getRowCount()-1 || j == field.getColumnCount()-1){
                    field.setTile(i,j, new VoidTile(i,j));
                }
                if(i >= 3 && j > 1 && j<6){
                    field.setTile(i,j, new VoidTile(i,j));
                }
                if(i >= 3 && j>7){
                    field.setTile(i,j, new VoidTile(i,j));
                }

            }
        }
        return field;
    }
}
