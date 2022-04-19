package inc.premzl;

import inc.premzl.Models.Consensus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static inc.premzl.DNA.DNAOperations.splitSequence;
import static inc.premzl.DNA.SequenceSearch.GreedySearch.generateProfiles;
import static inc.premzl.DNA.SequenceSearch.GreedySearch.getKmers;
import static inc.premzl.Files.FileOperations.readFile;

public class RegulatoryDNASearch {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");

        if (Objects.equals(args[0], "greedy")) {
            List<String> sequences = splitSequence(readFile(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            List<List<String>> kmers = new ArrayList<>();

            for (String sequence : sequences) kmers.add(getKmers(sequence, Integer.parseInt(args[4])));

            Consensus consensus = generateProfiles(kmers, Integer.parseInt(args[4]));
            System.out.println(consensus.getConsensus() + " " + consensus.getScore());
        } else throw new Exception("Invalid algorithm argument");
    }
}
