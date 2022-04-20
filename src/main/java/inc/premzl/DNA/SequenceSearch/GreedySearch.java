package inc.premzl.DNA.SequenceSearch;

import inc.premzl.Models.Consensus;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static inc.premzl.DNA.DNAOperations.getKmers;
import static inc.premzl.DNA.DNAOperations.getSequences;

public class GreedySearch {
    private List<List<String>> kmers;
    private final int n;
    private final int t;
    private final int l;
    private final String filename;

    public GreedySearch(int n, int t, int l, String filename) throws IOException {
        this.n = n;
        this.t = t;
        this.l = l;
        this.filename = filename;
        this.getAllKmers();
    }

    public Consensus greedySearch() {
        return generateProfiles();
    }

    public Consensus generateProfiles() {
        final Consensus[] consensus = {new Consensus()};
        Generator.cartesianProduct(kmers).stream().forEach(lmers -> {
            Consensus current = calculateScore(lmers);
            if (current.getScore() > consensus[0].getScore()) consensus[0] = current;
        });
        return consensus[0];
    }

    public Consensus calculateScore(List<String> lmers) {
        Consensus consensus = new Consensus();
        HashMap<String, Integer> bases;
        for (int i = 0; i < l; i++) {
            bases = new HashMap<>();

            for (String lmer : lmers) {
                bases.put(String.valueOf(lmer.charAt(i)),
                        bases.getOrDefault(String.valueOf(lmer.charAt(i)), 0) + 1);
            }

            Map.Entry<String, Integer> entry = bases.entrySet().stream()
                    .max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ? 1 : -1).get();
            consensus.addConsensus(entry.getKey());
            consensus.addScore(entry.getValue());
        }

        return consensus;
    }

    private void getAllKmers() throws IOException {
        List<String> sequences = getSequences(filename, n, t);
        kmers = new ArrayList<>();

        for (String sequence : sequences) kmers.add(getKmers(sequence, l));
    }
}
