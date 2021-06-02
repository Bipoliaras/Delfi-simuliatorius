package simulator.markov;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChildWords {

    private Map<String, Integer> words;
    private int count;

    public ChildWords() {
        this.words = new HashMap<>();
        this.count = 0;
    }

    public ChildWords(String word) {
        this();
        addWord(word);
    }

    public void addWord(String word) {
        this.words.put(word, 1);
        this.count++;
    }

    public void mergeWord(String word) {
        this.words.merge(word, 1, Integer::sum);
        this.count++;
    }

    public String getRandomWord(Random random) {
        int number = random.nextInt(count);

        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            if (number < entry.getValue()) {
                return entry.getKey();
            }

            number -= entry.getValue();
        }

        return "";
    }
}
