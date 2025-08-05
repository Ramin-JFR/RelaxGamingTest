package Model.grid;

import java.util.List;

public record Cluster(SymbolsWeight symbol, List<Position> positions) {
    public Cluster(SymbolsWeight symbol, List<Position> positions) {
        this.symbol = symbol;
        this.positions = List.copyOf(positions);
    }

    public int getLength() {
        return positions.size();
    }


}