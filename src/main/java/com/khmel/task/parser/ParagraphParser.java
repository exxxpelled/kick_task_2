package com.khmel.task.parser;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;

public class ParagraphParser extends AbstractParser {
  private static final String SENTENCE_DELIMITER = "(?<=[.!?])\\s+";

  public ParagraphParser(AbstractParser nextParser) {
    this.nextParser = nextParser;
  }

  @Override
  public void parse(String paragraph, TextComposite parentComposite) {
    String[] sentences = paragraph.split(SENTENCE_DELIMITER);
    for (String sentence : sentences) {
      TextComposite sentenceComposite = new TextComposite(TextComponentType.SENTENCE);
      parentComposite.add(sentenceComposite);
      nextParser.parse(sentence, sentenceComposite);
    }
  }
}
