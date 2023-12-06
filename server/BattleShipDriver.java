package server;

public class BattleShipDriver {
    private final static int DEFAULT_GRID_SIZE = 10;
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                int portNumber = Integer.parseInt(args[0]);
                BattleServer bs = new BattleServer(portNumber, DEFAULT_GRID_SIZE);
                bs.listen();
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
                bs.listen();
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
        System.out.println("Usage: java server.BattleShipDriver <port number> |grid size| ");
        System.out.println("Example: java server.BattleShipDriver 6767 10");
    }
}
