package View;

import Model.grid.Cluster;
import Model.grid.SymbolsWeight;

import java.util.List;

public class View {
    public void displayGrid(SymbolsWeight[][] grid) {
        for (SymbolsWeight[] row : grid) {
            for (SymbolsWeight symbol : row) {
                System.out.print(symbol.name() + "\t");
            }
            System.out.println();
        }
    }

    public void displayClusters(List<Cluster> clusters) {
        int i = 1;
        for (Cluster cluster : clusters) {
            System.out.println("Cluster " + (i++) + ": Symbol = " + cluster.symbol() + ", Positions = " + cluster.positions()
            );
        }
    }


    public void displayPayoutForLastMatch(double winPayment) {

        System.out.println();
        System.out.println("you won " + winPayment + " in this match ");
    }

    public void displayOverallWin(double winPayment) {
        System.out.println();
        System.out.println("you won " + winPayment + " till now (including your own money)");
    }

    public void winBeforeDoubleUpView(float winnings) {
        System.out.println("You won " + winnings + " till now (excluding your own money).");
        System.out.println("You have a chance to double your win payment in this game. If you win your prize will be double but if you lose you won't get any money.\nNow you have 2 options\n1 => play\nOR\n2 => not playing");
    }
    public void displaySummary(float totalWin, float totalBet) {
        float rtp = (totalWin / totalBet) * 100;
        System.out.println("===Summery of this run=====");
        System.out.println("Total win: " + totalWin);
        System.out.printf("Simulation completed: RTP = %.2f%%\n", rtp);
    }

    public void displayOverallWinAfterDoubleUp(float balance) {
        System.out.println("\nIn summary you have " + balance + " in your account.\n");
    }


    public void gameFinished(float overallPayment) {
        System.out.println("Game is done and you won: "+overallPayment);
    }


}
