package org.javakontor.sherlog.core.impl.internal.store;

import org.javakontor.sherlog.core.filter.LogEventFilterMemento;
import org.javakontor.sherlog.core.impl.filter.AbstractLogEventFilter;

abstract class AbstractTestLogEventFilter extends AbstractLogEventFilter {

  @Override
  protected void onRestoreFromMemento(LogEventFilterMemento memento) {

  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }

}