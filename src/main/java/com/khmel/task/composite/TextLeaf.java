package com.khmel.task.composite;

public class TextLeaf extends TextComponent {
  private String text;

  public TextLeaf(String text, TextComponentType componentType) {
    this.text = text;
    this.componentType = componentType;
  }

  @Override
  public String buildText() {
    return text;
  }
}
