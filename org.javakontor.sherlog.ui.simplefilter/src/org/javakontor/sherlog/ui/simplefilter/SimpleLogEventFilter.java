package org.javakontor.sherlog.ui.simplefilter;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

public class SimpleLogEventFilter extends AbstractLogEventFilter {

  private SimpleLogEventFilterMemento _config;

  public SimpleLogEventFilter() {
    this._config = new SimpleLogEventFilterMemento(null, null, null, null);
  }

  public SimpleLogEventFilter(SimpleLogEventFilterMemento configuration) {
    this._config = configuration;
  }

  @Override
  protected void onRestoreFromMemento(LogEventFilterMemento memento) {
    // TODO copy memento
    this._config = (SimpleLogEventFilterMemento) memento;
  }

  public boolean matches(LogEvent event) {

    if (this._config.isLogLevelSet() && !event.getLogLevel().isGreaterOrEqual(this._config.getLogLevel())) {
      return false;
    }

    if (this._config.isThreadSet() && !this._config.getThread().equals(event.getThreadName())) {
      return false;
    }

    if (this._config.isCategorySet() && (event.getCategory() != null)
        && (event.getCategory().indexOf(this._config.getCategory()) == -1)) {
      return false;
    }

    if (this._config.isMessageSet() && (event.getMessage().indexOf(this._config.getMessage()) == -1)) {
      return false;
    }

    return true;

  }

  public LogEventFilterMemento saveToMemento() {
    // TODO copy
    return this._config;
  }

}
