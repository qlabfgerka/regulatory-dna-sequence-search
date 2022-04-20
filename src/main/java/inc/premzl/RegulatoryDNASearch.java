package inc.premzl;

import inc.premzl.Models.Consensus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static inc.premzl.DNA.DNAOperations.getKmers;
import static inc.premzl.DNA.DNAOperations.splitSequence;
import static inc.premzl.DNA.SequenceSearch.BranchAndBoundSearch.generateConsensuses;
import static inc.premzl.DNA.SequenceSearch.GreedySearch.generateProfiles;
import static inc.premzl.Files.FileOperations.readFile;

public class RegulatoryDNASearch {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");

        List<String> sequences = splitSequence(readFile(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        List<List<String>> kmers = new ArrayList<>();
        for (String sequence : sequences) kmers.add(getKmers(sequence, Integer.parseInt(args[4])));

        if (Objects.equals(args[0], "greedy")) {
            Consensus consensus = generateProfiles(kmers, Integer.parseInt(args[4]));
            System.out.println(consensus.getConsensus() + " " + consensus.getScore());
        } else if (Objects.equals(args[0], "bnb")) {
            Consensus consensus = new Consensus(-1);
            generateConsensuses("", Integer.parseInt(args[4]), 0, consensus, sequences);

            System.out.println(consensus.getConsensus() + " " + consensus.getHamming());
        } else throw new Exception("Invalid algorithm argument");
    }
}
