package inc.premzl.DNA.SequenceSearch;

import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> generateConsensuses(int l) {
        return Generator
                .permutation("T", "C", "A", "G").withRepetitions(l)
                .stream()
                .map(nmer -> String.join("", nmer))
                .toList();
    }

    public static void getBestConsensus(List<List<String>> kmers, List<String> consensuses) {
        int index, min, hamming;
        List<Integer> currentHammings;
        List<List<Integer>> hammings = new ArrayList<>();

        for (String consensus : consensuses) {
            System.out.print("CONSENSUS: " + consensus + " ");
            index = -1;
            min = consensus.length() + 1;
            currentHammings = new ArrayList<>();
            for (List<String> kmer : kmers) {
                for (int i = 0; i < kmer.size(); i++) {
                    hamming = getHamming(kmer.get(i), consensus);
                    System.out.print(kmer.get(i) + " (" + hamming + "," + min + ")\t");
                    if (hamming < min) {
                        min = hamming;
                        index = i;
                    }
                }
                currentHammings.add(index);
                System.out.print(index + " " + kmer.get(index) + "\t\t");
            }
            hammings.add(currentHammings);
            System.out.println();
        }
        System.out.println(hammings);

        min = Integer.MAX_VALUE;
        for (int i = 0; i < hammings.size(); i++) {
            if (hammings.get(i).get(0) + hammings.get(i).get(1) < min)
                min = hammings.get(i).get(0) + hammings.get(i).get(1);
        }

        System.out.println(min);
    }
}
