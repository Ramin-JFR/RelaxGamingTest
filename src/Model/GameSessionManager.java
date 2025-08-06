package Model;

import Controller.Controller;
import Model.db.Db;
import Model.user.Account;

import java.sql.SQLException;
import java.util.Scanner;

public class GameSessionManager {
    private final Scanner scanner = new Scanner(System.in);
    private final Controller controller;


    public GameSessionManager(Controller controller) {
        this.controller = controller;
    }


    public void runSimulation(int rounds, float betPerRound, int row, int column) throws Exception {
        float totalBet = 0, totalWin = 0;

        for (int i = 0; i < rounds; i++) {
            totalBet += betPerRound;
            Controller controller = new Controller(row, column);
            GameResult result = controller.runGame(betPerRound);
            totalWin += result.finalWin;

            if (result.hasWin && Math.random() < 0.5) {
                float doubled = Math.random() < 0.5 ? 0 : result.winPrice * 2;
                totalWin += (doubled - result.winPrice);
            }
        }
        Account account = new Account("accountNumber");
        account.setAccountBalance(totalWin);
        float temp = account.getAccountBalance();
        account.setAccountBalance(totalWin+temp);

        float rtp = (totalWin / totalBet) * 100;
        System.out.printf("Simulation completed: RTP = %.2f%%\n", rtp);
    }
}
