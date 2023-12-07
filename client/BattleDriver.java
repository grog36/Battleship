package client;

import java.util.Scanner;

public class BattleDriver {
    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                //TODO ('Read messages from the keyboard and send them to the client')
                int portNumber = Integer.parseInt(args[1]);
                BattleClient bc = new BattleClient(args[0], portNumber, args[2]);
                bc.connect();
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

    public static void printUsageMessage() {
        System.out.println("Invalid Usage!");
        System.out.println("Usage: java client.BattleDriver <hostname> <portnumber> <username>");
        System.out.println("Example: java client.BattleDriver localhost 6767 Bobby");
    }
}
