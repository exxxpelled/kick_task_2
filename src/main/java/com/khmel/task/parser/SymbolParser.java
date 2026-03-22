package com.khmel.task.parser;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.composite.TextLeaf;

public class SymbolParser extends AbstractParser {
  @Override
  public void parse(String symbol, TextComposite parentComposite) {
    TextLeaf symbolLeaf = new TextLeaf(symbol, TextComponentType.SYMBOL);
    parentComposite.add(symbolLeaf);
  }
}
