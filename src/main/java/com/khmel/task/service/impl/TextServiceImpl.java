package com.khmel.task.service.impl;

import com.khmel.task.composite.TextComposite;
import com.khmel.task.service.TextService;

import java.util.OptionalInt;

public class TextServiceImpl implements TextService {
  @Override
  public OptionalInt findMaxAmountSentencesWithSimilarWords() {
    return OptionalInt.empty();
  }

  @Override
  public void displaySentencesSortedByLexemesAmount() {

  }

  @Override
  public TextComposite swapLexemes() {
    return null;
  }
}
