package Model;

public class GameResult {
    public float winPrice;
    public boolean hasWin;
    public boolean isDoubleUpPlayed;
    public float finalWin;

    public GameResult(float winPrice) {
        this.winPrice = winPrice;
        this.hasWin = winPrice > 0;
        this.finalWin = winPrice;
        this.isDoubleUpPlayed = false;
    }
}