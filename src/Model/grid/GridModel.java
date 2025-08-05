package Model.grid;

import java.util.ArrayList;
import java.util.Random;

public class GridModel {
    private final ArrayList<SymbolsWeight> weightsPool = new ArrayList<>();
    private final Random random = new Random();
    private SymbolsWeight[][] grid;

    public GridModel(int row, int column) {
        initializeWeightedPool();
        grid = new SymbolsWeight[row][column];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                grid[r][c] = getRandomSymbols();
            }
        }
    }

    private void initializeWeightedPool() {
        for (SymbolsWeight symbol : SymbolsWeight.values()) {
            int weight = symbol.getInitialWeight();
            for (int i = 0; i < weight; i++) {
                weightsPool.add(symbol);
            }
        }
    }

    private SymbolsWeight getRandomSymbols() {
        return weightsPool.get(random.nextInt(weightsPool.size()));
    }

    public SymbolsWeight[][] getGrid() {
        return grid;
    }

    public SymbolsWeight[][] setGrid(SymbolsWeight[][] newGrid){
        this.grid = newGrid;
        return grid;
    }

}