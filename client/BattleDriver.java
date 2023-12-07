package client;

import java.util.Scanner;

public class BattleDriver {
    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                //Attempts to connect, throws an error if args are wrong and displays the usage message
                int portNumber = Integer.parseInt(args[1]);
                BattleClient bc = new BattleClient(args[0], portNumber, args[2]);

                //Connects to server
                bc.connect();

                //Creates a scanner and checks for command-line input. Sends the input to the server via BattleClient
                Scanner scanner = new Scanner(System.in);
                while(bc.isConnected()) {
                    if (scanner.hasNextLine()) {
                        bc.send(scanner.nextLine());
                    }
                }
                scanner.close();
            }
            catch (NumberFormatException e) {
                printUsageMessage();
            }
        }
        else {
            printUsageMessage();
        }
    }

    /**
     * Helper method to print the usage message for this driver
     */
    public static void printUsageMessage() {
        System.out.println("Invalid Usage!");
        System.out.println("Usage: java client.BattleDriver <hostname> <portnumber> <username>");
        System.out.println("Example: java client.BattleDriver localhost 6767 Bobby");
    }
}
