import Controller.Controller;
import Model.GameSessionManager;
import Model.db.Db;
import Model.dto.GameRoundResponse;
import Model.grid.Position;
import Model.user.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GameServer {
    public static void main(String[] args) throws SQLException {
        Db.run();
        int row = 8, column = 8;
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller(row, column);
        GameSessionManager sessionManager = new GameSessionManager(controller);

        try {
            System.out.println("You have 2 options:" +
                    "1=> Play a match" +
                    "2=> Run simulation");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    oneMatch(sessionManager, scanner, row, column);
                    break;
                case 2:
                    runSimulation(sessionManager, scanner, row, column);
                    break;
                case 3:
                    listOfTransaction(controller, scanner);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }

    private static void runSimulation(GameSessionManager sessionManager, Scanner scanner, int row, int column) throws Exception {
        System.out.print("Enter number of simulations to run: ");
        int simulations = scanner.nextInt();

        System.out.print("Enter bet amount per simulation: ");
        float betPerRound = scanner.nextFloat();

//        checkAccountBalance("accountNumber",scanner);

        sessionManager.runSimulation(simulations, betPerRound, row, column);
    }

    private static void oneMatch(GameSessionManager sessionManager, Scanner scanner, int row, int column) throws Exception {
        System.out.print("Enter bet amount: ");
        float bet = scanner.nextFloat();
        System.out.println("Enter your account number: ");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();

//        checkAccountBalance(accountNumber, scanner);

        sessionManager.runOneMatch(accountNumber, bet, row, column);
    }

//    private static void checkAccountBalance(String accountNumber, Scanner scanner) throws SQLException {
//        Account account = new Account(accountNumber);
//        float balance = account.getAccountBalance(); // You’ll need to implement this method if not done yet
//
//        if (balance >= betAmount) {
//
//        } else {
//            System.out.println("You don't have enough balance to place this bet.\n" +
//                    "do you wanna add money? ");
//
//
//
//        }
//    }




    private static void listOfTransaction(Controller controller, Scanner scanner) {
        System.out.println("Please enter your account number");
        String accountNumber = scanner.nextLine();


    }
}