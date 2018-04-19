package org.mytuc.mgoern;

import org.mytuc.mgoern.gameEntities.GameBoard;
import org.mytuc.mgoern.gameEntities.GameConfiguration;
import org.mytuc.mgoern.tools.GameObserver;

public class Main {
    public static void main(String[] args) {
        int boardSize = 30;

        GameConfiguration config = new GameConfiguration("config.cfg");
        GameBoard board = new GameBoard(boardSize, boardSize, false);
        GameObserver observer = new GameObserver( board );

        board.initByConfig( config );

        do {
            board.doGameOfLife();
            board.drawBoard();
            board.printStatusMessage();
        } while( observer.hasNextGeneration() );

        if(observer.hasError())
            board.printStatusMessage( observer.getErrorMessage() );
    }
}
