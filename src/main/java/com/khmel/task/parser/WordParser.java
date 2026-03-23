package com.khmel.task.parser;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.composite.TextLeaf;

public class WordParser extends AbstractParser {
  @Override
  public void parse(String word, TextComposite parentComposite) {
    TextLeaf wordLeaf = new TextLeaf(word, TextComponentType.WORD);
    parentComposite.add(wordLeaf);
  }
}
