package com.khmel.task.service;

import com.khmel.task.composite.TextComposite;

import java.util.OptionalInt;

public interface TextService {
  OptionalInt findMaxAmountSentencesWithSimilarWords();

  void displaySentencesSortedByLexemesAmount();

  TextComposite swapLexemes();
}
