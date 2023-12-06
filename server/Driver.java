package server;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Grid test = new Grid("grog");
        System.out.println(test.toString());
        
        //Request coordinate to shoot
        Scanner sc = new Scanner(System.in);

        while (!test.isDead()) {
            System.out.print("Type in the row you would like to shoot [0-9]: ");
            int rowIndex = sc.nextInt();
            System.out.print("Type in the column you would like to shoot [0-9]: ");
            int colIndex = sc.nextInt();
            test.shoot(rowIndex, colIndex);
            System.out.println(test.toString());
        }


        System.out.println("YOU HAVE SUNK ALL SHIPS!");
        sc.close();
    }
}