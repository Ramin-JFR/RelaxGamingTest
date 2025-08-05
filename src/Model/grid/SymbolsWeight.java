package Model.grid;

public enum SymbolsWeight {
    H1(100, 100),
    H2(100, 100),
    H3(100, 100),
    H4(100, 100),
    L5(100, 100),
    L6(100, 100),
    L7(100, 100),
    L8(100, 100),
    WR(100, 100),
    BL(100, 0),
    EM(0,0),
    ;

    private final int initialWeight;
    private final int avalancheWeight;


    SymbolsWeight(int initialWeight, int avalancheWeight) {
        this.initialWeight = initialWeight;
        this.avalancheWeight = avalancheWeight;
    }

    public int getInitialWeight(){
        return initialWeight;
    }

    public int getAvalancheWeight() {
        return avalancheWeight;
    }
}
