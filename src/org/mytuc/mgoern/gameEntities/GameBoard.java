package org.mytuc.mgoern.gameEntities;

import org.mytuc.mgoern.gameContainer.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameBoard {
    private int width;
    private int height;
    private boolean isSphere;

    private HashMap<String, GameCell> relevantCells;
    private int generationNumber = 0;


    public GameBoard(int width, int height, boolean isSphere){
        this.width = width;
        this.height = height;
        this.isSphere = isSphere;

        this.relevantCells = new HashMap<>();
    }

    public boolean isValidCellCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < this.width && y < this.height;
    }

    public boolean isCellBirthSuccessful(int x, int y){
        if(!this.isValidCellCoordinate( x, y))
            return false;

        GameCell temporaryCell = new GameCell(x,y, this);
        this.relevantCells.put( temporaryCell.getCoordinateHash(), temporaryCell);

        return true;
    }

    public void initByConfig(GameConfiguration config) {
        for(Point startCell : config.getStartCells()){
            this.isCellBirthSuccessful( startCell.x, startCell.y );
        }
    }

    public HashMap<String, GameCell> getCellsAlive() {
        return this.relevantCells;
    }

    public void doGameOfLife() {
        this.generationNumber++;
        this.clearConsole();

        HashMap<String, GameCell> newMap = new HashMap<>();
        ArrayList<GameCell> newCells = new ArrayList<>();

        for(Map.Entry<String, GameCell> entry : this.relevantCells.entrySet()){
            ArrayList<Point> addresses = entry.getValue().getPossibleNeighboursPointAddresses();
            int criticalNeighbourCount = 0;
            for(Point point : addresses){
                if(this.relevantCells.containsKey( point.toString() )){
                    criticalNeighbourCount++;
                    continue;
                } else if(newMap.containsKey( point.toString() )) {
                    continue;
                }
                GameCell tempCell = new GameCell(point.x, point.y, this);
                int neighbours = 0;
                for(Point neighboursAroundPossibleNewCell : tempCell.getPossibleNeighboursPointAddresses()){
                    if(this.relevantCells.containsKey( neighboursAroundPossibleNewCell.toString() ))
                        neighbours++;
                }
                if(neighbours == 3) {
                    newMap.put(point.toString(), tempCell);
                    newCells.add( tempCell );
                }
            }

            if(criticalNeighbourCount == 2 || criticalNeighbourCount == 3) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }

        this.relevantCells = newMap;
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

        }
    }

    public void drawBoard() {
        int[][] temporaryBoard = new int[this.height][this.width];
        int x, y;
        StringBuilder tempOutputString = new StringBuilder();

        for(Map.Entry<String, GameCell> entry : this.relevantCells.entrySet()){
            temporaryBoard[entry.getValue().getCoordinates().y][entry.getValue().getCoordinates().x] = 1;
        }

        tempOutputString.append('#');
        for(y = 0; y < temporaryBoard.length; y++)
            tempOutputString.append('-');
        tempOutputString.append('#').append("\n");

        for(y = 0; y < temporaryBoard.length; y++){
            tempOutputString.append('|');
            for(x = 0; x < temporaryBoard[y].length; x++){
                tempOutputString.append((temporaryBoard[y][x] == 0) ? ' ' : 'X');
            }
            tempOutputString.append('|').append("\n");
        }

        tempOutputString.append('#');
        for(y = 0; y < temporaryBoard.length; y++)
            tempOutputString.append('-');
        tempOutputString.append('#');
        System.out.println( tempOutputString.toString() );
    }

    public void printStatusMessage() {
        System.out.println( this.createStatusMessage() );
    }

    public void printStatusMessage(String message) {
        System.out.println( message );
    }

    private String createStatusMessage() {
        return "Generation: "+this.generationNumber+" Cells alive: "+this.relevantCells.size()+" - Plz press ENTER";
    }

    public boolean isSphere() {
        return this.isSphere;
    }

    public Point getBoardDimensions() {
        return new Point(this.width, this.height);
    }
}
