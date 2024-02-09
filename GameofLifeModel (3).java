/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gameoflife;

import java.util.Random;
import java.util.prefs.Preferences;

/**
 *
 * @author Samad
 */
public class GameofLifeModel {
    private int rows;
    private int cols;
    private int[][] life;
    private int[][] beforeLife;
    ControlsPanel panel;
    public GameofLifeModel(int rows, int cols,ControlsPanel panel) {
        this.rows = rows;
        this.cols = cols;
        this.life = new int[rows][cols];
        this.beforeLife = new int[rows][cols];
        this.panel = panel;
        initializeClear();
        Preferences prefs = Preferences.userNodeForPackage(PreferencesDialog.class);
        String selectedShape = prefs.get(PreferencesDialog.PREF_STARTING_PATTERN, "Clear");
        int speed = prefs.getInt(PreferencesDialog.PREF_SIMULATION_SPEED, 1);
        int zoomFactor = prefs.getInt(PreferencesDialog.PREF_ZOOM_FACTOR, 5); 
        
    }

    public void initializeGame() {
        
        // Randomly populate some cells
        Random random = new Random();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                beforeLife[x][y] = random.nextInt(5) == 1 ? 1 : 0;
                
            }
        }
        
        
    }
    public void initializeClear() {
        // Initialize an empty grid
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                beforeLife[x][y] = 0;
            }
        }
        copyArray();
    }
    public void initializeShape(String shape) {
        switch (shape) {
            case "Glider":
                initializeGlider();
                break;
            case "Beacon":
                initializeBeacon();
                break;
            case "Blinker":
                initializeBlinker();
                break;
            // Add cases for other shapes
            // ...
            case "Toad":
                initializeToad();
            case "Beehive":
                initializeBeehive();
            default:
                
                // Handle unknown shape or provide a default behavior
                break;
        }
    }
    private void initializeGlider() {
       
    // Glider shape
    int[][] glider = {
        {0, 1, 0},
        {0, 0, 1},
        {1, 1, 1}
    };
    setShapeAtCenter(glider);
}

private void initializeBeacon() {
    // Beacon shape
    int[][] beacon = {
        {1, 1, 0, 0},
        {1, 1, 0, 0},
        {0, 0, 1, 1},
        {0, 0, 1, 1}
    };
    setShapeAtCenter(beacon);
}
private void initializeBeehive() {
    // Beehive shape
    int[][] beehive = {
        {0, 1, 1, 0},
        {1, 0, 0, 1},
        {0, 1, 1, 0}
    };
    setShapeAtCenter(beehive);
}

private void initializeBlinker() {
    // Blinker shape (Oscillator)
    int[][] blinker = {
        {0, 1, 0},
        {0, 1, 0},
        {0, 1, 0}
    };
    setShapeAtCenter(blinker);
}

private void initializeToad() {
    // Toad shape
    int[][] toad = {
        {0, 0, 0, 0},
        {0, 1, 1, 1},
        {1, 1, 1, 0},
        {0, 0, 0, 0}
    };
    setShapeAtCenter(toad);
}

private void setShapeAtCenter(int[][] shape) {

    int startX = (rows - shape.length) / 5;
    int startY = (cols - shape[0].length) / 5;

    setShapeAt(startX, startY, shape);
}
private void setShapeAt(int startX, int startY, int[][] shape) {
    for (int x = 0; x < shape.length; x++) {
        for (int y = 0; y < shape[x].length; y++) {
            beforeLife[startX + x][startY + y] = shape[x][y];
        }
    }
}

    public void setLife(int[][] newLife) {
        if (newLife.length == life.length && newLife[0].length == life[0].length) {
            for (int i = 0; i < life.length; i++) {
                System.arraycopy(newLife[i], 0, life[i], 0, life[i].length);
            }
        } else {
            // Handle error or throw an exception if the dimensions don't match
            throw new IllegalArgumentException("Invalid dimensions for setLife");
        }
    }
    public int[][] getLife() {
        return life;
    }
    public void updateGrid() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int aliveNeighbors = countAliveNeighbors(x, y);
                if (life[x][y] == 1) {
                    // Live cell rules
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        beforeLife[x][y] = 0; // Starvation or overpopulation
                    }
                } else {
                    // Dead cell rules
                    if (aliveNeighbors == 3) {
                        beforeLife[x][y] = 1; // Reproduction
                    }
                }
            }
        }

        // Copy the updated grid to the current state
        copyArray();
    }

    private int countAliveNeighbors(int x, int y) {
        int alive = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = (x + i + rows) % rows;
                int neighborY = (y + j + cols) % cols;
                alive += life[neighborX][neighborY];
            }
        }
        alive -= life[x][y]; // Subtract the center cell
        return alive;
    }
    public void setCell(int x, int y, int value) {
        if (x >= 0 && x < rows && y >= 0 && y < cols) {
            beforeLife[x][y] = value;
        }
    }
    public void copyArray() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                life[x][y] = beforeLife[x][y];
            }
        }
    }

    public void setPanel(ControlsPanel panel){
        this.panel = panel;
    }

    
}

