package com.khmel.task.parser;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.composite.TextLeaf;

public class SymbolParser extends AbstractParser {
  @Override
  public void parse(String symbols, TextComposite parentComposite) {
    for (char c : symbols.toCharArray()) {
      TextLeaf symbolLeaf = new TextLeaf(c, TextComponentType.SYMBOL);
      parentComposite.add(symbolLeaf);
    }
  }
}
