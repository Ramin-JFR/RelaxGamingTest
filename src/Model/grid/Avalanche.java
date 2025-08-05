package Model.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Avalanche {
    private final List<SymbolsWeight> avalanchePool = new ArrayList<>();

    public Avalanche() {
        avalancheWeightPool();
    }

    private void avalancheWeightPool() {
        avalanchePool.clear();
        for (SymbolsWeight symbolsWeight : SymbolsWeight.values()) {
            int weight = symbolsWeight.getAvalancheWeight();
            for (int i = 0; i < weight; i++) {
                avalanchePool.add(symbolsWeight);
            }
        }
    }

    public SymbolsWeight[][] removeClusters(SymbolsWeight[][] gridModel, List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            for (Position position : cluster.positions()) {
                gridModel[position.row()][position.col()] = SymbolsWeight.EM;
            }
        }
        return gridModel;
    }

    public SymbolsWeight[][] collapseGrid(SymbolsWeight[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int col = 0; col < cols; col++) {
            boolean changed;
            do {
                changed = false;
                for (int row = rows - 1; row > 0; row--) {
                    if (grid[row][col] == SymbolsWeight.EM && grid[row - 1][col] != SymbolsWeight.EM) {
                        grid[row][col] = grid[row - 1][col];
                        grid[row - 1][col] = SymbolsWeight.EM;
                        changed = true;
                    }
                }
            } while (changed);
        }
        return grid;
    }

    public SymbolsWeight[][] newSymbolsFallDown(SymbolsWeight[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        Random random = new Random();

        if (avalanchePool.isEmpty()) {
            avalancheWeightPool();
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (grid[row][col] == SymbolsWeight.EM) {
                    grid[row][col] = avalanchePool.get(random.nextInt(avalanchePool.size()));
                }
            }
        }
        return grid;
    }
}
