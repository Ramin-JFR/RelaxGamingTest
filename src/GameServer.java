import Controller.Controller;
import Model.GameSessionManager;
import Model.db.Db;
import Model.user.Account;

import java.sql.SQLException;
import java.util.Scanner;

public class GameServer {
    public static void main(String[] args) throws SQLException {
        Db.run();
        int row = 8, column = 8;
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller(row, column);
        GameSessionManager sessionManager = new GameSessionManager(controller);

        try {
            System.out.println("""
                    You have 3 options:
                    1=> Play a match
                    2=> Run simulation
                    3=> Retrieve transactions""");
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
        scanner.nextLine();

        System.out.print("Enter bet amount per simulation: ");
        float betPerRound = scanner.nextFloat();
        scanner.nextLine();

        float totalBet = betPerRound * simulations;

        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();

        Account account = new Account(accountNumber);
        float balance = account.getAccountBalance();

        if (balance < totalBet) {
            System.out.println("You don't have enough balance to place this bet.\n" +
                    "Do you want to add money? If yes, enter the amount to deposit. If not, type 'no':");

            String reply = scanner.nextLine();

            if (reply.equalsIgnoreCase("no")) {
                System.exit(0);
            }

            float depositAmount;
            try {
                depositAmount = Float.parseFloat(reply);
                account.addDepositToAccount(accountNumber, depositAmount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Exiting.");
                return;
            }

            account.setAccountBalance(depositAmount + balance);
            balance = account.getAccountBalance();
        }


        if (balance < totalBet) {
            System.out.println("Still not enough balance. Exiting.");
            return;
        }

        System.out.println("You have enough money. Starting simulation...");
        sessionManager.runSimulation(accountNumber, simulations, betPerRound, row, column);
    }

    private static void oneMatch(GameSessionManager sessionManager, Scanner scanner, int row, int column) throws Exception {
        System.out.print("Enter bet amount: ");
        float bet = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter your account number: ");
        String accountNumber = scanner.nextLine();

        Account account = new Account(accountNumber);
        float balance = account.getAccountBalance();

        if (balance >= bet) {
            System.out.println("You have enough money.");
            sessionManager.runOneMatch(accountNumber, bet, row, column);
        } else {
            System.out.println("You don't have enough balance to place this bet.\n" +
                    "Do you want to add money?\n" +
                    "If yes, please enter the amount to deposit. If not, type 'no':");

            String reply = scanner.nextLine();
            if (reply.equalsIgnoreCase("no")) {
                System.exit(0);
            }

            float depositAmount;
            try {
                depositAmount = Float.parseFloat(reply);
                account.addDepositToAccount(accountNumber, depositAmount);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Exiting.");
                return;
            }

            account.setAccountBalance(depositAmount + balance);
            sessionManager.runOneMatch(accountNumber, bet, row, column);
        }
    }

    private static void listOfTransaction(Controller controller, Scanner scanner) throws Exception {
        System.out.println("Please enter your account number");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        controller.getListOfTransactions(accountNumber);


    }
}