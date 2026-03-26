package com.khmel.task.service.impl;

import com.khmel.task.composite.TextComponent;
import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.composite.TextLeaf;
import com.khmel.task.service.TextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

public class TextServiceImpl implements TextService {
  private static Logger logger = LogManager.getLogger(TextServiceImpl.class);

  @Override
  public OptionalInt findMaxAmountSentencesWithSimilarWords(TextComposite textComposite) {
    if (textComposite == null || textComposite.getComponentType() != TextComponentType.TEXT) {
      logger.warn("Invalid text composite provided");
      return OptionalInt.empty();
    }

    List<TextComponent> paragraphs = textComposite.getComponents();
    List<TextComposite> sentences = new ArrayList<>();

    for (TextComponent paragraph : paragraphs) {
      if (paragraph.getComponentType() == TextComponentType.PARAGRAPH) {
        TextComposite paragraphComposite = (TextComposite) paragraph;
        for (TextComponent sentence : paragraphComposite.getComponents()) {
          if (sentence.getComponentType() == TextComponentType.SENTENCE) {
            sentences.add((TextComposite) sentence);
          }
        }
      }
    }

    List<Set<String>> sentenceWords = new ArrayList<>();
    for (TextComposite sentence : sentences) {
      Set<String> words = extractWordsFromSentence(sentence);
      sentenceWords.add(words);
    }

    Map<String, Integer> wordFrequency = new HashMap<>();
    for (Set<String> words : sentenceWords) {
      for (String word : words) {
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
      }
    }

    OptionalInt maxFrequency = wordFrequency.values().stream()
            .mapToInt(Integer::intValue)
            .max();

    if (maxFrequency.isPresent() && maxFrequency.getAsInt() > 0) {
      logger.info("Maximum number of sentences with the same word: {}", maxFrequency.getAsInt());
    }

    return maxFrequency;
  }

  @Override
  public void displaySentencesSortedByLexemesAmount(TextComposite textComposite) {
    if (textComposite == null || textComposite.getComponentType() != TextComponentType.TEXT) {
      logger.warn("Invalid text composite provided");
      return;
    }

    List<TextComponent> paragraphs = textComposite.getComponents();
    List<TextComposite> sentences = new ArrayList<>();

    for (TextComponent paragraph : paragraphs) {
      if (paragraph.getComponentType() == TextComponentType.PARAGRAPH) {
        TextComposite paragraphComposite = (TextComposite) paragraph;
        for (TextComponent sentence : paragraphComposite.getComponents()) {
          if (sentence.getComponentType() == TextComponentType.SENTENCE) {
            sentences.add((TextComposite) sentence);
          }
        }
      }
    }

    List<Map.Entry<TextComposite, Integer>> sentencesWithCount = new ArrayList<>();
    for (TextComposite sentence : sentences) {
      int lexemeCount = countLexemesInSentence(sentence);
      sentencesWithCount.add(new AbstractMap.SimpleEntry<>(sentence, lexemeCount));
    }

    sentencesWithCount.sort(Map.Entry.comparingByValue());

    logger.info("Sentences sorted by number of lexemes (ascending):");
    System.out.println("\n=== Sentences sorted by number of lexemes (ascending) ===\n");
    for (Map.Entry<TextComposite, Integer> entry : sentencesWithCount) {
      String sentenceText = entry.getKey().buildText();
      System.out.println("Lexemes count: " + entry.getValue());
      System.out.println("Sentence: " + sentenceText);
      System.out.println();
    }
  }

