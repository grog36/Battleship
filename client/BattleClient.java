package client;

import common.MessageSource;
import common.MessageListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BattleClient extends MessageSource implements MessageListener {
    //Host
    private InetAddress host;
    //Port
    private int port;
    //Username
    private String username;

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
