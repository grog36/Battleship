package client;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BattleClient extends MessageSource implements MessageListener {
    //InetAddress for the socket
    private InetAddress host;

    //Port number for the socket
    private int port;

    //Username for the player who owns this client
    private String username;

    //Connection Agent to contain the connection
    private ConnectionAgent connection;


    /**
     * Constructor
     * 
     * @param hostname The hostname to connect to
     * @param port The port number to connect to
     * @param username The username to set for this user
     */
    public BattleClient(String hostname, int port, String username) {
        try {
            this.host = InetAddress.getByName(hostname);
            this.port = port;
            this.username = username;
        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host exception. No IP address was found. Please try again.");
            System.exit(1);
        }
        catch (SecurityException e) {
            System.out.println("Security Exception. A security manager exists and operation is not permitted.");
            System.exit(2);
        }
    }

    /**
     * Connects to the previously setup socket
     */
    public void connect() {
        try {
            this.connection = new ConnectionAgent(new Socket(this.host, this.port));
            this.connection.addMessageListener(this);
            //Sends a first time message to the server containing the requested username for the player
            this.send("Username Creation: " + this.username);
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
            System.exit(3);
        }
    }

    /**
     * Checks to see if the client is connected to the server
     * 
     * @return Returns true if the client is connected to the server, false otherwise
     */
    public boolean isConnected() {
        return this.connection.isConnected();
    }

    /**
     * Used to notify observers that the subject has receieved a message.
     * 
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source) {
        //If the message is "Goodbye.", the connection has been closed by the server
        if (message.equals("Goodbye.")) {
            this.connection.close();
            System.out.println("Connection to the server has been closed. Please press enter to quit.");
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

    /**
     * Sends a message to the server
     * 
     * @param message The message you wish to send to the server
     */
    public void send(String message) {
        this.connection.sendMessage(message + "\n");
    }
}
