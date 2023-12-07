package common;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionAgent extends MessageSource implements Runnable {
    //The socket containing the connection
    private Socket socket;

    //The Scanner containing the input for the socket
    private Scanner in;

    //The PrintStream containing the output for the socket
    private PrintStream out;

    //The thread that the process runs on
    private Thread thread;


    /**
     * Constructor
     * 
     * @param socket The socket containing the connection
     */
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
        this.out.print(message + "\n");
    }

    /**
     * Returns whether the current socket is connected (not closed)
     * 
     * @return Returns whether the current socket is connected (not closed)
     */
    public boolean isConnected() {
        return !(this.socket.isClosed());
    }

    /**
     * Closes all sockets and streams
     */
    public void close() {
        try {
            this.socket.close();
            this.in.close();
            this.out.close();
        }
        catch (IOException e) {
            System.out.println("IOException encountered. Error trying to create an output stream from socket.");
            System.exit(1);
        }
    }

    /**
     * Runs this process as a thread
     */
    public void run() {
        while (this.isConnected()) {
            if (this.in.hasNextLine()) {
                this.notifyReceipt(this.in.nextLine());
            }
        }
    }
}