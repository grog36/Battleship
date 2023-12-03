import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;

public class Grid {
    private final int GRID_SIZE = 10;

    private final int CARRIER_SIZE = 5;
    private final int BATTLESHIP_SIZE = 4;
    private final int CRUISER_SIZE = 3;
    private final int SUBMARINE_SIZE = 3;
    private final int DESTROYER_SIZE = 2;
    private final int[] SHIP_SIZES = {CARRIER_SIZE, BATTLESHIP_SIZE, CRUISER_SIZE, SUBMARINE_SIZE, DESTROYER_SIZE};

    private final char CARRIER_CHAR = 'C';
    private final char BATTLESHIP_CHAR = 'B';
    private final char CRUISER_CHAR = 'R';
    private final char SUBMARINE_CHAR = 'S';
    private final char DESTROYER_CHAR = 'D';
    private final char[] SHIP_CHARS = {CARRIER_CHAR, BATTLESHIP_CHAR, CRUISER_CHAR, SUBMARINE_CHAR, DESTROYER_CHAR};

    private final char HIT = '@';
    private final char MISS = 'X';
    private final char WATER = ' ';

    private char[][] grid;


    private void setupShips() {
        String[] directions = {"Up", "Right", "Down", "Left"};


        //Setup the randomizer
        Random randomizer = new Random();

        for (int shipIndex = 0; shipIndex < 5; shipIndex++) {
            boolean isValidLocation = false;
    
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
                }
            }
        }
    }

    public Grid() {
        this.grid = new char[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid[i][j] = WATER;
            }
        }
        setupShips();
    }


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