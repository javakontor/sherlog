package org.javakontor.sherlog.core.impl.filter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.Filterable;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.util.Assert;

public class AbstractFilterable implements Filterable {

  private final CopyOnWriteArrayList<LogEventFilter> _logEventFilters;

  public AbstractFilterable() {
    _logEventFilters = new CopyOnWriteArrayList<LogEventFilter>();
  }

  public boolean addLogEventFilter(LogEventFilter logEventFilter) {
    Assert.notNull("Parameter 'logEventFilter' must not be null", logEventFilter);
    return _logEventFilters.addIfAbsent(logEventFilter);
  }

  public List<LogEventFilter> getLogEventFilters() {
    return Collections.unmodifiableList(_logEventFilters);
  }

  public boolean removeLogEventFilter(LogEventFilter logEventFilter) {
    return _logEventFilters.remove(logEventFilter);
  }

  protected boolean isFiltered(LogEvent logEvent) {
    List<LogEventFilter> logEventFilters = _logEventFilters;
    if (logEventFilters.isEmpty()) {
      return true;
    }
    for (LogEventFilter filter : logEventFilters) {
      if (!filter.matches(logEvent)) {
        return false;
      }
    }
    return true;
  }

}
