package client;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BattleClient extends MessageSource implements MessageListener {
    //Host
    private InetAddress host;
    //Port
    private int port;
    //Username
    private String username;
    //Connection Agent
    private ConnectionAgent connection;

    //Constructor TODO ?
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

    //TODO method
    public void connect() {
        try {
            this.connection = new ConnectionAgent(new Socket(this.host, this.port));
            this.connection.addMessageListener(this);
            this.send("Username Creation: " + this.username);
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
            System.exit(3);
        }
    }

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

    public void send(String message) {
        this.connection.sendMessage(message + "\n");
    }
}
