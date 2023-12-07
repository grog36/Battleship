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

    //Constructor
    public BattleServer(int port, int requestedGridSize) {
        //Create the server using socket stuff (TODO)
        try {
            this.serverSocket = new ServerSocket(port);
            this.serverSocket.setSoTimeout(5000); //5s timeout TODO (FOR DEBUGGING)
            this.game = new Game(requestedGridSize);
            this.agents = new ArrayList<ConnectionAgent>();

            //While loop will break if server times out (5 seconds), causing program to proceed to catch statements
            while (true) {
                Socket server = serverSocket.accept();
                ConnectionAgent ca = new ConnectionAgent(server);
                ca.addMessageListener(this);
                agents.add(ca);
                ca.sendMessage("Goodbye.");
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
        System.out.println("Broadcast.");
    }

    //Closes all connected agents
    private void closeAllConnections() {
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

    /** TODO
     * Used to notify observers that the subject has receieved a message.
     * 
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source) {
        System.out.println("Message receieved.");
        System.out.println(message);
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
