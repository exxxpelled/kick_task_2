package com.khmel.task.parser;

import com.khmel.task.composite.TextComposite;

public abstract class AbstractParser {
  protected AbstractParser nextParser;

  public abstract void parse(String text, TextComposite parentComposite);
}
