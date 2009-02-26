package org.javakontor.sherlog.core.impl.filter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.Filterable;
import org.javakontor.sherlog.core.filter.FilterableChangeListener;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.filter.LogEventFilterChangeEvent;
import org.javakontor.sherlog.core.filter.LogEventFilterListener;
import org.javakontor.sherlog.util.Assert;

public class AbstractFilterable implements Filterable, LogEventFilterListener {

  private final CopyOnWriteArrayList<LogEventFilter>          _logEventFilters;

  private final CopyOnWriteArraySet<FilterableChangeListener> _filterableChangeListener;

  public AbstractFilterable() {
    _logEventFilters = new CopyOnWriteArrayList<LogEventFilter>();
    _filterableChangeListener = new CopyOnWriteArraySet<FilterableChangeListener>();
  }

  /**
   * Subclasses that wants to react on an added log event filter must overwrite logEventFilterAdded() to make sure the
   * correct events are sent after adding a new LogEventFilter
   * 
   */
  public final boolean addLogEventFilter(LogEventFilter logEventFilter) {
    Assert.notNull("Parameter 'logEventFilter' must not be null", logEventFilter);
    boolean logEventFilterAdded = _logEventFilters.addIfAbsent(logEventFilter);
    if (logEventFilterAdded) {
      // invoke hook method for subclasses
      logEventFilterAdded(logEventFilter);

      // inform registered listener about new LogFilterEvent
      for (FilterableChangeListener filterableChangeListener : _filterableChangeListener) {
        filterableChangeListener.logEventFilterAdded(logEventFilter);
      }

      logEventFilter.addLogFilterListener(this);
    }
    return logEventFilterAdded;
  }

  /**
   * Can be used in subclasses to react immediately if a new LogEventFilter has been added.
   * 
   * <p>
   * <b>Note</b> The {@link FilterableChangeListener} get's informed after the execution of this method
   * 
   * @param logEventFilter
   */
  protected void logEventFilterAdded(LogEventFilter logEventFilter) {
  }

  public List<LogEventFilter> getLogEventFilters() {
    return Collections.unmodifiableList(_logEventFilters);
  }

  /**
   * Subclasses that wants to react on a removed log event filter must overwrite logEventFilterRemoved() in order to
   * make sure the correct events are sent after removing a new LogEventFilter
   * 
   */
  public final boolean removeLogEventFilter(LogEventFilter logEventFilter) {
    boolean logEventFilterRemoved = _logEventFilters.remove(logEventFilter);
    if (logEventFilterRemoved) {
      logEventFilter.removeLogFilterListener(this);
      // invoke hook method
      logEventFilterRemoved(logEventFilter);

      // inform listener
      for (FilterableChangeListener filterableChangeListener : _filterableChangeListener) {
        filterableChangeListener.logEventFilterRemoved(logEventFilter);
      }
    }
    return logEventFilterRemoved;
  }

  /**
   * Overwrite in subclasses to react on a changed filter that has been registered with this Filterable
   */
  public void filterChanged(LogEventFilterChangeEvent event) {

  }

  /**
   * Can be used in subclasses to react immediately if a LogEventFilter has been removed.
   * 
   * <p>
   * <b>Note</b> The {@link FilterableChangeListener} get's informed after the execution of this method
   * 
   * @param logEventFilter
   */
  protected void logEventFilterRemoved(LogEventFilter logEventFilter) {
  }

  public boolean addFilterableChangeListener(FilterableChangeListener filterableChangeListener) {
    return _filterableChangeListener.add(filterableChangeListener);
  }

  public boolean removeFilterableChangeListener(FilterableChangeListener filterableChangeListener) {
    return _filterableChangeListener.remove(filterableChangeListener);
  }

  /**
   * Returns <tt>true</tt> if there a no {@link LogEventFilter LogEventFilters} registered or if one of the registered
   * {@link LogEventFilter#matches(LogEvent) matches} this logEvent.
   * 
   * @param logEvent
   * @return
   */
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
