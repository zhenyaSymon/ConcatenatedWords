import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Zhenya on 15.03.2017.
 */
public class CounterKernel { public static void main(String[] args) {
    getResultOfConcatenating("words.txt");
}

    public static ArrayList<String> findAllConcatenatedWords(List<String> words) {
        ArrayList<String> list = new ArrayList();
        Set<String> set = new HashSet(words);

        for (String word : words) {
            set.remove(word);
            if (findConcatenatedParts( set,"",word)) {
                list.add(word);
            }
            set.add(word);
        }
        return list;
    }

    public static boolean findConcatenatedParts(Set<String> set, String last,String word) { //check for existing concatenated parts
        if (!last.equals("")){
            set.add(last);
        }
        if (set.contains(word)) {
            return true;
        }
        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            if (set.contains(prefix) &&
                    findConcatenatedParts( set, last + prefix,word.substring(i, word.length()))) {
                return true;
            }
        }
        return false;
    }

    public static List getWordsFromSource(String source) { //getting words from file
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(source), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static void getResultOfConcatenating(String source) { //finding the longest words and printing results
        ArrayList<String> finallyConcatenated = findAllConcatenatedWords(getWordsFromSource(source));
        int quantity = finallyConcatenated.size();
        String maxElement = Collections.max(finallyConcatenated, Comparator.comparing(s -> s.length()));
        System.out.println("The longest concatenated word: " + maxElement);
        finallyConcatenated.remove(maxElement);
        System.out.println("The second longest concatenated word: " + Collections.max(finallyConcatenated, Comparator.comparing(s -> s.length())));
        System.out.println("The total count of concatenated words in the file: " + quantity);

    }
}