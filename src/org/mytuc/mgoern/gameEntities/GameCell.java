package org.mytuc.mgoern.gameEntities;

import org.mytuc.mgoern.gameContainer.Point;

import java.util.ArrayList;

public class GameCell {
    private boolean status = false;
    private ArrayList<GameCell> neighboursAlive;

    private Point myCoordinates;
    private GameBoard myEnvironment;

    GameCell(int x, int y, GameBoard board){
        this.neighboursAlive = new ArrayList<>();
        this.myCoordinates = new Point(x,y);
        this.myEnvironment = board;
    }

    public boolean isAlive() {
        return this.status;
    }
    public void setAlive(boolean isAlive){ this.status = isAlive; }

    public ArrayList<GameCell> getNeighboursAlive() {
        return neighboursAlive;
    }

    public void addNeighbourAlive(GameCell neighbourAlive) {
        this.neighboursAlive.add( neighbourAlive );
    }

    public String getCoordinateHash() {
        return this.myCoordinates.toString();
    }

    public int getNeighbourCount(){
        return this.neighboursAlive.size();
    }
    
    public Point getCoordinates(){ return this.myCoordinates; }

    public ArrayList<Point> getPossibleNeighboursPointAddresses() {
        ArrayList<Point> tempStringArray = new ArrayList<>();

        for(int x = -1; x < 2; x++){
            for(int y = -1; y < 2; y++){
                if(x == 0 && y == 0)
                    continue;

                int xVar = this.myCoordinates.x + x;
                int yVar = this.myCoordinates.y + y;

                if(xVar < 0 && this.myEnvironment.isSphere())
                    xVar = this.myEnvironment.getBoardDimensions().x + xVar;
                else if(xVar >= this.myEnvironment.getBoardDimensions().x && this.myEnvironment.isSphere())
                    xVar = 0;
                else if(xVar < 0 || xVar >= this.myEnvironment.getBoardDimensions().x)
                    continue;

                if(yVar < 0 && this.myEnvironment.isSphere())
                    yVar = this.myEnvironment.getBoardDimensions().y + yVar;
                else if(yVar >= this.myEnvironment.getBoardDimensions().y && this.myEnvironment.isSphere())
                    yVar = 0;
                else if(yVar < 0 || yVar >= this.myEnvironment.getBoardDimensions().y)
                    continue;

                tempStringArray.add( new Point(xVar, yVar) );
            }
        }

        return tempStringArray;
    }
}
