package org.mytuc.mgoern.gameEntities;

import org.mytuc.mgoern.gameContainer.Point;
import org.mytuc.mgoern.tools.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameConfiguration {
    private String pathToFile;
    private int gameTemplateToLoad = 0;
    private int[][] gameTemplateStar = new int[][]{{0,1},{1,0},{1,1},{1,2},{2,1}};
    private int[][] gameTemplateGiven;

    private ArrayList<Point> startCells = new ArrayList<>();

    public GameConfiguration(String pathToFile){
        this.pathToFile = pathToFile;
        try {
            this.parseFile();
        } catch (FileNotFoundException e){
            Console.log( "File not found ("+this.pathToFile+"), loading defaults" );
        } catch(ArrayIndexOutOfBoundsException e){
            Console.log("Array Size mismatch. CFG with more than one single Number?");
        } finally {
            this.loadConfig();
        }
    }

    private void parseFile() throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        int arraySize = 0;
        int next = 0;
        boolean isCommentArea = false;

        Scanner scanner = new Scanner( new File( this.pathToFile ));
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            line = line.trim();
            String[] parts = line.split("\\s+");
            if(line.charAt(0) == '/' && line.charAt(1) == '*') {
                isCommentArea = true;
                continue;
            }else if(line.charAt(0) == '*' && line.charAt(1) == '/'){
                isCommentArea = false;
                continue;
            } else if(isCommentArea || line.charAt(0) == '#'){
                continue;
            }
            if(parts.length == 1 && !parts[0].isEmpty()){
                if(Character.isDigit(parts[0].charAt( 0 ))){
                    arraySize = Character.getNumericValue( parts[0].charAt( 0 ));
                }
            } else if(parts.length == 2 && !parts[0].isEmpty() && !parts[1].isEmpty()){
                if(this.gameTemplateGiven == null){
                    this.gameTemplateGiven = new int[arraySize][2];
                } else if(this.gameTemplateGiven.length != arraySize)
                    throw new ArrayIndexOutOfBoundsException();

                this.gameTemplateGiven[ next ] = new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                next++;
            }
        }
        scanner.close();

        if(this.gameTemplateGiven != null && this.gameTemplateGiven.length > 0)
            this.gameTemplateToLoad = 99;
    }

    private void loadConfig(){
        switch( this.gameTemplateToLoad ){
            case 99:
                this.loadGameTemplate( this.gameTemplateGiven );
                break;
            case 0:
            default:
                this.loadGameTemplate( this.gameTemplateStar );
        }
    }

    private void loadGameTemplate(int[][] gameTemplate) {
        for (int[] aSimplePoint : gameTemplate) {
            Point c = new Point(aSimplePoint[0], aSimplePoint[1]);
            this.startCells.add(c);
        }
    }

    public ArrayList<Point> getStartCells(){
        return this.startCells;
    }
}
