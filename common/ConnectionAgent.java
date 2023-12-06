package common;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionAgent extends MessageSource implements Runnable {
    //Socket
    private Socket socket;

    //Scanner
    private Scanner in;

    //PrintStream
    private PrintStream out;

    //Thread
    private Thread thread;


    // TODO CONSTRUCTOR
    public ConnectionAgent(Socket socket) {
        this.socket = socket;
    }

    //TODO method
    public void sendMessage(String message) {

    }

    //TODO method
    public boolean isConnected() {
        return true;
    }

    //TODO method
    public void close() {

    }

    //TODO method
    public void run() {

    }

}
