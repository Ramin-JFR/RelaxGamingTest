package Model.grid;

import java.util.List;

public interface ClusterDetectionStrategy {
    List<Cluster> findClusters(SymbolsWeight[][] grid);
}
