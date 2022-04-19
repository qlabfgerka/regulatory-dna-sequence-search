package inc.premzl;

import java.util.List;
import java.util.Objects;

import static inc.premzl.DNA.DNAOperations.splitSequence;
import static inc.premzl.Files.FileOperations.readFile;

public class RegulatoryDNASearch {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");

        if(Objects.equals(args[0], "greedy")) {
            List<String> sequences = splitSequence(readFile(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

            for(String sequence: sequences) {
                System.out.println(sequence);
            }
        } else throw new Exception("Invalid algorithm argument");
    }
}
