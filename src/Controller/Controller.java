package Controller;

import Model.GameResult;
import Model.db.Db;
import Model.db.DbRow;
import Model.dto.GameRoundResponse;
import Model.grid.*;
import Model.payout.DefaultPayoutCalculator;
import Model.payout.PayoutCalculator;
import Model.user.Account;
import Model.user.Transaction;
import View.View;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    private final View view;
    private final GridModel gridModel;
    private final Avalanche avalanche;
    private final ClusterDetectionStrategy clusterDetector;
    private final PayoutCalculator payout;
    private final int row;
    private final int column;


    public Controller(int row, int column) {
        this.row = row;
        this.column = column;
        this.gridModel = new GridModel(row, column);
        this.view = new View();
        this.avalanche = new Avalanche();
        this.clusterDetector = new DefaultClusterDetector();
        this.payout = new DefaultPayoutCalculator();
    }

    public GameResult runGame(float betAmount) throws SQLException {
        float totalWin = 0;
        float winPayment;


        do {
            view.displayGrid(gridModel.getGrid());

            List<Cluster> clusters = clusterDetector.findClusters(gridModel.getGrid());

            SymbolsWeight[][] afterRemoval = avalanche.removeClusters(gridModel.getGrid(), clusters);
            SymbolsWeight[][] afterCollapse = avalanche.collapseGrid(afterRemoval);
            SymbolsWeight[][] afterFallDown = avalanche.newSymbolsFallDown(afterCollapse);

            winPayment = payout.calculatePayoutForMatch(clusters, betAmount);


            view.displayPayoutForLastMatch(winPayment);
            System.out.println("=============");

            totalWin += winPayment;
            gridModel.setGrid(afterFallDown);

        } while (winPayment > 0);

        GridModel replaceGridModel = new GridModel(row, column);
        gridModel.setGrid(replaceGridModel.getGrid());


        return new GameResult(totalWin);
    }

    public void getListOfTransactions(String accountNumber) {
        try {
            Account account = new Account(accountNumber);
            List<Transaction> transactions = account.getTransactions();

            System.out.println("===== Transactions for Account: " + accountNumber + " =====");
            for (Transaction t : transactions) {
                System.out.println("Type: " + t.getDescription() + ", Amount: " + t.getAmountInEuros() +
                        ", Timestamp: " + t.getTimestamp() + ", Expiry: " + t.getExpiryDate());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }
    }


//    public GameRoundResponse playOneRound(float betAmount) {
//        SymbolsWeight[][] initialGrid = gridModel.getGrid();
//
//        List<Cluster> clusters = clusterDetector.findClusters(initialGrid);
//
//        List<List<Position>> clusterPositions = clusters.stream()
//                .map(Cluster::positions)
//                .toList();
//
//        SymbolsWeight[][] afterRemoval = avalanche.removeClusters(initialGrid, clusters);
//        SymbolsWeight[][] afterCollapse = avalanche.collapseGrid(afterRemoval);
//        SymbolsWeight[][] afterFallDown = avalanche.newSymbolsFallDown(afterCollapse);
//
//        float winAmount = payout.calculatePayoutForMatch(clusters, betAmount);
//
//        return new GameRoundResponse(
//                initialGrid,
//                clusterPositions,
//                afterRemoval,
//                afterCollapse,
//                afterFallDown,
//                winAmount
//        );
//    }

}