package client;

import java.io.PrintStream;

import common.MessageListener;
import common.MessageSource;

public class PrintStreamMessageListener implements MessageListener {

    //OutputStream
    private PrintStream out;

    //Constructor (TODO)
    public PrintStreamMessageListener(PrintStream out) {
        this.out = out;
    }


    /**
     * Used to notify observers that the subject has receieved a message.
     * 
     * @param message The message received by the subject
     * @param source The source from which this message originated (if needed).
     */
    public void messageReceived(String message, MessageSource source) {
        this.out.print(message);
    }
    
    //TODO Method 2 implemented from MessageListener
    public void sourceClosed(MessageSource source) {
        System.out.println("Source closed.");
    }
}