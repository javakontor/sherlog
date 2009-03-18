package org.javakontor.sherlog.ui.logview.decorator;

import java.awt.Component;

import org.javakontor.sherlog.domain.LogEvent;

public class LogEventTableCellDecoratorContext {

  // MOVE?
  public static enum Attribute {
    time, logLevel, trace, category, message
  };

  private final Component _component;

  private final LogEvent  _logEvent;

  private final Attribute _attribute;

  private final Object    _value;

  private final boolean   _focus;

  private final boolean   _selected;

  public LogEventTableCellDecoratorContext(Component component, LogEvent logEvent, Attribute attribute, Object value,
      boolean focus, boolean selected) {
    super();

    this._component = component;
    this._logEvent = logEvent;
    this._attribute = attribute;
    this._value = value;
    this._focus = focus;
    this._selected = selected;
  }

  public Component getComponent() {
    return this._component;
  }

  public LogEvent getLogEvent() {
    return this._logEvent;
  }

  public Attribute getAttribute() {
    return this._attribute;
  }

  public Object getValue() {
    return this._value;
  }

  public boolean hasFocus() {
    return this._focus;
  }

  public boolean isSelected() {
    return this._selected;
  }

}
