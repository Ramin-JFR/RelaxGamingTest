package Model.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class DefaultClusterDetector implements ClusterDetectionStrategy {
    private final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    @Override
    public List<Cluster> findClusters(SymbolsWeight[][] grid) {
        int size = grid.length;
        boolean[][] visited = new boolean[size][size];
        List<Cluster> clusters = new ArrayList<>();
        Set<Position> destroyedBlockers = new HashSet<>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                SymbolsWeight symbol = grid[row][col];


                if (visited[row][col] || symbol == SymbolsWeight.BL || symbol == SymbolsWeight.WR) continue;

                List<Position> clusterPositions = new ArrayList<>();
                Set<Position> localWilds = new HashSet<>();
                dfs(row, col, symbol, clusterPositions, visited, grid, localWilds);

                if (clusterPositions.size() >= 5) {
                    clusters.add(new Cluster(symbol, clusterPositions));
                }
            }
        }

        for (Cluster cluster : clusters) {
            for (Position pos : cluster.positions()) {
                for (int[] dir : directions) {
                    int newRow = pos.row() + dir[0];
                    int newCol = pos.col() + dir[1];
                    if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                        if (grid[newRow][newCol] == SymbolsWeight.BL) {
                            destroyedBlockers.add(new Position(newRow, newCol));
                        }
                    }
                }
            }
        }

        if (!destroyedBlockers.isEmpty()) {
            clusters.add(new Cluster(SymbolsWeight.BL, new ArrayList<>(destroyedBlockers)));
        }

        return clusters;
    }


    private void dfs(int row, int col, SymbolsWeight targetSymbol, List<Position> cluster, boolean[][] visited,
                     SymbolsWeight[][] grid, Set<Position> localWilds) {
        int size = grid.length;
        if (row < 0 || row >= size || col < 0 || col >= size) return;

        SymbolsWeight current = grid[row][col];
        Position pos = new Position(row, col);

        if (current == SymbolsWeight.BL) return;

        if (current != SymbolsWeight.WR && visited[row][col]) return;

        if (current != targetSymbol && current != SymbolsWeight.WR) return;

        if (current == SymbolsWeight.WR) {
            if (localWilds.contains(pos)) return;
            localWilds.add(pos);
        } else {
            visited[row][col] = true;
        }

        cluster.add(pos);

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            dfs(newRow, newCol, targetSymbol, cluster, visited, grid, localWilds);
        }
    }
}