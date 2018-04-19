package org.mytuc.mgoern.tools;

import org.mytuc.mgoern.gameEntities.GameBoard;

import java.util.Scanner;

public class GameObserver {
    private char iterationQuitCharacter = 'q';
    private Character useIterationByKeypress = null;
    private int useIterationByTime = -1;
    private boolean useIterationOnKeypressAll = false;
    Scanner scanner;

    private GameBoard gameBoard;

    private String errorMsg = "";

    public GameObserver( GameBoard board ){
        this.scanner = new Scanner(System.in);
        this.gameBoard = board;
        resetAllConfigs();
        setUseIterationByKeypressAll();
    }

    public boolean hasNextGeneration() {
        if(gameBoard.getCellsAlive().isEmpty()){
            this.errorMsg = "No living cell left";
            return false;
        }

        if(useIterationByTime > 0){
            return this.doIterationByTime();
        } else if(this.useIterationOnKeypressAll || (this.useIterationByKeypress != null)){
            return this.doIterationByKeypress();
        } else {
            this.errorMsg = "Unsupported Iteration Mode";
            return false;
        }
    }

    private boolean doIterationByKeypress() {
        do{
            try {
                Thread.sleep( 50 );
            } catch (InterruptedException e) {
                this.errorMsg = "Error while sleeping: " + e.getMessage();
                return false;
            }
            if(this.doIterationBreakByStopKey()) {
                this.errorMsg = "Break through keypress (" + this.iterationQuitCharacter + ")";
                return false;
            } else if(this.doIterationContinueByKeySpecific())
                return true;
        } while (true);
    }

    private boolean doIterationContinueByKeySpecific() {
        String scannerStr = this.scanner.toString();
        boolean status = (
            (this.useIterationOnKeypressAll && !scannerStr.isEmpty()) ||
            scannerStr.contains( this.useIterationByKeypress + "" )
        );

        this.scanner.reset();

        return status;
    }

    private boolean doIterationByTime() {
        long timeStart = System.currentTimeMillis();
        long timeElapsed = 0L;

        this.scanner.reset();
        while(timeElapsed < 1000 * useIterationByTime){
            try {
                Thread.sleep( 50 );
            } catch(InterruptedException e){
                this.errorMsg = "Error while sleeping: " + e.getMessage();
                return false;
            }
            timeElapsed = System.currentTimeMillis() - timeStart;
            if(this.doIterationBreakByStopKey()){
                this.errorMsg = "Break through keypress (" + this.iterationQuitCharacter + ")";
                return false;
            }
        }
        return true;
    }

    private boolean doIterationBreakByStopKey() {
        boolean status =  this.scanner.toString().contains( this.iterationQuitCharacter + "" );

        this.scanner.reset();

        return status;
    }

    public String getErrorMessage(){ return this.errorMsg; }
    public boolean hasError(){ return !this.errorMsg.isEmpty(); }

    private void resetAllConfigs(){
        this.useIterationByKeypress = null;
        this.useIterationByTime = -1;
    }
    public void setUseIterationByKeypressAll(){
        resetAllConfigs();
        this.useIterationOnKeypressAll = true;
    }
    public void setUseIterationByKeypress(char keyChar){
        resetAllConfigs();
        this.useIterationByKeypress = keyChar;
    }
    public void setIterationTimer(int time){
        resetAllConfigs();
        this.useIterationByTime = time;
    }
}
