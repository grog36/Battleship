package common;

import java.io.IOException;
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
        try {
            this.socket = socket;
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());
            this.thread = new Thread(this);
            this.thread.start();
        }
        catch (IOException e) {
            System.out.println("IOException encountered. Error trying to create an output stream from socket.");
            System.exit(1);
        }
    }

    /**
     * Sends message to out (PrintStream)
     * 
     * @param message The message to be sent to out (PrintStream)
     */
    public void sendMessage(String message) {
        this.out.print(message);
    }

    //TODO method (Maybe this is the purpose?)
    public boolean isConnected() {
        return socket.isConnected();
    }

    //TODO method
    public void close() {
        try {
            this.in.close();
            this.socket.close();
            this.out.close();
        }
        catch (IOException e) {
            System.out.println("IOException encountered. Error trying to create an output stream from socket.");
            System.exit(1);
        }
    }

    //TODO method
    public void run() {
        System.out.println("Starts the thread");
        while (this.isConnected()) {
            if (this.in.hasNextLine()) {
                this.notifyReceipt(this.in.nextLine());
            }
        }
    }

}
