package inc.premzl.Models;

public class Consensus {
    private int score;
    private int hamming;
    private String consensus;

    public Consensus() {
        consensus = "";
        score = 0;
    }

    public Consensus(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getConsensus() {
        return consensus;
    }

    public void setConsensus(String consensus) {
        this.consensus = consensus;
    }

    public void addConsensus(String consensus) {
        this.consensus += consensus;
    }

    public int getHamming() {
        return hamming;
    }

    public void setHamming(int hamming) {
        this.hamming = hamming;
    }
}
