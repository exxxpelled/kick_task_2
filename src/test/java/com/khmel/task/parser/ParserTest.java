package com.khmel.task.parser;

import com.khmel.task.composite.TextComponent;
import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
  @Test
  void wordParser() {
    LetterParser letterParser = new LetterParser();
    WordParser wordParser = new WordParser(letterParser);
    TextComposite wordComposite = new TextComposite(TextComponentType.WORD);
    String word = "World";
    wordParser.parse(word, wordComposite);
    List<TextComponent> wordComponents = wordComposite.getComponents();

    assertEquals(5, wordComponents.size());

    assertEquals(TextComponentType.LETTER, wordComponents.get(0).getComponentType());
    assertEquals("W", wordComponents.get(0).buildText());

    assertEquals(TextComponentType.LETTER, wordComponents.get(1).getComponentType());
    assertEquals("o", wordComponents.get(1).buildText());

    assertEquals(TextComponentType.LETTER, wordComponents.get(2).getComponentType());
    assertEquals("r", wordComponents.get(2).buildText());

    assertEquals(TextComponentType.LETTER, wordComponents.get(3).getComponentType());
    assertEquals("l", wordComponents.get(3).buildText());

    assertEquals(TextComponentType.LETTER, wordComponents.get(4).getComponentType());
    assertEquals("d", wordComponents.get(4).buildText());
  }

  @Test
  void lexemeParser() {
    SymbolParser symbolParser = new SymbolParser();
    LetterParser letterParser = new LetterParser();
    WordParser wordParser = new WordParser(letterParser);
    LexemeParser lexemeParser = new LexemeParser(wordParser, symbolParser);
    TextComposite lexemeComposite = new TextComposite(TextComponentType.LEXEME);
    String lexeme = "('Hello-world').";
    lexemeParser.parse(lexeme, lexemeComposite);
    List<TextComponent> lexemeComponents = lexemeComposite.getComponents();

    assertEquals(8, lexemeComponents.size());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(0).getComponentType());
    assertEquals("(", lexemeComponents.get(0).buildText());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(1).getComponentType());
    assertEquals("'", lexemeComponents.get(1).buildText());

    assertEquals(TextComponentType.WORD, lexemeComponents.get(2).getComponentType());
    assertEquals("Hello", lexemeComponents.get(2).buildText());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(3).getComponentType());
    assertEquals("-", lexemeComponents.get(3).buildText());

    assertEquals(TextComponentType.WORD, lexemeComponents.get(4).getComponentType());
    assertEquals("world", lexemeComponents.get(4).buildText());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(5).getComponentType());
    assertEquals("'", lexemeComponents.get(5).buildText());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(6).getComponentType());
    assertEquals(")", lexemeComponents.get(6).buildText());

    assertEquals(TextComponentType.SYMBOL, lexemeComponents.get(7).getComponentType());
    assertEquals(".", lexemeComponents.get(7).buildText());
  }

  @Test
  void sentenceParser() {
    SymbolParser symbolParser = new SymbolParser();
    LetterParser letterParser = new LetterParser();
    WordParser wordParser = new WordParser(letterParser);
    LexemeParser lexemeParser = new LexemeParser(wordParser, symbolParser);
    SentenceParser sentenceParser = new SentenceParser(lexemeParser);
    TextComposite sentenceComposite = new TextComposite(TextComponentType.SENTENCE);
    String sentence = "'Hello' world (abc).";
    sentenceParser.parse(sentence, sentenceComposite);
    List<TextComponent> sentenceComponents = sentenceComposite.getComponents();

    assertEquals(3, sentenceComponents.size());

    assertEquals(TextComponentType.LEXEME, sentenceComponents.get(0).getComponentType());
    assertEquals("'Hello'", sentenceComponents.get(0).buildText());

    assertEquals(TextComponentType.LEXEME, sentenceComponents.get(1).getComponentType());
    assertEquals("world", sentenceComponents.get(1).buildText());

    assertEquals(TextComponentType.LEXEME, sentenceComponents.get(2).getComponentType());
    assertEquals("(abc).", sentenceComponents.get(2).buildText());
  }
}

