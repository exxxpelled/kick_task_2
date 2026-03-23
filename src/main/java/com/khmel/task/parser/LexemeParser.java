package com.khmel.task.parser;

import com.khmel.task.composite.TextComposite;

public class LexemeParser extends AbstractParser {
  private static final String WORD_DELIMITER = "(?<=\\w)(?=\\p{Punct})|(?<=\\p{Punct})(?=\\w)";
  private static final String WORD_REGEX = "[\\wа-яА-ЯёЁ]+";

  public LexemeParser(AbstractParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(String lexeme, TextComposite parentComposite) {
    String[] parts = lexeme.split(WORD_DELIMITER);

    if (parts.length == 1) {
      if (lexeme.matches(WORD_REGEX)) {
        nextParser.parse(lexeme, parentComposite);
      } else {
        if (nextParser != null) {
          nextParser.parse(lexeme, parentComposite);
        }
      }
    } else {
      for (String part : parts) {
        if (part.matches(WORD_REGEX)) {
          nextParser.parse(part, parentComposite);
        } else {
          if (nextParser != null) {
            nextParser.parse(part, parentComposite);
          }
        }
      }
    }
  }
}
