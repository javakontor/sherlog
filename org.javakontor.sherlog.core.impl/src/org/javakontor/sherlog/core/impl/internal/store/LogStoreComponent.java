package org.javakontor.sherlog.core.impl.internal.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.impl.filter.AbstractFilterable;
import org.javakontor.sherlog.core.store.LogEventStoreListener;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogStoreComponent extends AbstractFilterable implements ModifiableLogEventStore {

  /** */
  private final List<LogEvent>    _logEvents;

  private final List<LogEvent>    _filteredLogEvents;

  /** */
  private final List<String>      _categories;

  /** */
  private final EventListenerList _eventListenerList;

  /**
   * 
   */
  public LogStoreComponent() {
    _logEvents = new ArrayList<LogEvent>();
    _categories = new ArrayList<String>();
    _filteredLogEvents = new ArrayList<LogEvent>();
    _eventListenerList = new EventListenerList();
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
  public boolean addLogEventFilter(LogEventFilter logEventFilter) {
    boolean logEventFilterAdded = super.addLogEventFilter(logEventFilter);
    if (logEventFilterAdded) {
      refilter();
    }
    return logEventFilterAdded;
  }

  @Override
  public boolean removeLogEventFilter(LogEventFilter logEventFilter) {
    boolean logEventFilterRemoved = super.removeLogEventFilter(logEventFilter);
    if (logEventFilterRemoved) {
      refilter();
    }
    return logEventFilterRemoved;
  }

  protected void refilter() {
    _filteredLogEvents.clear();

    for (LogEvent logEvent : _logEvents) {
      if (isFiltered(logEvent)) {
        _filteredLogEvents.add(logEvent);
      }
    }
  }

  /** ModifiableLoggingEventStore */

  public void addLogEvent(LogEvent event) {
    List<LogEvent> list = new LinkedList<LogEvent>();
    list.add(event);
    addLogEvents(list);
  }

  public void addLogEvents(List<LogEvent> events) {
    if (events.isEmpty()) {
      return;
    }

    List<String> newCategories = new LinkedList<String>();
    List<LogEvent> newFilteredEvents = new LinkedList<LogEvent>();

    for (LogEvent event : events) {
      // add to logEvents list
      _logEvents.add(event);

      // add new categories and save for event handling later on
      String category = event.getCategory();
      if (!_categories.contains(newCategories)) {
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

    // TODO
    fireLogEventsAdded(events);
  }

  public void reset() {
    _logEvents.clear();
    _filteredLogEvents.clear();
    _categories.clear();

    fireLogEventStoreReset();
  }

  /** Filterable */

  public List<LogEvent> getFilteredLogEvents() {
    return getLogEvents();
  }

  protected void fireLogEventsAdded(final Collection<LogEvent> loggingEvents) {
    if (loggingEvents == null) {
      return;
    }

    // System.err.println("hae");

    for (LogEventStoreListener logEventStoreListener : _eventListenerList.getListeners(LogEventStoreListener.class)) {

      // System.err.println(logEventStoreListener);

      logEventStoreListener.logEventsAdded(loggingEvents);
    }
  }

  protected void fireCategoriesAdded(final Collection<String> categories) {
    if (categories == null) {
      return;
    }
    for (LogEventStoreListener logEventStoreListener : _eventListenerList.getListeners(LogEventStoreListener.class)) {
      logEventStoreListener.categoriesAdded(categories);
    }

  }

  protected void fireLogEventStoreReset() {
    for (LogEventStoreListener logEventStoreListener : _eventListenerList.getListeners(LogEventStoreListener.class)) {
      logEventStoreListener.logEventStoreReset();
    }
  }
}
