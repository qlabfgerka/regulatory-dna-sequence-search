package inc.premzl.DNA.SequenceSearch;

import inc.premzl.Models.Consensus;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedySearch {
    public static List<String> getKmers(String sequence, int length) {
        List<String> kmers = new ArrayList<>();
        for (int i = 0; i < sequence.length() - length + 1; i++) {
            kmers.add(sequence.substring(i, i + length));
        }
        return kmers;
    }

    public static Consensus generateProfiles(List<List<String>> kmers, int l) {
        final Consensus[] consensus = {new Consensus()};
        Generator.cartesianProduct(kmers).stream().forEach(lmers -> {
            Consensus current = calculateScore(lmers, l);
            if (current.getScore() > consensus[0].getScore()) consensus[0] = current;
        });
        return consensus[0];
    }

    public static Consensus calculateScore(List<String> lmers, int l) {
        Consensus consensus = new Consensus();
        HashMap<String, Integer> bases;
        for (int i = 0; i < l; i++) {
            bases = new HashMap<>();
            
            for (String lmer : lmers) {
                bases.put(String.valueOf(lmer.charAt(i)),
                        bases.getOrDefault(String.valueOf(lmer.charAt(i)), 0) + 1);
            }

            Map.Entry<String, Integer> entry = bases.entrySet().stream().max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ? 1 : -1).get();
            consensus.addConsensus(entry.getKey());
            consensus.addScore(entry.getValue());
        }

        return consensus;
    }
}
