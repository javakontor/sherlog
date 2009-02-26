package org.javakontor.sherlog.core.store;

import java.util.EventObject;
import java.util.List;

import org.javakontor.sherlog.core.LogEvent;

public class LogEventStoreChangeEvent extends EventObject {

  /**
   * 
   */
  private static final long    serialVersionUID = 1L;

  private final List<LogEvent> _logEvents;

  private final List<LogEvent> _filteredLogEvents;

  private final List<String>   _categories;

  public LogEventStoreChangeEvent(LogEventStore source, List<LogEvent> logEvents, List<LogEvent> filteredLogEvents,
      List<String> categories) {
    super(source);

    this._logEvents = logEvents;
    this._filteredLogEvents = filteredLogEvents;
    this._categories = categories;
  }

  public List<LogEvent> getLogEvents() {
    return _logEvents;
  }

  public List<LogEvent> getFilteredLogEvents() {
    return _filteredLogEvents;
  }

  public List<String> getCategories() {
    return _categories;
  }

  public boolean hasLogEvents() {
    return (!this._logEvents.isEmpty());
  }

  public boolean hasFilteredLogEvents() {
    return (!this._filteredLogEvents.isEmpty());
  }

  public boolean hasCategories() {
    return (!this._categories.isEmpty());
  }

}
