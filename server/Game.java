package server;

import java.util.ArrayList;

public class Game {
    //Array list for the games
    private ArrayList<Grid> grids;

    //Grid size
    private int gridSize;

    //Constructor (no args)
    public Game(int requestedGridSize) {
        this.gridSize = requestedGridSize;
        this.grids = new ArrayList<Grid>();
    }

    public void addPlayer(String playerName) {
        this.grids.add(new Grid(playerName, this.gridSize));
    }

    /**
     * Returns the grid associated with a player
     * @param playerName The name of the player you wish to access the grid of
     * @return Returns the grid associated with the player if the player exists, otherwise returns null
     */
    public Grid getGrid(String playerName) {
        for (Grid g : this.grids) {
            if (g.getPlayer() == playerName) {
                return g;
            }
        }
        return null;
    }
    
}
