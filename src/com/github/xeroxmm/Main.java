package com.github.xeroxmm;

import com.github.xeroxmm.gameEntities.GameBoard;
import com.github.xeroxmm.gameTools.GameConfig;
import com.github.xeroxmm.gameTools.GameObserver;

public class Main {
    public static void main(String[] args) {
        int boardSize = 30;

        GameConfig config = new GameConfig("config.cfg");
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
