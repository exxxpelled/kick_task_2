package com.khmel.task.reader.impl;

import com.khmel.task.exception.CustomException;
import com.khmel.task.reader.TextReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TextReaderImpl implements TextReader {
  private static final String FILE_PATH = "data/text.txt";

  @Override
  public String readText() throws CustomException {
    Path path = Paths.get(FILE_PATH);
    if (!Files.exists(path)) {
      throw new CustomException("File does not exists");
    }
    try {
      List<String> strings = Files.readAllLines(path);
      String result = String.join("\n",strings);
      return result;
    } catch (IOException exception) {
      throw new CustomException("Failed to read file");
    }
  }
}
