package Model.dto;

import Model.grid.SymbolsWeight;
import Model.grid.Position;

import java.util.List;

public class GameRoundResponse {
    public SymbolsWeight[][] initialGrid;
    public List<List<Position>> clusters;
    public SymbolsWeight[][] afterRemoval;
    public SymbolsWeight[][] afterCollapse;
    public SymbolsWeight[][] afterFallDown;
    public float winAmount;

    public GameRoundResponse(
            SymbolsWeight[][] initialGrid,
            List<List<Position>> clusters,
            SymbolsWeight[][] afterRemoval,
            SymbolsWeight[][] afterCollapse,
            SymbolsWeight[][] afterFallDown,
            float winAmount
    ) {
        this.initialGrid = initialGrid;
        this.clusters = clusters;
        this.afterRemoval = afterRemoval;
        this.afterCollapse = afterCollapse;
        this.afterFallDown = afterFallDown;
        this.winAmount = winAmount;
    }
}