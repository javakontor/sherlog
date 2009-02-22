package org.javakontor.sherlog.core.impl.internal.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.store.LogEventStoreListener;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogStoreComponent implements ModifiableLogEventStore {

  /** */
  private final List<LogEvent>    _logEvents;

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
    // TODO Auto-generated method stub
    return 0;
  }

  public void addLogEventFilter(LogEventFilter logEventFilter) {
    // TODO Auto-generated method stub

  }

  public List<LogEventFilter> getLogEventFilters() {
    // TODO Auto-generated method stub
    return null;
  }

  public void removeLogEventFilter(LogEventFilter logEventFilter) {
    // TODO Auto-generated method stub

  }

  /** ModifiableLoggingEventStore */

  public void addLogEvent(LogEvent event) {

    // System.err.println("addLogEvent");

    _logEvents.add(event);
    Collections.sort(_logEvents);

    List<LogEvent> list = new LinkedList<LogEvent>();
    list.add(event);

    fireLogEventsAdded(list);
  }

  public void addLogEvents(List<LogEvent> events) {

    // System.err.println("addLogEvents");

    _logEvents.addAll(events);
    Collections.sort(_logEvents);

    fireLogEventsAdded(events);
  }

  public void reset() {
    _logEvents.clear();
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
