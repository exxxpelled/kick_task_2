package com.khmel.task.service.impl;

import com.khmel.task.composite.TextComponent;
import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.service.TextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.LinkedHashMap;

public class TextServiceImpl implements TextService {
  private static Logger logger = LogManager.getLogger(TextServiceImpl.class);

  @Override
  public OptionalInt findMaxAmountSentencesWithSimilarWords(TextComposite textComposite) {
    return OptionalInt.empty();
  }

  @Override
  public void displaySentencesSortedByLexemesAmount(TextComposite textComposite) {
    if (textComposite == null) {
      logger.warn("Text composite is null, cannot display sentences sorted by lexemes amount");
      return;
    }

    List<TextComponent> sentences = extractSentences(textComposite);

    if (sentences.isEmpty()) {
      logger.info("No sentences found in text");
      return;
    }

    logger.info("Displaying sentences sorted by lexemes amount (total: {} sentences)", sentences.size());

    Map<String, Integer> sentenceLexemeCount = new LinkedHashMap<>();

    for (TextComponent sentence : sentences) {
      String sentenceText = sentence.buildText();
      int lexemeCount = countLexemes(sentence);
      sentenceLexemeCount.put(sentenceText, lexemeCount);
    }

    List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(sentenceLexemeCount.entrySet());
    sortedEntries.sort(Map.Entry.comparingByValue());

    int position = 1;
    for (Map.Entry<String, Integer> entry : sortedEntries) {
      logger.info("{}. (lexemes: {}) {}", position, entry.getValue(), entry.getKey());
      position++;
    }

    logger.info("Finished displaying {} sentences", sortedEntries.size());
  }

  @Override
  public TextComposite swapLexemes(TextComposite textComposite) {
    return null;
  }

  private List<TextComponent> extractSentences(TextComposite composite) {
    List<TextComponent> sentences = new ArrayList<>();

    if (composite.getComponentType() == TextComponentType.SENTENCE) {
      sentences.add(composite);
    } else {
      for (TextComponent component : composite.getComponents()) {
        if (component instanceof TextComposite) {
          sentences.addAll(extractSentences((TextComposite) component));
        }
      }
    }

    return sentences;
  }

  private int countLexemes(TextComponent component) {
    int count = 0;

    if (component instanceof TextComposite composite) {
      for (TextComponent child : composite.getComponents()) {
        if (child.getComponentType() == TextComponentType.LEXEME) {
          count++;
        } else if (child instanceof TextComposite) {
          count += countLexemes(child);
        }
      }
    }

    return count;
  }
}
