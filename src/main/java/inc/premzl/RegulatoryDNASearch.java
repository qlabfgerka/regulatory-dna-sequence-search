package inc.premzl;

import inc.premzl.DNA.SequenceSearch.BranchAndBoundSearch;
import inc.premzl.DNA.SequenceSearch.GreedySearch;
import inc.premzl.Models.Consensus;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegulatoryDNASearch {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");
        String filename = args[1];
        int n = Integer.parseInt(args[2]), t = Integer.parseInt(args[3]), l = Integer.parseInt(args[4]);

        if (Objects.equals(args[0], "greedy")) {
            GreedySearch greedySearch = new GreedySearch(n, t, l, filename);
            Consensus consensus = greedySearch.greedySearch();

            System.out.println(consensus.getConsensus() + " " + consensus.getScore());
        } else if (Objects.equals(args[0], "bnb")) {
            BranchAndBoundSearch bnbSearch = new BranchAndBoundSearch(n, t, l, filename);
            Consensus consensus = bnbSearch.branchAndBoundSearch();

            System.out.println(consensus.getConsensus() + " " + consensus.getHamming());
        } else if (Objects.equals(args[0], "test")) {
            BranchAndBoundSearch bnbSearch = new BranchAndBoundSearch(n, t, l, filename);
            GreedySearch greedySearch = new GreedySearch(n, t, l, filename);
            Consensus consensus = new Consensus();

            long avgTime, start, end;
            int iterations = 100;

            for (int opt = 0; opt < 2; opt++) {
                avgTime = 0;
                for (int i = 0; i < iterations; i++) {
                    start = System.nanoTime();
                    if (opt == 0) consensus = greedySearch.greedySearch();
                    else consensus = bnbSearch.branchAndBoundSearch();
                    end = System.nanoTime();
                    avgTime += (end - start);
                }
                System.out.println((double) TimeUnit.MILLISECONDS.convert(avgTime, TimeUnit.NANOSECONDS) / iterations);
                System.out.printf("N: %d\tT: %d\tL: %d\tALGORITHM: %s\tCONSENSUS: %s\t%s: %d\n",
                        n, t, l,
                        opt == 0 ? "GREEDY" : "BRANCH AND BOUND",
                        consensus.getConsensus(),
                        opt == 0 ? "SCORE" : "HAMMING",
                        opt == 0 ? consensus.getScore() : consensus.getHamming());
            }
        } else throw new Exception("Invalid algorithm argument");
    }
}
