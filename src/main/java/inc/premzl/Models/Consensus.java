package inc.premzl.Models;

public class Consensus {
    private int score;
    private String consensus;

    public Consensus() {
        consensus = "";
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getConsensus() {
        return consensus;
    }

    public void addConsensus(String consensus) {
        this.consensus += consensus;
    }
}