  @Override
  public TextComposite swapFirstAndLastLexemes(TextComposite textComposite) {
    if (textComposite == null || textComposite.getComponentType() != TextComponentType.TEXT) {
      logger.warn("Invalid text composite provided");
      return textComposite;
    }

    List<TextComponent> paragraphs = textComposite.getComponents();

    for (TextComponent paragraph : paragraphs) {
      if (paragraph.getComponentType() == TextComponentType.PARAGRAPH) {
        TextComposite paragraphComposite = (TextComposite) paragraph;
        List<TextComponent> sentences = paragraphComposite.getComponents();

        for (TextComponent sentenceComponent : sentences) {
          if (sentenceComponent.getComponentType() == TextComponentType.SENTENCE) {
            TextComposite sentence = (TextComposite) sentenceComponent;
            List<TextComponent> lexemes = sentence.getComponents();

            if (lexemes.size() >= 2) {
              TextComponent firstLexeme = lexemes.get(0);
              TextComponent lastLexeme = lexemes.get(lexemes.size() - 1);

              int firstIndex = sentence.getComponents().indexOf(firstLexeme);
              int lastIndex = sentence.getComponents().indexOf(lastLexeme);

              if (firstIndex != -1 && lastIndex != -1) {
                TextComposite firstCopy = copyLexeme((TextComposite) firstLexeme);
                TextComposite lastCopy = copyLexeme((TextComposite) lastLexeme);

                sentence.remove(firstLexeme);
                sentence.remove(lastLexeme);

                List<TextComponent> currentLexemes = sentence.getComponents();
                if (firstIndex < lastIndex) {
                  currentLexemes.add(firstIndex, lastCopy);
                  currentLexemes.add(lastIndex, firstCopy);
                } else {
                  currentLexemes.add(lastIndex, firstCopy);
                  currentLexemes.add(firstIndex, lastCopy);
                }

                sentence.getComponents().clear();
                sentence.getComponents().addAll(currentLexemes);
              }
            }
          }
        }
      }
    }

    logger.info("Swapped first and last lexemes in each sentence");
    return textComposite;
  }

  private Set<String> extractWordsFromSentence(TextComposite sentence) {
    Set<String> words = new HashSet<>();
    List<TextComponent> lexemes = sentence.getComponents();

    for (TextComponent lexeme : lexemes) {
      if (lexeme.getComponentType() == TextComponentType.LEXEME) {
        TextComposite lexemeComposite = (TextComposite) lexeme;
        String word = extractWordFromLexeme(lexemeComposite);
        if (!word.isEmpty()) {
          words.add(word.toLowerCase());
        }
      }
    }

    return words;
  }

  private String extractWordFromLexeme(TextComposite lexeme) {
    StringBuilder word = new StringBuilder();
    List<TextComponent> components = lexeme.getComponents();

    for (TextComponent component : components) {
      if (component.getComponentType() == TextComponentType.WORD) {
        TextComposite wordComposite = (TextComposite) component;
        word.append(extractWordText(wordComposite));
      } else if (component.getComponentType() == TextComponentType.SYMBOL) {
        continue;
      }
    }

    return word.toString();
  }

  private String extractWordText(TextComposite word) {
    StringBuilder wordText = new StringBuilder();
    List<TextComponent> letters = word.getComponents();

    for (TextComponent letter : letters) {
      if (letter.getComponentType() == TextComponentType.LETTER) {
        wordText.append(letter.buildText());
      }
    }

    return wordText.toString();
  }

  private int countLexemesInSentence(TextComposite sentence) {
    int count = 0;
    List<TextComponent> components = sentence.getComponents();

    for (TextComponent component : components) {
      if (component.getComponentType() == TextComponentType.LEXEME) {
        count++;
      }
    }

    return count;
  }

  private TextComposite copyLexeme(TextComposite originalLexeme) {
    TextComposite copy = new TextComposite(TextComponentType.LEXEME);

    for (TextComponent component : originalLexeme.getComponents()) {
      if (component.getComponentType() == TextComponentType.WORD) {
        TextComposite originalWord = (TextComposite) component;
        TextComposite wordCopy = new TextComposite(TextComponentType.WORD);

        for (TextComponent letter : originalWord.getComponents()) {
          if (letter.getComponentType() == TextComponentType.LETTER) {
            TextLeaf letterCopy = new TextLeaf(
                    letter.buildText().charAt(0),
                    TextComponentType.LETTER
            );
            wordCopy.add(letterCopy);
          }
        }
        copy.add(wordCopy);
      } else if (component.getComponentType() == TextComponentType.SYMBOL) {
        TextLeaf symbolCopy = new TextLeaf(
                component.buildText().charAt(0),
                TextComponentType.SYMBOL
        );
        copy.add(symbolCopy);
      }
    }

    return copy;
  }
}
