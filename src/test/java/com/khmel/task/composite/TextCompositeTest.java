package com.khmel.task.composite;

import com.khmel.task.parser.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextCompositeTest {
  TextComposite textComposite = new TextComposite(TextComponentType.TEXT);

  @Test
  void countLettersAndSymbolsTest() {
    SymbolParser symbolParser = new SymbolParser();
    LetterParser letterParser = new LetterParser();
    WordParser wordParser = new WordParser(letterParser);
    LexemeParser lexemeParser = new LexemeParser(wordParser, symbolParser);
    SentenceParser sentenceParser = new SentenceParser(lexemeParser);
    ParagraphParser paragraphParser = new ParagraphParser(sentenceParser);
    TextComposite paragraphComposite = new TextComposite(TextComponentType.PARAGRAPH);
    String paragraph = "'Hello' world (abc). Java Innowise!\n    1234?";
    paragraphParser.parse(paragraph, paragraphComposite);
    int countLetters = paragraphComposite.countLetters();
    int countSymbols = paragraphComposite.countSymbolsWithoutSpaces();

    assertEquals(29, countLetters);
    assertEquals(36, countSymbols);
  }

}