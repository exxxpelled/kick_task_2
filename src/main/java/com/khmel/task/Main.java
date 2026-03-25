package com.khmel.task;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.exception.CustomException;
import com.khmel.task.parser.*;
import com.khmel.task.reader.TextReader;
import com.khmel.task.reader.impl.TextReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    TextReader textReader = new TextReaderImpl();
    try {
      String text = textReader.readText();
      logger.info("Original text:\n{}", text);

      LetterParser letterParser = new LetterParser();
      SymbolParser symbolParser = new SymbolParser();
      WordParser wordParser = new WordParser(letterParser);
      LexemeParser lexemeParser = new LexemeParser(wordParser, symbolParser);
      SentenceParser sentenceParser = new SentenceParser(lexemeParser);
      ParagraphParser paragraphParser = new ParagraphParser(sentenceParser);
      TextParser textParser = new TextParser(paragraphParser);

      TextComposite textComposite = new TextComposite(TextComponentType.TEXT);
      textParser.parse(text, textComposite);

      String parsedText = textComposite.buildText();
      logger.info("Parsed text:\n{}", parsedText);

      int lettersAmount = textComposite.countLetters();
      logger.info("Letters amount: {}", lettersAmount);

      int symbolsAmount = textComposite.countAllSymbols();
      logger.info("Symbols amount: {}", symbolsAmount);
    } catch (CustomException e) {
      logger.error("Error: {}", e.getMessage());
    }
  }
}
