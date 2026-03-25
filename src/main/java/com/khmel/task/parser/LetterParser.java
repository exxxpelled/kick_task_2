package com.khmel.task.parser;

import com.khmel.task.composite.TextComponentType;
import com.khmel.task.composite.TextComposite;
import com.khmel.task.composite.TextLeaf;

public class LetterParser extends AbstractParser{
  @Override
  public void parse(String letter, TextComposite parentComposite) {
    TextLeaf letterLeaf= new TextLeaf(letter.charAt(0), TextComponentType.LETTER);
    parentComposite.add(letterLeaf);
  }
}
