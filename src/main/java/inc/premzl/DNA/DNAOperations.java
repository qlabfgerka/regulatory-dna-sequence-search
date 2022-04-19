package inc.premzl.DNA;

import java.util.List;
import java.util.stream.Stream;

public class DNAOperations {
    public static List<String> splitSequence(String dna, int size, int amount) {
        return Stream.of(dna.split(String.format("(?<=\\G.{%d})", size))).limit(amount).toList();
    }
}
