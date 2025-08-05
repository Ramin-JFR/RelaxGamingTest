package Model.payout;

import Model.grid.Cluster;
import Model.grid.SymbolsWeight;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPayoutCalculator implements PayoutCalculator {
    private static final Map<SymbolsWeight, Map<Integer, Integer>> PAYOUT_TABLE = new HashMap<>();
    public static float winPayment = 10;

    static {
        addPayout(SymbolsWeight.H1, new int[]{5, 6, 7, 8, 10});
        addPayout(SymbolsWeight.H2, new int[]{4, 5, 6, 7, 9});
        addPayout(SymbolsWeight.H3, new int[]{4, 5, 6, 7, 9});
        addPayout(SymbolsWeight.H4, new int[]{3, 4, 5, 6, 7});
        addPayout(SymbolsWeight.L5, new int[]{1, 2, 3, 4, 5});
        addPayout(SymbolsWeight.L6, new int[]{1, 2, 3, 4, 5});
        addPayout(SymbolsWeight.L7, new int[]{1, 2, 3, 4, 5});
        addPayout(SymbolsWeight.L8, new int[]{1, 2, 3, 4, 5});

        PAYOUT_TABLE.put(SymbolsWeight.WR, new HashMap<>());
        PAYOUT_TABLE.put(SymbolsWeight.BL, new HashMap<>());
        PAYOUT_TABLE.put(SymbolsWeight.EM, new HashMap<>());
    }

    private static void addPayout(SymbolsWeight symbol, int[] payouts) {
        Map<Integer, Integer> tierMap = new HashMap<>();
        int[][] ranges = {{5, 8}, {9, 12}, {13, 16}, {17, 20}, {21, 64}};
        for (int i = 0; i < ranges.length; i++) {
            int from = ranges[i][0];
            int to = ranges[i][1];
            for (int count = from; count <= to; count++) {
                tierMap.put(count, payouts[i]);
            }
        }
        PAYOUT_TABLE.put(symbol, tierMap);
    }

    @Override
    public float calculatePayoutForMatch(List<Cluster> clusters, float betAmount) {
        float winPayout = 0;
        for (Cluster cluster : clusters) {
            SymbolsWeight symbol = cluster.symbol();
            Map<Integer, Integer> payoutTiers = PAYOUT_TABLE.get(symbol);
            if (payoutTiers == null) continue;
            int basePayout = payoutTiers.getOrDefault(cluster.getLength(), 0);
            double scaledPayout = (betAmount / 10.0) * basePayout;
            winPayout += (float) scaledPayout;
        }
        if (winPayout > 0) {
            return winPayout + 10;
        }
        return winPayout;
    }


    @Override
    public float getWinPaymentAfterDoubleChance(boolean winner, float sessionWin, String accountNumber) throws SQLException {
        float win = winner ? (sessionWin * 2) : 0;
        return win;
    }


}