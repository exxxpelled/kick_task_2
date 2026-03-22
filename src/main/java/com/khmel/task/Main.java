package com.khmel.task;

import com.khmel.task.exception.CustomException;
import com.khmel.task.parser.AbstractParser;
import com.khmel.task.parser.TextParser;
import com.khmel.task.reader.TextReader;
import com.khmel.task.reader.impl.TextReaderImpl;

public class Main {
  public static void main(String[] args) {
    TextReader textReader = new TextReaderImpl();
    try {
      String text = textReader.readText();
      System.out.println(text);
    } catch (CustomException e) {
      System.out.println(e.getMessage());
    }
  }
}
