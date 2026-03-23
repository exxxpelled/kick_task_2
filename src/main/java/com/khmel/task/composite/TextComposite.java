package com.khmel.task.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite extends TextComponent {
  private static final String SPACE = " ";
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
    TextComponent previousComponent = null;

    for (TextComponent component : components) {
      if (component.getComponentType() == TextComponentType.PARAGRAPH) {
        if (previousComponent != null) {
          text.append("\n");
        }
        text.append(SPACE.repeat(4));
      } else if (component.getComponentType() == TextComponentType.SENTENCE) {
        if (previousComponent != null &&
                previousComponent.getComponentType() != TextComponentType.PARAGRAPH) {
          text.append(SPACE);
        }
      } else if (component.getComponentType() == TextComponentType.LEXEME) {
        if (previousComponent != null &&
                (previousComponent.getComponentType() == TextComponentType.LEXEME ||
                        previousComponent.getComponentType() == TextComponentType.WORD)) {
          text.append(SPACE);
        }
      }

      text.append(component.buildText());
      previousComponent = component;
    }
    return text.toString();
  }
}
