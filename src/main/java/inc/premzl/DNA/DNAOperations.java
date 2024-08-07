package inc.premzl.DNA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static inc.premzl.Files.FileOperations.readFile;

public class DNAOperations {
    public static List<String> splitSequence(String dna, int size, int amount) {
        return Stream.of(dna.split(String.format("(?<=\\G.{%d})", size))).limit(amount).toList();
    }

    public static List<String> getKmers(String sequence, int length) {
        List<String> kmers = new ArrayList<>();
        for (int i = 0; i < sequence.length() - length + 1; i++) {
            kmers.add(sequence.substring(i, i + length));
        }
        return kmers;
    }

    public static List<String> getSequences(String filename, int n, int t) throws IOException {
        return splitSequence(readFile(filename), n, t);
    }
}
