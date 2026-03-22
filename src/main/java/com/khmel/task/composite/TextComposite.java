package com.khmel.task.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite extends TextComponent {
  private List<TextComponent> components = new ArrayList<>();

  public TextComposite(TextComponentType componentType) {
    this.componentType = componentType;
  }

  public boolean add(TextComponent component) {
    return components.add(component);
  }
  
  public boolean remove(TextComponent component) {
    return components.remove(component);
  }

  @Override
  public String buildText() {
    StringBuilder text = new StringBuilder();
    for (TextComponent component : components) {
      if (component.getComponentType() == TextComponentType.PARAGRAPH) {
        text.append("%n%t");
      }
      text.append(component);
    }
    return text.toString();
  }
}
