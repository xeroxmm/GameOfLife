package org.mytuc.mgoern.gameEntities;

import java.util.HashMap;

public class GameBoard {
    private int width;
    private int height;
    private boolean isSpheric;

    private HashMap<String, GameCell> relevantCells;


    GameBoard(int width, int height, boolean isSphere ){
        this.width = width;
        this.height = height;
        this.isSpheric = isSphere;

        this.relevantCells = new HashMap<String, GameCell>();
    }

    public boolean isValidCellCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < this.width && y < this.height;
    }

    public boolean isCellBirthSuccessful(int x, int y){
        if(!this.isValidCellCoordinate( x, y))
            return false;

        GameCell temporaryCell = new GameCell(x,y);
        this.relevantCells.put( temporaryCell.getCoordinateHash(), temporaryCell);

        return true;
    }
}
