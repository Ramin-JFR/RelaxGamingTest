import Controller.Controller;
import Model.GameSessionManager;
import Model.dto.GameRoundResponse;
import Model.grid.Position;

import java.util.List;
import java.util.Scanner;

public class GameServer {
    public static void main(String[] args) {
        int row = 8, column = 8;
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller(row, column);
        GameSessionManager sessionManager = new GameSessionManager(controller);

        try {
                System.out.print("Enter number of simulations to run: ");
                int simulations = scanner.nextInt();

                System.out.print("Enter bet amount per simulation: ");
                float betPerRound = scanner.nextFloat();

                sessionManager.runSimulation(simulations, betPerRound, row, column);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }
}