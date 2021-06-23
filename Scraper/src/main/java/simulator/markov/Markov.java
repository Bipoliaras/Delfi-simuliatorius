package simulator.markov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Markov {

  private static final String START_PART = "[->";
  private static final String END_PART = "->]";
  private final Random random = new Random();

  private final Pattern pattern = Pattern.compile("[a-zA-ZąĄčČęĘėĖįĮšŠųŲūŪžŽ]+|[^\\w\\s]|");

  private Map<String, ChildWords> wordPoll = new HashMap<>();


  public void parseText(String text) {
    List<String> sentenceParts = getPartsFromComment(text).stream().filter(string ->
        !string.isBlank() && !string.isEmpty()).collect(Collectors.toList());
    addFirstAndLastPartToList(sentenceParts);
    updateWordSequenceMap(sentenceParts.toArray(new String[0]));
  }

  public String generate() {
    return generateRandomText(wordPoll);
  }

  private List<String> getPartsFromComment(String comment) {
    return pattern
        .matcher(comment)
        .results()
        .map(MatchResult::group)
        .collect(Collectors.toList());
  }

  private void addFirstAndLastPartToList(List<String> sentenceParts) {
    sentenceParts.add(0, START_PART);
    sentenceParts.add(END_PART);
  }

  private void updateWordSequenceMap(String[] sentenceParts) {
    for (int i = 0; i < sentenceParts.length - 1; i++) {
      String parentWord = sentenceParts[i];
      String childWord = sentenceParts[i + 1];

      Optional.ofNullable(wordPoll.get(parentWord))
          .ifPresentOrElse(
              words -> words.mergeWord(childWord),
              () -> wordPoll.put(parentWord, new ChildWords(childWord))
          );
    }
  }

  private String generateRandomText(Map<String, ChildWords> wordPool) {
    var text = new StringBuilder();
    String parentWord = wordPool.get(START_PART).getRandomWord(random);

    while (!parentWord.equals(END_PART)) {
      text.append(parentWord);
      text.append(" ");
      parentWord = wordPool.get(parentWord).getRandomWord(random);
    }

    return text.toString();
  }
}
