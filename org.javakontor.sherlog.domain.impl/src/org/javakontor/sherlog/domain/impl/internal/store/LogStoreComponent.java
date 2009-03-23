package org.javakontor.sherlog.domain.impl.internal.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.LogEventFilterChangeEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterFactory;
import org.javakontor.sherlog.domain.impl.filter.AbstractFilterable;
import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.LogEventChangeEvent;
import org.javakontor.sherlog.domain.impl.reader.LogEventChangeListener;
import org.javakontor.sherlog.domain.store.LogEventStoreEvent;
import org.javakontor.sherlog.domain.store.LogEventStoreListener;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogStoreComponent extends AbstractFilterable implements ModifiableLogEventStore, LogEventChangeListener {

  private final Logger                                     _logger = LoggerFactory.getLogger(getClass());

  /** */
  private final List<LogEvent>                             _logEvents;

  private final List<LogEvent>                             _filteredLogEvents;

  /** */
  private final List<String>                               _categories;

  /** */
  private final EventListenerList                          _eventListenerList;

  private final Map<LogEventFilterFactory, LogEventFilter> _registeredLogEventFilters;

  /**
   *
   */
  public LogStoreComponent() {
    _logEvents = new ArrayList<LogEvent>();
    _categories = new ArrayList<String>();
    _filteredLogEvents = new ArrayList<LogEvent>();
    _eventListenerList = new EventListenerList();
    _registeredLogEventFilters = new Hashtable<LogEventFilterFactory, LogEventFilter>();
  }

  public void addLogStoreListener(final LogEventStoreListener listener) {
    _eventListenerList.add(LogEventStoreListener.class, listener);
  }

  public void removeLogStoreListener(final LogEventStoreListener listener) {
    _eventListenerList.remove(LogEventStoreListener.class, listener);
  }

  public List<LogEvent> getLogEvents() {
    return Collections.unmodifiableList(_logEvents);
  }

  public List<String> getCategories() {
    return Collections.unmodifiableList(_categories);
  }

  public int getLogEventCount() {
    return _logEvents.size();
  }

  public int getFilteredLogEventCount() {
    return _filteredLogEvents.size();
  }

  @Override
  protected void logEventFilterAdded(final LogEventFilter logEventFilter) {
    refilter();
  }

  @Override
  protected void logEventFilterRemoved(final LogEventFilter logEventFilter) {
    refilter();
  }

  @Override
  public void filterChanged(final LogEventFilterChangeEvent event) {
    refilter();
  }

  protected void refilter() {
    _filteredLogEvents.clear();

    for (final LogEvent logEvent : _logEvents) {
      if (isFiltered(logEvent)) {
        _filteredLogEvents.add(logEvent);
      }
    }

    fireLogEventStoreChange();

  }

  /** ModifiableLoggingEventStore */

  public void addLogEvent(final LogEvent event) {
    final List<LogEvent> list = new LinkedList<LogEvent>();
    list.add(event);
    addLogEvents(list);
  }

  public void addLogEvents(final List<LogEvent> events) {
    Assert.notNull("Parameter 'events' must not be null", events);

    if (events.isEmpty()) {
      return;
    }

    for (final LogEvent event : events) {
      // add to logEvents list
      _logEvents.add(event);
      if (event instanceof AbstractLogEvent) {
        final AbstractLogEvent abstractLogEvent = (AbstractLogEvent) event;
        abstractLogEvent.addLogEventChangeListener(this);
      }

      // add new categories and save for event handling later on
      final String category = event.getCategory();
      if (!_categories.contains(category)) {
        _categories.add(category);
      }

      // check if it's a filtered event
      if (isFiltered(event)) {
        _filteredLogEvents.add(event);
      }
    }

    Collections.sort(_logEvents);
    Collections.sort(_filteredLogEvents);

    fireLogEventStoreChange();
  }

  public void reset() {
    for (final LogEvent logEvent : _logEvents) {
      if (logEvent instanceof AbstractLogEvent) {
        final AbstractLogEvent abstractLogEvent = (AbstractLogEvent) logEvent;
        abstractLogEvent.removeLogEventChangeListener(this);
      }
    }

    _logEvents.clear();
    _filteredLogEvents.clear();
    _categories.clear();

    fireLogEventStoreChange();
  }

  /** Filterable */

  public List<LogEvent> getFilteredLogEvents() {
    return _filteredLogEvents;
  }

  public void addLogEventFilterFactory(final LogEventFilterFactory logEventFilterFactory) {
    _logger.debug("LogEventFilterFactory added: " + logEventFilterFactory);
    final LogEventFilter logEventFilter = logEventFilterFactory.createLogEventFilter();
    addLogEventFilter(logEventFilter);
    _registeredLogEventFilters.put(logEventFilterFactory, logEventFilter);
  }

  public void removeLogEventFilterFactory(final LogEventFilterFactory logEventFilterFactory) {
    _logger.debug("LogEventFilterFactory removed: " + logEventFilterFactory);
    final LogEventFilter logEventFilter = _registeredLogEventFilters.remove(logEventFilterFactory);
    if (logEventFilter != null) {
      removeLogEventFilter(logEventFilter);
    }
  }

  protected void fireLogEventStoreChange() {
    final LogEventStoreEvent event = new LogEventStoreEvent(this);
    for (final LogEventStoreListener logEventStoreListener : _eventListenerList
        .getListeners(LogEventStoreListener.class)) {
      logEventStoreListener.logEventStoreChanged(event);
    }
  }

  public void logEventChange(final LogEventChangeEvent event) {
    refilter();
  }
}
