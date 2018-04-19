package org.mytuc.mgoern.gameEntities;

import com.github.xeroxmm.gameTools.GameConfig;

import java.util.HashMap;

public class GameBoard {
    private int width;
    private int height;
    private boolean isSpheric;

    private HashMap<String, GameCell> relevantCells;


    public GameBoard(int width, int height, boolean isSphere){
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

    public void initByConfig(GameConfig config) {
        for(GameCell startCell : config.getCells()){
            this.isCellBirthSuccessful( startCell.getCoordinates().x, startCell.getCoordinates().y );
        }
    }

    public HashMap<String, GameCell> getCellsAlive() {
        return this.relevantCells;
    }

    public void doGameOfLife() {
        this.clearConsole();
        // TODO implement Game Logic
    }

    /**
     * Found at <a href="https://stackoverflow.com/questions/2979383/java-clear-the-console">Source Stackoverflow</a>
     */
    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            //  Handle any exceptions.
            //  But no need for...
        }
    }

    public void drawBoard() {
        // TODO implement Drawing Logic
    }

    public void printStatusMessage() {
        System.out.println( this.createStatusMessage() );
    }

    public void printStatusMessage(String message) {
        System.out.println( message );
    }

    private String createStatusMessage() {
        return "Default Status Message.. Input: ";
    }
}
