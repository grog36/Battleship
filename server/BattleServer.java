package server;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class BattleServer implements MessageListener {
    //Server socket for the game
    private ServerSocket serverSocket;
    //Current??? (TODO): WHAT IS THIS
    private int current;
    //Is game in session?
    private boolean gameInSession = false;
    //The game
    private Game game;
    //ArrayList containing the connection agents
    private ArrayList<ConnectionAgent> agents;
    //ArrayList containing the names of all of the players of the current game
    private ArrayList<String> playerNames;
    //ArrayList containing the alive players of the current game
    private ArrayList<String> alivePlayers;

    //Constructor
    public BattleServer(int port, int requestedGridSize) {
        //Create the server using socket stuff (TODO)
        try {
            this.serverSocket = new ServerSocket(port);
            this.game = new Game(requestedGridSize);
            this.agents = new ArrayList<ConnectionAgent>();
            this.playerNames = new ArrayList<String>();
            //While loop will break when everyone has left the game or after the 15s auto-timeout when server is started
            while (true) {
                //If there are no users connected, the server will auto-timeout in 15s
                if (this.agents.size() == 0) {
                    this.serverSocket.setSoTimeout(15000);
                }
                else {
                    this.serverSocket.setSoTimeout(0);
                }
                Socket server = serverSocket.accept();
                ConnectionAgent ca = new ConnectionAgent(server);
                ca.addMessageListener(this);
                this.agents.add(ca);
            }
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
            this.closeAllConnections();
            System.exit(1);
        }
        catch (IllegalArgumentException e) {
            System.out.println("The port number is invalid. Please try again");
            this.closeAllConnections();
            System.exit(2);
        }
        catch (SecurityException e) {
            System.out.println("A security manager exists and is currently not allowing this operation.");
            this.closeAllConnections();
            System.exit(3);
        }
    }

    //No idea what this method is supposed to do yet (TODO)
    public void listen() {
        System.out.println("Listen.");
    }

    /**
     * Sends a message to all connected users
     * 
     * @param message The message to be sent to all users
     */
    public void broadcast(String message) {
        for (ConnectionAgent ca : this.agents) {
            ca.sendMessage(message);
        }
    }

    //Closes all connected agents
    private void closeAllConnections() {
        this.broadcast("Goodbye.");
        for (ConnectionAgent ca : this.agents) {
            ca.close();
        }
        try {
            this.serverSocket.close();
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
        }
    }

    //Starts the game
    private void startGame() {
        this.gameInSession = true;
        this.broadcast("The game begins");
        this.alivePlayers = new ArrayList<String>(this.playerNames);
        this.current = 0;
        this.broadcast(this.playerNames.get(this.current) + " it is your turn");
    }

    //Checks to see if there is a winner
    private void checkForWin() {
        if (this.alivePlayers.size() == 0) {
            this.gameInSession = false;
            this.broadcast("GAME OVER: " + this.alivePlayers.get(0) + " wins!");
        }
    }

    /**
     * Method to attempt to shoot at a player
     * 
     * @param playerName The player the user wishes to shoot at
     * @param rowIndex The row in which the player wishes to shoot at
     * @param colIndex The column in which the player wishes to shoot at
     * @param source The MessageSource of the user who wishes to shoot
     */
    private void fireAtPlayer(String playerName, int rowIndex, int colIndex, MessageSource source) {
        if (this.agents.get(this.current).equals(source)) {
            if (this.alivePlayers.contains(playerName)) {
                if (this.game.getGrid(playerName).shoot(rowIndex, colIndex)) {
                    this.broadcast("Shots fired at " + playerName + " by " + this.playerNames.get(current));
                    this.checkForLife(playerName);
                    //Change whose turn it is
                    int currentIndex = this.alivePlayers.indexOf(this.playerNames.get(this.current));
                    currentIndex++;
                    if (currentIndex == this.alivePlayers.size()) {
                        currentIndex = 0;
                    }
                    this.current = this.playerNames.indexOf(this.alivePlayers.get(currentIndex));
                    this.broadcast(this.playerNames.get(this.current) + " it is your turn");
                    this.checkForWin();
                }
                else {
                    this.sendMessageToSource("Invalid coordinates. Please try again.", source);
                }
            }
        }
        else {
            this.sendMessageToSource("It is not your turn", source);
        }
    }

    /**
     * Checks to see if a player is still alive and removes them from the arraylist if not
     * 
     * @param playerName The player you wish to check
     */
    private void checkForLife(String playerName) {
        if (this.game.getGrid(playerName).isDead()) {
            this.alivePlayers.remove(playerName);
        }
    }

    /**
     * Send a message to the desired message source
     * 
     * @param message The message to be sent
     * @param source The MessageSource to send the message to
     */
    private void sendMessageToSource(String message, MessageSource source) {
        for (ConnectionAgent ca : this.agents) {
            if (ca.equals(source)) {
                ca.sendMessage(message);
            }
        }
    }

    /** TODO
     * Used to notify observers that the subject has receieved a message.
     * 
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source) {
        if (message.startsWith("Username Creation: ")) {
            String username = message.substring(19, (message.length()));
            if (this.gameInSession) {
                this.sendMessageToSource("Game is already in progress. Please try again later.", source);
            }
            else {
                this.game.addPlayer(username);
                this.playerNames.add(username);
                System.out.println("!!! " + username + " has connected");
            }
        }
        else if (message.contains("/map")) {
            try {
                String player = message.substring(5, message.length());
                for (int i = 0; i < this.agents.size(); i++) {
                    if (this.agents.get(i).equals(source)) {
                        if (player.equals(this.playerNames.get(i))) {
                            this.sendMessageToSource(this.game.getGrid(player).toString(), source);
                        }
                        else {
                            this.sendMessageToSource(this.game.getGrid(player).getEnemyPov(), source);
                        }
                    }
                }
            }
            catch (StringIndexOutOfBoundsException e) {
                this.sendMessageToSource("Improper '/map' usage. Correct Usage: /map <playerName>", source);
            }
        }
        else if (message.equals("/start")) {
            if (this.playerNames.size() > 0) {
                this.startGame();
            }
            else {
                this.broadcast("Not enough players to start the game");
            }
        }
        else if (message.startsWith("/fire")) {
            if (this.gameInSession) {
                String[] brokenMessage = message.split(" ");
                try {
                    String playerToShoot = brokenMessage[1];
                    int rowIndex = Integer.parseInt(brokenMessage[2]);
                    int colIndex = Integer.parseInt(brokenMessage[3]);
                    this.fireAtPlayer(playerToShoot, rowIndex, colIndex, source);
                }
                catch (NumberFormatException e) {
                    this.sendMessageToSource("Invalid coordinate. Please try again.", source);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    this.sendMessageToSource("Improper '/fire' usage. Correct Usage: /fire <targetName> <rowIndex> <columnIndex>", source);
                }
            }
            else {
                this.sendMessageToSource("Game not in progress", source);
            }
        }
        else if (message.equals("/end")) {
            int indexToRemove = -1;
            for (int i = 0; i < this.agents.size(); i++) {
                if (this.agents.get(i).equals(source)) {
                    indexToRemove = i;
                }
            }
            this.broadcast("!!! " + this.playerNames.get(indexToRemove) + " surrendered");
            this.agents.remove(indexToRemove);
            this.playerNames.remove(indexToRemove);
            if (this.agents.size() == 0) {
                System.out.println("All players have left. Server will be shutdown.");
                this.closeAllConnections();
                System.exit(5);
            }
        }
        else {
            System.out.println(message);
        }
    }

    /**
     * Used to notify observers that the subject will not receive new messages; observers can
     * deregister themselves.
     * 
     * @param source The <code>MessageSource</code> that does not expect more messages.
     */
    public void sourceClosed(MessageSource source) {
        source.removeMessageListener(this);
    }
}
