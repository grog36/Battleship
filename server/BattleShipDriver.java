package server;

public class BattleShipDriver {
    private final static int DEFAULT_GRID_SIZE = 10;
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                int portNumber = Integer.parseInt(args[0]);
                BattleServer bs = new BattleServer(portNumber, DEFAULT_GRID_SIZE);
            }
            catch (NumberFormatException e) {
                printUsageMessage();
            }
        }
        else if (args.length == 2) {
            try {
                int portNumber = Integer.parseInt(args[0]);
                int gridSize = Integer.parseInt(args[1]);
                BattleServer bs = new BattleServer(portNumber, gridSize);
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
        System.out.println("Usage: java server.BattleShipDriver <portnumber> |gridsize| ");
        System.out.println("Example: java server.BattleShipDriver 6767 10");
    }
}
