package client;

import common.MessageListener;
import common.MessageSource;

import java.io.PrintStream;

public class PrintStreamMessageListener implements MessageListener {

    //OutputStream
    private PrintStream out;

    /**
     * Constructor
     * 
     * @param out The Printstream to use as an output
     */
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