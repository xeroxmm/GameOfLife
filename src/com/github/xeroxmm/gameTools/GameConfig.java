package com.github.xeroxmm.gameTools;

import com.github.xeroxmm.gameEntities.GameCell;

import java.util.ArrayList;

public class GameConfig {
    private String filePath;
    private ArrayList<GameCell> cells;

    public GameConfig(String pathToConfigFile) {
        this.filePath = pathToConfigFile;
        this.cells = new ArrayList<>();
    }

    public ArrayList<GameCell> getCells() {
        return cells;
    }
}
