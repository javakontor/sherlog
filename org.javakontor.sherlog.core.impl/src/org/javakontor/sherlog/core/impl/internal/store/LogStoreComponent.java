package org.javakontor.sherlog.core.impl.internal.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.filter.LogEventFilterChangeEvent;
import org.javakontor.sherlog.core.filter.LogEventFilterFactory;
import org.javakontor.sherlog.core.impl.filter.AbstractFilterable;
import org.javakontor.sherlog.core.store.LogEventStoreChangeEvent;
import org.javakontor.sherlog.core.store.LogEventStoreListener;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogStoreComponent extends AbstractFilterable implements ModifiableLogEventStore {

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

  public void addLogStoreListener(LogEventStoreListener listener) {
    _eventListenerList.add(LogEventStoreListener.class, listener);
  }

  public void removeLogStoreListener(LogEventStoreListener listener) {
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
  protected void logEventFilterAdded(LogEventFilter logEventFilter) {
    refilter();
  }

  @Override
  protected void logEventFilterRemoved(LogEventFilter logEventFilter) {
    refilter();
  }

  @Override
  public void filterChanged(LogEventFilterChangeEvent event) {
    refilter();
  }

  protected void refilter() {
    _filteredLogEvents.clear();

    for (LogEvent logEvent : _logEvents) {
      if (isFiltered(logEvent)) {
        _filteredLogEvents.add(logEvent);
      }
    }

    fireLogEventStoreReset();

  }

  /** ModifiableLoggingEventStore */

  public void addLogEvent(LogEvent event) {
    List<LogEvent> list = new LinkedList<LogEvent>();
    list.add(event);
    addLogEvents(list);
  }

  public void addLogEvents(List<LogEvent> events) {
    Assert.notNull("Parameter 'events' must not be null", events);

    if (events.isEmpty()) {
      return;
    }

    final List<String> newCategories = new LinkedList<String>();
    final List<LogEvent> newFilteredEvents = new LinkedList<LogEvent>();

    for (LogEvent event : events) {
      // add to logEvents list
      _logEvents.add(event);

      // add new categories and save for event handling later on
      String category = event.getCategory();
      if (!_categories.contains(category)) {
        _categories.add(category);
        newCategories.add(category);
      }

      // check if it's a filtered event
      if (isFiltered(event)) {
        _filteredLogEvents.add(event);
        newFilteredEvents.add(event);
      }
    }

    Collections.sort(_logEvents);
    if (!newFilteredEvents.isEmpty()) {
      Collections.sort(_filteredLogEvents);
    }

    fireLogEventsAdded(events, newFilteredEvents, newCategories);
  }

  public void reset() {
    _logEvents.clear();
    _filteredLogEvents.clear();
    _categories.clear();

    fireLogEventStoreReset();
  }

  /** Filterable */

  public List<LogEvent> getFilteredLogEvents() {
    return _filteredLogEvents;
  }

  public void addLogEventFilterFactory(LogEventFilterFactory logEventFilterFactory) {
    _logger.debug("LogEventFilterFactory added: " + logEventFilterFactory);
    LogEventFilter logEventFilter = logEventFilterFactory.createLogEventFilter();
    addLogEventFilter(logEventFilter);
    _registeredLogEventFilters.put(logEventFilterFactory, logEventFilter);
  }

  public void removeLogEventFilterFactory(LogEventFilterFactory logEventFilterFactory) {
    _logger.debug("LogEventFilterFactory removed: " + logEventFilterFactory);
    LogEventFilter logEventFilter = _registeredLogEventFilters.remove(logEventFilterFactory);
    if (logEventFilter != null) {
      removeLogEventFilter(logEventFilter);
    }
  }

  protected void fireLogEventsAdded(final List<LogEvent> loggingEvents, final List<LogEvent> filteredLogEvents,
      final List<String> categoriesAdded) {

    LogEventStoreChangeEvent event = new LogEventStoreChangeEvent(this, Collections.unmodifiableList(loggingEvents),
        Collections.unmodifiableList(filteredLogEvents), Collections.unmodifiableList(categoriesAdded));

    for (LogEventStoreListener logEventStoreListener : _eventListenerList.getListeners(LogEventStoreListener.class)) {
      logEventStoreListener.logEventsAdded(event);
    }
  }

  protected void fireLogEventStoreReset() {
    for (LogEventStoreListener logEventStoreListener : _eventListenerList.getListeners(LogEventStoreListener.class)) {
      logEventStoreListener.logEventStoreReset();
    }
  }
}
