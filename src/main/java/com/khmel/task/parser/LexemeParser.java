package com.khmel.task.parser;

import com.khmel.task.composite.TextComposite;

public class LexemeParser extends AbstractParser {
  private static final String WORD_REGEX = "";

  public LexemeParser(AbstractParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(String lexeme, TextComposite parentComposite) {

  }
}
