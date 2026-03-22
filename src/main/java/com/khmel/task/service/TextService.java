package com.khmel.task.service;

import java.util.OptionalInt;

public interface TextService {
  OptionalInt findMaxAmountSentencesWithSimilarWords();
  void displaySentencesSortedByLexemesAmount();

}
