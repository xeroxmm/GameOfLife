package org.mytuc.mgoern;

import org.mytuc.mgoern.gameEntities.GameBoard;
import org.mytuc.mgoern.gameEntities.GameConfiguration;
import org.mytuc.mgoern.tools.GameObserver;

public class Main {
    public static void main(String[] args) {
        int boardSize = 20;

        GameConfiguration config = new GameConfiguration("resources/config.cfg");
        GameBoard board = new GameBoard(boardSize, boardSize, true);
        GameObserver observer = new GameObserver( board );

        board.initByConfig( config );

        do {
            board.drawBoard();
            board.printStatusMessage();
            observer.waitForKeypress();

            board.doGameOfLife();
        } while( observer.hasNextGeneration() );

        if(observer.hasError())
            board.printStatusMessage( observer.getErrorMessage() );
    }
}
