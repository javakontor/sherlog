package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

public class ColorFilter extends AbstractLogEventFilter {

  private final List<Color> _colors;

  public ColorFilter(Color initialColor) {
    this._colors = new LinkedList<Color>();
    this._colors.add(initialColor);
  }

  public boolean matches(LogEvent event) {
    if (!hasColors()) {
      return true;
    }
    Color color = (Color) event.getUserDefinedField("COLOR");

    if ((color != null) && this._colors.contains(color)) {
      return true;
    }
    return false;
  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }

  @Override
  protected void onRestoreFromMemento(LogEventFilterMemento memento) {
    // TODO Auto-generated method stub
  }

  public void addColor(Color color) {
    if (!this._colors.contains(color)) {
      this._colors.add(color);
      fireFilterChange();
    }
  }

  public void removeColor(Color color) {
    this._colors.remove(color);
    fireFilterChange();
  }

  public boolean hasColor(Color color) {
    return this._colors.contains(color);
  }

  public boolean hasColors() {
    return !this._colors.isEmpty();
  }

}
