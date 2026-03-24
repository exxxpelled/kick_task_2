package com.khmel.task.service;

import com.khmel.task.composite.TextComposite;

import java.util.OptionalInt;

public interface TextService {
  OptionalInt findMaxAmountSentencesWithSimilarWords(TextComposite textComposite);

  void displaySentencesSortedByLexemesAmount(TextComposite textComposite);

  TextComposite swapLexemes(TextComposite textComposite);
}
