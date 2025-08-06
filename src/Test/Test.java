package Test;

import Model.grid.Cluster;
import Model.grid.ClusterDetectionStrategy;
import Model.grid.DefaultClusterDetector;
import Model.grid.SymbolsWeight;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultClusterDetectorTest {

    @Test
    void testFindClustersOnProvidedGrid() {
        SymbolsWeight[][] grid = {
                {SymbolsWeight.L5, SymbolsWeight.WR, SymbolsWeight.H3, SymbolsWeight.L8, SymbolsWeight.BL, SymbolsWeight.L5, SymbolsWeight.H2, SymbolsWeight.L8},
                {SymbolsWeight.L8, SymbolsWeight.H3, SymbolsWeight.L6, SymbolsWeight.L7, SymbolsWeight.L7, SymbolsWeight.L7, SymbolsWeight.L7, SymbolsWeight.BL},
                {SymbolsWeight.L5, SymbolsWeight.L7, SymbolsWeight.WR, SymbolsWeight.H3, SymbolsWeight.H3, SymbolsWeight.L8, SymbolsWeight.H3, SymbolsWeight.BL},
                {SymbolsWeight.WR, SymbolsWeight.L5, SymbolsWeight.H2, SymbolsWeight.H3, SymbolsWeight.BL, SymbolsWeight.H1, SymbolsWeight.H2, SymbolsWeight.L6},
                {SymbolsWeight.L6, SymbolsWeight.H1, SymbolsWeight.BL, SymbolsWeight.L5, SymbolsWeight.H3, SymbolsWeight.H1, SymbolsWeight.BL, SymbolsWeight.H3},
                {SymbolsWeight.L6, SymbolsWeight.BL, SymbolsWeight.L6, SymbolsWeight.L6, SymbolsWeight.L5, SymbolsWeight.L6, SymbolsWeight.H2, SymbolsWeight.L8},
                {SymbolsWeight.L8, SymbolsWeight.H2, SymbolsWeight.WR, SymbolsWeight.L8, SymbolsWeight.H3, SymbolsWeight.BL, SymbolsWeight.L6, SymbolsWeight.L6},
                {SymbolsWeight.BL, SymbolsWeight.WR, SymbolsWeight.H3, SymbolsWeight.H3, SymbolsWeight.WR, SymbolsWeight.H1, SymbolsWeight.L6, SymbolsWeight.L7}
        };


        ClusterDetectionStrategy detector = new DefaultClusterDetector();

        List<Cluster> clusters = detector.findClusters(grid);

        assertFalse(clusters.isEmpty(), "No clusters detected.");

        clusters.forEach(c -> {
            System.out.println("Symbol: " + c.symbol() + ", Size: " + c.getLength());
            System.out.println("  Positions: " + c.positions());
        });


    }
}
