package inc.premzl.DNA.SequenceSearch;

import inc.premzl.Models.Consensus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inc.premzl.DNA.DNAOperations.getKmers;
import static inc.premzl.DNA.DNAOperations.getSequences;

public class BranchAndBoundSearch {
    private List<List<List<String>>> kmers;
    private final int n;
    private final int t;
    private final int l;
    private final String filename;

    public BranchAndBoundSearch(int n, int t, int l, String filename) throws IOException {
        this.n = n;
        this.t = t;
        this.l = l;
        this.filename = filename;
        this.getAllKmers();
    }

    public Consensus branchAndBoundSearch() {
        Consensus consensus = new Consensus(-1);
        generateConsensuses("", 0, consensus);
        return consensus;
    }

    public int getHamming(String first, String second) {
        int max = Math.max(first.length(), second.length());
        int min = Math.min(first.length(), second.length());
        int hamming = 0;

        for (int i = 0; i < max; i++)
            if (i >= min || first.charAt(i) != second.charAt(i)) ++hamming;

        return hamming;
    }

    public void generateConsensuses(String current, int score, Consensus best) {
        if (score >= best.getScore() && best.getScore() != -1) return;

        if (current.length() > 0) {
            int minHamming, hamming, minHammings = 0;
            for (List<String> kmer : kmers.get(current.length() - 1)) {
                minHamming = Integer.MAX_VALUE;
                for (String lmer : kmer) {
                    hamming = getHamming(lmer, current);
                    if (hamming < minHamming) minHamming = hamming;
                }
                minHammings += minHamming;
            }

            score += minHammings;

            if (current.length() == l) {
                if (minHammings < best.getHamming() || best.getScore() == -1) {
                    best.setConsensus(current);
                    best.setScore(score);
                    best.setHamming(minHammings);
                }
                return;
            }
        }

        generateConsensuses(current + "A", score, best);
        generateConsensuses(current + "G", score, best);
        generateConsensuses(current + "T", score, best);
        generateConsensuses(current + "C", score, best);
    }

    private void getAllKmers() throws IOException {
        List<String> sequences = getSequences(filename, n, t);
        kmers = new ArrayList<>();
        List<List<String>> kmer;

        for (int i = 1; i <= l; i++) {
            kmer = new ArrayList<>();
            for (String sequence : sequences) kmer.add(getKmers(sequence, i));
            kmers.add(kmer);
        }
    }
}
