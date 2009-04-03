package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

public class ColorFilter extends AbstractLogEventFilter {

  private final List<Color> _colors;

  public ColorFilter() {
    this._colors = new LinkedList<Color>();
  }

  public boolean matches(final LogEvent event) {
    if (!hasColors()) {
      return true;
    }
    final Color color = (Color) event.getUserDefinedField("COLOR");

    if ((color != null) && this._colors.contains(color)) {
      return true;
    }
    return false;
  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }

  @Override
  protected void onRestoreFromMemento(final LogEventFilterMemento memento) {
    // TODO Auto-generated method stub
  }

  public void addColor(final Color color) {
    if (!this._colors.contains(color)) {
      this._colors.add(color);
      fireFilterChange();
    }
  }

  public void removeColor(final Color color) {
    this._colors.remove(color);
    fireFilterChange();
  }

  public boolean hasColor(final Color color) {
    return this._colors.contains(color);
  }

  public boolean hasColors() {
    return !this._colors.isEmpty();
  }

}
