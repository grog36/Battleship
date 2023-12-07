package server;

import java.lang.ArrayIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    //The desired grid size (usually 10)
    private int gridSize;

    //The player name associated with this grid
    private String playerName;

    //Sizes of the ships
    private final int CARRIER_SIZE = 5;
    private final int BATTLESHIP_SIZE = 4;
    private final int CRUISER_SIZE = 3;
    private final int SUBMARINE_SIZE = 3;
    private final int DESTROYER_SIZE = 2;
    private final int[] SHIP_SIZES = {CARRIER_SIZE, BATTLESHIP_SIZE, CRUISER_SIZE, SUBMARINE_SIZE, DESTROYER_SIZE};

    //Characters to be used by the board
    private final char CARRIER_CHAR = 'C';
    private final char BATTLESHIP_CHAR = 'B';
    private final char CRUISER_CHAR = 'R';
    private final char SUBMARINE_CHAR = 'S';
    private final char DESTROYER_CHAR = 'D';
    private final char[] SHIP_CHARS = {CARRIER_CHAR, BATTLESHIP_CHAR, CRUISER_CHAR, SUBMARINE_CHAR, DESTROYER_CHAR};
    private final char HIT = '@';
    private final char MISS = 'X';
    private final char WATER = ' ';
    
    //The grid of characters representing the current board state
    private char[][] grid;

    //Array lists of the current positions containing pieces to each ship
    private ArrayList<Integer> carrierLocations = new ArrayList<Integer>();
    private ArrayList<Integer> battleshipLocations = new ArrayList<Integer>();
    private ArrayList<Integer> cruiserLocations = new ArrayList<Integer>();
    private ArrayList<Integer> submarineLocations = new ArrayList<Integer>();
    private ArrayList<Integer> destroyerLocations = new ArrayList<Integer>();
    
    //Sets up the ships for the game
    private void setupShips(int shipCount) {
        //Sets up the possible directions
        String[] directions = {"Up", "Right", "Down", "Left"};

        //Setup the randomizer
        Random randomizer = new Random();

        //For each ship (Carrier = 0, Battleship = 1, Cruiser = 2, Submarine = 3, Destroyer = 4)
        for (int shipIndex = 0; shipIndex < shipCount; shipIndex++) {
            boolean isValidLocation = false; //Ensures the random spaces are valid to hold a ship
            
            while (!isValidLocation) {
                String direction = directions[randomizer.nextInt(directions.length)];
                int randomCoordinate = randomizer.nextInt(this.gridSize * this.gridSize);
                int rowIndex = (int) (randomCoordinate / this.gridSize);
                int columnIndex = randomCoordinate % this.gridSize;
    
                boolean squareNotBlank = false;
                switch (direction) {
                    case "Up":
                        for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                            try {
                                if (this.grid[rowIndex - offsetCount][columnIndex] != WATER) {
                                    squareNotBlank = true;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                squareNotBlank = true;
                            }
                        }
                        //If all of the squares are blank (have a spot for a ship)
                        if (!squareNotBlank) {
                            for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                                this.grid[rowIndex - offsetCount][columnIndex] = SHIP_CHARS[shipIndex];
                            }
                            isValidLocation = true;
                        }
                        break;
                    case "Right":
                        for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                            try {
                                if (this.grid[rowIndex][columnIndex + offsetCount] != WATER) {
                                    squareNotBlank = true;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                squareNotBlank = true;
                            }
                        }
                        //If all of the squares are blank (have a spot for a ship)
                        if (!squareNotBlank) {
                            for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                                this.grid[rowIndex][columnIndex + offsetCount] = SHIP_CHARS[shipIndex];
                            }
                            isValidLocation = true;
                        }
                        break;
                    case "Down":
                        for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                            try {
                                if (this.grid[rowIndex + offsetCount][columnIndex] != WATER) {
                                    squareNotBlank = true;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                squareNotBlank = true;
                            }
                        }
                        //If all of the squares are blank (have a spot for a ship)
                        if (!squareNotBlank) {
                            for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                                this.grid[rowIndex + offsetCount][columnIndex] = SHIP_CHARS[shipIndex];
                            }
                            isValidLocation = true;
                        }
                        break;
                    case "Left":
                        for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                            try {
                                if (this.grid[rowIndex][columnIndex - offsetCount] != WATER) {
                                    squareNotBlank = true;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                squareNotBlank = true;
                            }
                        }
                        //If all of the squares are blank (have a spot for a ship)
                        if (!squareNotBlank) {
                            for (int offsetCount = 0; offsetCount < SHIP_SIZES[shipIndex]; offsetCount++) {
                                this.grid[rowIndex][columnIndex - offsetCount] = SHIP_CHARS[shipIndex];
                            }
                            isValidLocation = true;
                        }
                        break;
                }
            }
        }
    }

    //Adds the ship locations to the corresponding array list
    private void updateLocations() {
        for (int rowIndex = 0; rowIndex < this.gridSize; rowIndex++) {
            for (int colIndex = 0; colIndex < this.gridSize; colIndex++) {
                switch (this.grid[rowIndex][colIndex]) {
                    case CARRIER_CHAR:
                        carrierLocations.add((rowIndex * this.gridSize) + colIndex);
                        break;
                    case BATTLESHIP_CHAR:
                        battleshipLocations.add((rowIndex * this.gridSize) + colIndex);
                        break;
                    case CRUISER_CHAR:
                        cruiserLocations.add((rowIndex * this.gridSize) + colIndex);
                        break;
                    case SUBMARINE_CHAR:
                        submarineLocations.add((rowIndex * this.gridSize) + colIndex);
                        break;
                    case DESTROYER_CHAR:
                        destroyerLocations.add((rowIndex * this.gridSize) + colIndex);
                        break;
                }
            }
        }
    }

    //Constructor to setup the grid using private methods
    public Grid(String playerName, int requestedGridSize) {
        this.gridSize = requestedGridSize;
        this.grid = new char[this.gridSize][this.gridSize];
        this.playerName = playerName;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                this.grid[i][j] = WATER;
            }
        }
        setupShips(5); //TODO (5 is default, needs to be able to be changed)
        updateLocations();
    }

    //Constructor for just player name
    public Grid(String playerName) {
        this(playerName, 10); //Default grid size is 10
    }

    //Helper method to hit a ship, used by shoot(int, int)
    private void hitShip(String shipName, ArrayList<Integer> shipList, Integer coordinate) {
        shipList.remove(coordinate);
        this.grid[((int) (coordinate / this.gridSize))][coordinate % this.gridSize] = HIT;
    }

    //Attempts to shoot a specific coordinate (returns true if valid shot, false otherwise)
    public boolean shoot(int rowIndex, int columnIndex) {
        //If the desired coordinate is out of bounds, return false
        if (rowIndex < 0 || rowIndex >= this.gridSize || columnIndex < 0 || columnIndex >= this.gridSize) {
            return false;
        }
        
        //Checks to see if it is a valid shot and shoots if it is
        boolean wasValidShot = true;
        char characterAtCoordinate = this.grid[rowIndex][columnIndex];
        Integer coordinate = ((rowIndex * this.gridSize) + columnIndex);
        switch (characterAtCoordinate) {
            case CARRIER_CHAR:
                hitShip("Carrier", carrierLocations, coordinate);
                break;
            case BATTLESHIP_CHAR:
                hitShip("Battleship", battleshipLocations, coordinate);
                break;
            case CRUISER_CHAR:
                hitShip("Cruiser", cruiserLocations, coordinate);
                break;
            case SUBMARINE_CHAR:
                hitShip("Submarine", submarineLocations, coordinate);
                break;
            case DESTROYER_CHAR:
                hitShip("Destroyer", destroyerLocations, coordinate);
                break;
            case WATER:
                this.grid[rowIndex][columnIndex] = MISS;
                break;
            default:
                wasValidShot = false;
                break;
        }
        return wasValidShot;
    }   

    //To string function (displays the grid) Very scuffed but works
    public String toString() {
        String output = " ";
        for (int i = 0; i < this.gridSize; i++) {
            output += "   ";
            output += i;
        }
        for (int j = 0; j < this.gridSize; j++) {
            output += "\n  ";
            for (int k = 0; k < this.gridSize; k++) {
                output += "+---";
            }
            output += "+\n";
            output += j;
            output += " ";
            for (int l = 0; l < this.gridSize; l++) {
                output += "| " + this.grid[j][l] + " ";
            }
            output += "|";
        }
        output += "\n  ";
        for (int m = 0; m < this.gridSize; m++) {
            output += "+---";
        }
        output += "+";
        return output;
    }

    public String getEnemyPov() {
        String output = " ";
        for (int i = 0; i < this.gridSize; i++) {
            output += "   ";
            output += i;
        }
        for (int j = 0; j < this.gridSize; j++) {
            output += "\n  ";
            for (int k = 0; k < this.gridSize; k++) {
                output += "+---";
            }
            output += "+\n";
            output += j;
            output += " ";
            for (int l = 0; l < this.gridSize; l++) {
                output += "| ";
                if (this.grid[j][l] == HIT || this.grid[j][l] == MISS) {
                    output += this.grid[j][l];
                }
                else {
                    output += WATER;
                }
                output += " ";
            }
            output += "|";
        }
        output += "\n  ";
        for (int m = 0; m < this.gridSize; m++) {
            output += "+---";
        }
        output += "+";
        return output;
    }
    
    //Check to see if player is dead (doesn't have any ships alive)
    public boolean isDead() {
        return (carrierLocations.size() == 0 && battleshipLocations.size() == 0 && cruiserLocations.size() == 0 && submarineLocations.size() == 0 && destroyerLocations.size() == 0);
    }

    //Get player name
    public String getPlayer() {
        return this.playerName;
    }
}