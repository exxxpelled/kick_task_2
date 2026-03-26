package com.khmel.task;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.exception.CustomException;

import com.khmel.task.parser.LetterParser;
import com.khmel.task.parser.LexemeParser;
import com.khmel.task.parser.ParagraphParser;
import com.khmel.task.parser.SentenceParser;
import com.khmel.task.parser.SymbolParser;
import com.khmel.task.parser.TextParser;
import com.khmel.task.parser.WordParser;
import com.khmel.task.reader.TextReader;
import com.khmel.task.reader.impl.TextReaderImpl;
import com.khmel.task.service.TextService;
import com.khmel.task.service.impl.TextServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalInt;

public class Main {
  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    TextReader textReader = new TextReaderImpl();
    TextService textService = new TextServiceImpl();

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

      int symbolsAmount = textComposite.countSymbolsWithoutSpaces();
      logger.info("Symbols amount: {}", symbolsAmount);

      logger.info("Find maximum number of sentences with the same word");
      OptionalInt maxSentences = textService.findMaxAmountSentencesWithSimilarWords(textComposite);
      if (maxSentences.isPresent()) {
        logger.info("Result: Maximum number of sentences containing the same word is: {}", maxSentences.getAsInt());
      } else {
        logger.warn("Result: No words found or text is empty");
      }

      logger.info("Display sentences sorted by number of lexemes (ascending)");
      textService.displaySentencesSortedByLexemesAmount(textComposite);

      logger.info("Swap first and last lexemes in each sentence");
      logger.info("Text before swapping:\n{}", textComposite.buildText());

      TextComposite modifiedText = textService.swapFirstAndLastLexemes(textComposite);

      logger.info("Text after swapping:\n{}", modifiedText.buildText());

    } catch (CustomException e) {
      logger.error("Error: {}", e.getMessage());
    }
  }
}
