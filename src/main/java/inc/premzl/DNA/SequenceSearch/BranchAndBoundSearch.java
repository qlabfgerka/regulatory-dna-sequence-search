package inc.premzl.DNA.SequenceSearch;

import inc.premzl.Models.Consensus;

import java.util.ArrayList;
import java.util.List;

import static inc.premzl.DNA.DNAOperations.getKmers;

public class BranchAndBoundSearch {
    public static int getHamming(String first, String second) {
        int max = Math.max(first.length(), second.length());
        int min = Math.min(first.length(), second.length());
        int hamming = 0;

        for (int i = 0; i < max; i++) {
            if (i >= min || first.charAt(i) != second.charAt(i)) ++hamming;
        }

        return hamming;
    }

    public static Consensus generateConsensuses(String current, int l, int score, Consensus best, List<String> sequences) {
        if (score >= best.getScore() && best.getScore() != -1) return best;

        List<List<String>> kmers = new ArrayList<>();
        for (String sequence : sequences) kmers.add(getKmers(sequence, current.length()));
        int minHamming, hamming, minHammings = 0;
        for (List<String> kmer : kmers) {
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
            return best;
        }

        generateConsensuses(current + "A", l, score, best, sequences);
        generateConsensuses(current + "G", l, score, best, sequences);
        generateConsensuses(current + "T", l, score, best, sequences);
        generateConsensuses(current + "C", l, score, best, sequences);

        return best;
    }
}
