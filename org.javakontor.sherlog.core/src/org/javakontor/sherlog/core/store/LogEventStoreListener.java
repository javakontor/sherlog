package org.javakontor.sherlog.core.store;

import java.util.Collection;
import java.util.EventListener;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;

/**
 * <p>
 * An instance of type {@link LogEventStoreListener} can be used to receive events if {@link LogEvent LogEvents} or
 * {@link LogEventFilter LogEventFilters} are added to or removed from the {@link LogEventStore}.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventStoreListener extends EventListener {

  /**
   * <p>
   * Invoked when new log events are added to the {@link LogEventStore}.
   * </p>
   *
   * @param logEvents
   *          the added log events.
   */
  public void logEventsAdded(Collection<LogEvent> logEvents);

  /**
   * <p>
   * Invoked when log events are removed from the {@link LogEventStore}.
   * </p>
   *
   * @param logEvents
   *          the removed log events.
   */
  public void logEventsRemoved(Collection<LogEvent> logEvents);

  /**
   * <p>
   * Invoked when log events with new categories are added to the {@link LogEventStore}.
   * </p>
   *
   * @param categories
   *          the added categories
   */
  public void categoriesAdded(Collection<String> categories);

  /**
   * <p>
   * Invoked when categories are removed from the {@link LogEventStore}.
   * </p>
   *
   * @param categories
   *          the removed categories
   */
  public void categoriesRemoved(Collection<String> categories);

  /**
   * <p>
   * Invoked when a new filter is added to the {@link LogEventStore}.
   * </p>
   *
   * @param logEventFilter
   *          the added {@link LogEventFilter}
   */
  public void filterAdded(LogEventFilter logEventFilter);

  /**
   * <p>
   * Invoked when a filter is removed from the {@link LogEventStore}.
   * </p>
   *
   * @param logEventFilter
   *          the removed {@link LogEventFilter}
   */
  public void filterRemoved(LogEventFilter logEventFilter);

  /**
   * <p>
   * Invoked when the {@link LogEventStore} is reset.
   * </p>
   */
  public void logEventStoreReset();
}
