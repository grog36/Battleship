import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.ArrayList;

public class Grid {
    //The desired grid size (usually 10)
    private final int GRID_SIZE = 10;

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
    private void setupShips() {
        //Sets up the possible directions
        String[] directions = {"Up", "Right", "Down", "Left"};

        //Setup the randomizer
        Random randomizer = new Random();

        //For each ship (Carrier = 0, Battleship = 1, Cruiser = 2, Submarine = 3, Destroyer = 4)
        for (int shipIndex = 0; shipIndex < 5; shipIndex++) {
            boolean isValidLocation = false; //Ensures the random spaces are valid to hold a ship
            
            while (!isValidLocation) {
                String direction = directions[randomizer.nextInt(directions.length)];
                int randomCoordinate = randomizer.nextInt(GRID_SIZE * GRID_SIZE);
                int rowIndex = (int) (randomCoordinate / GRID_SIZE);
                int columnIndex = randomCoordinate % GRID_SIZE;
    
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
        for (int rowIndex = 0; rowIndex < GRID_SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < GRID_SIZE; colIndex++) {
                switch (this.grid[rowIndex][colIndex]) {
                    case CARRIER_CHAR:
                        carrierLocations.add((rowIndex * GRID_SIZE) + colIndex);
                        break;
                    case BATTLESHIP_CHAR:
                        battleshipLocations.add((rowIndex * GRID_SIZE) + colIndex);
                        break;
                    case CRUISER_CHAR:
                        cruiserLocations.add((rowIndex * GRID_SIZE) + colIndex);
                        break;
                    case SUBMARINE_CHAR:
                        submarineLocations.add((rowIndex * GRID_SIZE) + colIndex);
                        break;
                    case DESTROYER_CHAR:
                        destroyerLocations.add((rowIndex * GRID_SIZE) + colIndex);
                        break;
                }
            }
        }
    }

    //Constructor to setup the grid using private methods
    public Grid() {
        this.grid = new char[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid[i][j] = WATER;
            }
        }
        setupShips();
        updateLocations();
    }

    //Attempts to shoot a specific coordinate (returns true if valid shot, false otherwise)
    public boolean shoot(int rowIndex, int columnIndex) {
        //If the desired coordinate is out of bounds, return false
        if (rowIndex < 0 || rowIndex >= GRID_SIZE || columnIndex < 0 || columnIndex >= GRID_SIZE) {
            return false;
        }
        
        //Checks to see if it is a valid shot and shoots if it is
        boolean wasValidShot = true;
        char characterAtCoordinate = this.grid[rowIndex][columnIndex];
        Integer coordinate = ((rowIndex * GRID_SIZE) + columnIndex);
        switch (characterAtCoordinate) {
            case CARRIER_CHAR:
                carrierLocations.remove(coordinate);
                this.grid[rowIndex][columnIndex] = HIT;
                System.out.println("You hit a ship.");
                if (carrierLocations.size() == 0) {
                    System.out.println("You have sunk the enemy's Carrier!");
                }
                break;
            case BATTLESHIP_CHAR:
                battleshipLocations.remove(coordinate);
                this.grid[rowIndex][columnIndex] = HIT;
                System.out.println("YOU HIT A SHIP");
                if (battleshipLocations.size() == 0) {
                    System.out.println("You have sunk the enemy's Battleship!");
                }
                break;
            case CRUISER_CHAR:
                cruiserLocations.remove(coordinate);
                this.grid[rowIndex][columnIndex] = HIT;
                System.out.println("You hit a ship.");
                if (cruiserLocations.size() == 0) {
                    System.out.println("You have sunk the enemy's Cruiser!");
                }
                break;
            case SUBMARINE_CHAR:
                submarineLocations.remove(coordinate);
                this.grid[rowIndex][columnIndex] = HIT;
                System.out.println("You hit a ship.");
                if (submarineLocations.size() == 0) {
                    System.out.println("You have sunk the enemy's Submarine!");
                }
                break;
            case DESTROYER_CHAR:
                destroyerLocations.remove(coordinate);
                this.grid[rowIndex][columnIndex] = HIT;
                System.out.println("You hit a ship.");
                if (destroyerLocations.size() == 0) {
                    System.out.println("You have sunk the enemy's Destroyer!");
                }
                break;
            case WATER:
                this.grid[rowIndex][columnIndex] = MISS;
                System.out.println("You missed.");
                break;
            default:
                System.out.println("Invalid Coordinate. Please shoot again.");
                wasValidShot = false;
                break;
        }
        return wasValidShot;
    }   

    //To string function (displays the grid)
    public String toString() {
        String output = "";
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                output += "[";
                output += this.grid[i][j];
                output += "]";
            }
            output += "\n";
        }
        return output;
    }
    
}