package org.javakontor.sherlog.domain.impl.internal.store;

import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

abstract class AbstractTestLogEventFilter extends AbstractLogEventFilter {

  @Override
  protected void onRestoreFromMemento(LogEventFilterMemento memento) {

  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }

}