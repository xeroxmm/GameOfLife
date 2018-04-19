package org.mytuc.mgoern.gameEntities;

import org.mytuc.mgoern.gameContainer.Point;

import java.util.ArrayList;

public class GameCell {
    private boolean status = false;
    private ArrayList<GameCell> neighboursAlive;

    private Point myCoordinates;

    GameCell(int x, int y){
        this.neighboursAlive = new ArrayList<GameCell>();
        this.myCoordinates = new Point(x,y);
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

    public Point getCoordinates(){ return this.myCoordinates; }
}
