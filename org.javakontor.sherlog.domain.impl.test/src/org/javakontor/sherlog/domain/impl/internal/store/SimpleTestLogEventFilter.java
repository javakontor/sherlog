package org.javakontor.sherlog.domain.impl.internal.store;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

class SimpleTestLogEventFilter extends AbstractLogEventFilter {
  private boolean _shouldMatch = false;

  public boolean matches(LogEvent event) {
    return _shouldMatch;
  }

  public boolean isShouldMatch() {
    return _shouldMatch;
  }

  public void setShouldMatch(boolean shouldMatch) {
    _shouldMatch = shouldMatch;
    fireFilterChange();
  }

  @Override
  protected void onRestoreFromMemento(LogEventFilterMemento memento) {

  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }
}