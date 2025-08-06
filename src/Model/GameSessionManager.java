package Model;

import Controller.Controller;
import Model.user.Account;
import View.View;

import java.util.Scanner;

public class GameSessionManager {
    private final Scanner scanner = new Scanner(System.in);
    private final Controller controller;
    private View view = new View();



    public GameSessionManager(Controller controller) {
        this.controller = controller;
    }


    public void runSimulation(int rounds, float betPerRound, int row, int column) throws Exception {
        float totalBet = 0, totalWin = 0;
        GameResult result = null;

        for (int i = 0; i < rounds; i++) {
            totalBet += betPerRound;
            Controller controller = new Controller(row, column);
            result = controller.runGame(betPerRound);
            totalWin += result.finalWin;

            if (result.hasWin && Math.random() < 0.5) {
                float doubled = Math.random() < 0.5 ? 0 : result.winPrice * 2;
                totalWin += (doubled - result.winPrice);
            }
        }
        Account account = new Account("accountNumber");
        account.applyTransaction(totalWin >= 0, totalWin);

        view.displaySummary(totalWin,totalBet);
    }

    public void runOneMatch(String accountNumber, float bet, int row, int column) throws Exception {
        float totalWin = 0;
        GameResult result = null;

            Controller controller = new Controller(row, column);
            result = controller.runGame(bet);

            if (result.hasWin && Math.random() < 0.5) {
                float doubled = Math.random() < 0.5 ? 0 : result.winPrice * 2;
                totalWin += (doubled - result.winPrice);
            }

        Account account = new Account(accountNumber);
        account.applyTransaction(totalWin >= 0, totalWin);

        view.displaySummary(totalWin, 0);


    }
}
