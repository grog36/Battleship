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
            this.connection = new ConnectionAgent(new Socket(this.host, this.port));
            this.connection.run();
        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host exception. No IP address was found. Please try again.");
            System.exit(1);
        }
        catch (SecurityException e) {
            System.out.println("Security Exception. A security manager exists and operation is not permitted.");
            System.exit(2);
        }
        catch (IOException e) {
            System.out.println("An I/O error has occured. Please try again.");
            System.exit(3);
        }
    }

    //TODO method
    public void connect() {

    }

    //TODO Method 1 implemented from MessageListener
    public void messageReceived(String message, MessageSource source) {
        System.out.println("Message receieved.");
    }

    //TODO Method 2 implemented from MessageListener
    public void sourceClosed(MessageSource source) {
        System.out.println("Source closed.");
    }

    //TODO method
    public void send(String message) {

    }
}
