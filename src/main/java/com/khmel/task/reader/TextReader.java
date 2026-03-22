package com.khmel.task.reader;

import com.khmel.task.exception.CustomException;

public interface TextReader {
  String readText() throws CustomException;
}
