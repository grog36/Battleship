package server;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BattleServer implements MessageListener {
    //Server socket for the game
    private ServerSocket serverSocket;
    //Current??? (TODO): WHAT IS THIS
    private int current;
    //The game
    private Game game;
    //ArrayList containing the connection agents
    private ArrayList<ConnectionAgent> agents;
    //ArrayList containing the names of the players
    private ArrayList<String> playerNames;

    //Constructor
    public BattleServer(int port, int requestedGridSize) {
        //Create the server using socket stuff (TODO)
        try {
            this.serverSocket = new ServerSocket(port);
            this.serverSocket.setSoTimeout(5000); //5s timeout TODO (FOR DEBUGGING)
            this.game = new Game(requestedGridSize);
            this.agents = new ArrayList<ConnectionAgent>();
            this.playerNames = new ArrayList<String>();

            //While loop will break if server times out (5 seconds), causing program to proceed to catch statements
            while (true) {
                Socket server = serverSocket.accept();
                ConnectionAgent ca = new ConnectionAgent(server);
                ca.addMessageListener(this);
                agents.add(ca);
                //agents.add(new ConnectionAgent(new Socket()));
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

    //No idea what this method is supposed to do yet (TODO)
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
        this.broadcast("Starting game. Please be patient...");
    }



    //Sends a message to the desired source
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
        if (message.contains("Username Creation: ")) {
            String username = message.substring(19, (message.length()));
            //TODO Add some logic so they can't join a game that is already in session
            this.game.addPlayer(username);
            this.playerNames.add(username);
            System.out.println("!!! " + username + " has connected");
        }
        else if (message.contains("/map")) {
            try {
                String player = message.substring(5, message.length());
                //TODO Needs logic to send the correct board to the correct person
                //I.E. Can't see where ships are when looking at another player's map
                this.sendMessageToSource(this.game.getGrid(player).toString(), source);
            }
            catch (StringIndexOutOfBoundsException e) {
                //TODO Send usage message to source of request
            }
        }
        else if (message.equals("/start")) {
            if (this.playerNames.size() > 1) {
                this.startGame();
            }
            else {
                //TODO Send message to player "Not enough players to start the game"
                this.broadcast("Not enough players to start the game");
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
