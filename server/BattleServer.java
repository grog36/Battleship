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
            this.serverSocket.setSoTimeout(10000); //10s timeout
            this.game = new Game(requestedGridSize);
            this.agents = new ArrayList<ConnectionAgent>();

            //While loop will break if server times out (10 seconds), causing program to proceed to catch statements
            while (true) {
                Socket client = serverSocket.accept();
                ConnectionAgent ca = new ConnectionAgent(client);
                agents.add(ca);
                ca.run();
                
            }
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
            System.exit(1);
        }
        catch (IllegalArgumentException e) {
            System.out.println("The port number is invalid. Please try again");
            System.exit(2);
        }
        catch (SecurityException e) {
            System.out.println("A security manager exists and is currently not allowing this operation.");
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


    //TODO Method 1 implemented from MessageListener
    public void messageReceived(String message, MessageSource source) {
        System.out.println("Message receieved.");
    }
    //TODO Method 2 implemented from MessageListener
    public void sourceClosed(MessageSource source) {
        System.out.println("Source closed.");
    }
}
